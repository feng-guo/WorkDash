package com.example.workdash.screen.WorkerScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.example.workdash.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType.CV_32F
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun AuthenticateWorker() {
    val context = LocalContext.current
    OpenCVLoader.initDebug()
    val imageResourceId = R.drawable.driving
    val image = loadImageFromResource(context, imageResourceId)
    val grayImage = convertToGray(image)
    val cascadeClassifier = loadFaceCascadeClassifier(context)
    val faces = detectFaces(grayImage, cascadeClassifier)

    if (faces.isNotEmpty()) {
        val bestFaceRect = chooseBestFace(faces)

        val faceImage = Mat(image, bestFaceRect)
        val anotherImageResourceId = R.drawable.she
        val anotherImage = loadImageFromResource(context, anotherImageResourceId)
        val grayAnotherImage = convertToGray(anotherImage)

        val resizedFaceImage = resizeImage(faceImage, Size(100.0, 100.0))

        val accuracy = calculateFaceMatchAccuracy(resizedFaceImage, grayAnotherImage)
        println("Face Match Accuracy: $accuracy%")

        val resizedFaceBitmap = Bitmap.createBitmap(resizedFaceImage.cols(), resizedFaceImage.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(resizedFaceImage, resizedFaceBitmap)

        val grayAnotherBitmap = Bitmap.createBitmap(grayAnotherImage.cols(), grayAnotherImage.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(grayAnotherImage, grayAnotherBitmap)

        val resizedFaceState = remember { mutableStateOf(resizedFaceBitmap) }
        val grayAnotherState = remember { mutableStateOf(grayAnotherBitmap) }

        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(630.dp)
        ) {
            item{
                Image(
                    bitmap = resizedFaceState.value.asImageBitmap(),
                    contentDescription = "Gray Another Image"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    bitmap = grayAnotherState.value.asImageBitmap(),
                    contentDescription = "Resized Face Image"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Face Match Accuracy:",
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$accuracy%",
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    } else {
        println("No face detected in the image.")
    }
}

private fun chooseBestFace(faces: List<Rect>): Rect {
    return faces.maxByOrNull { it.width * it.height } ?: faces.first()
}

private fun convertToGray(image: Mat): Mat {
    if (image.channels() > 1) {
        val grayImage = Mat()
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY)
        return grayImage
    }
    return image
}

private fun loadImageFromResource(context: Context, resourceId: Int): Mat {
    val imageBitmap = BitmapFactory.decodeResource(context.resources, resourceId)
    val imageMat = Mat()
    Utils.bitmapToMat(imageBitmap, imageMat)
    return imageMat
}

private fun loadFaceCascadeClassifier(context: Context): CascadeClassifier {
    val cascadeClassifier = CascadeClassifier()
    val cascadeClassifierFile = File(context.cacheDir, "haarcascade_frontalface_default.xml")

    if (!cascadeClassifierFile.exists()) {
        try {
            val inputStream = context.resources.openRawResource(R.raw.haarcascade_frontalface_default)
            val outputStream = FileOutputStream(cascadeClassifierFile)

            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)

            outputStream.write(buffer)
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    cascadeClassifier.load(cascadeClassifierFile.absolutePath)

    return cascadeClassifier
}

private fun detectFaces(image: Mat, cascadeClassifier: CascadeClassifier): List<Rect> {
    val grayImage = convertToGray(image)

    val faces = MatOfRect()
    cascadeClassifier.detectMultiScale(grayImage, faces)

    return faces.toList()
}

private fun resizeImage(image: Mat, newSize: Size): Mat {
    val resizedImage = Mat()
    Imgproc.resize(image, resizedImage, newSize)
    return resizedImage
}

private fun calculateFaceMatchAccuracy(image1: Mat, image2: Mat): Double {
    val size = Size(100.0, 100.0)
    val resizedImage1 = resizeImage(image1, size)
    val resizedImage2 = resizeImage(image2, size)

    val grayImage1 = convertToGray(resizedImage1)
    val grayImage2 = convertToGray(resizedImage2)

    val floatImage1 = Mat()
    grayImage1.convertTo(floatImage1, CV_32F)
    val floatImage2 = Mat()
    grayImage2.convertTo(floatImage2, CV_32F)

    val diff = Mat()
    Core.subtract(floatImage1, floatImage2, diff)
    val norm = Core.norm(diff)

    return (1.0 - (norm / (size.width * size.height))) * 100.0
}