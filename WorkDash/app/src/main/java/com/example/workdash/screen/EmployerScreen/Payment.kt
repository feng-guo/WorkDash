package com.example.workdash.screen.EmployerScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.workdash.R
import com.steliospapamichail.creditcardmasker.utils.CardType
import com.steliospapamichail.creditcardmasker.utils.getCardTypeFromNumber
import com.steliospapamichail.creditcardmasker.viewtransformations.CardNumberMask
import com.steliospapamichail.creditcardmasker.viewtransformations.ExpirationDateMask
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import okhttp3.RequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun Payment() {
    var cardNumber by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("0") }

    val bankIcons = listOf(
        R.drawable.visa,
        R.drawable.mastercard,
        R.drawable.american_express,
        R.drawable.maestro,
        R.drawable.dinners_club,
        R.drawable.discover,
        R.drawable.jcb
    )

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "PAYMENTS",
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (icon in bankIcons) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "$$amount",
            style = TextStyle(
                color = Color.Black,
                fontSize = 40.sp,
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = cardNumber,
            visualTransformation = CardNumberMask("-"),
            trailingIcon = {
                val iconRes = when (getCardTypeFromNumber(cardNumber)) {
                    CardType.VISA -> R.drawable.visa
                    CardType.MASTERCARD -> R.drawable.mastercard
                    CardType.AMERICAN_EXPRESS -> R.drawable.american_express
                    CardType.MAESTRO -> R.drawable.maestro
                    CardType.DINNERS_CLUB -> R.drawable.dinners_club
                    CardType.DISCOVER -> R.drawable.discover
                    CardType.JCB -> R.drawable.jcb
                    else -> R.drawable.ic_launcher_background
                }
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "card_type",
                    modifier = Modifier
                        .height(30.dp)
                        .width(40.dp)
                        .padding(end = 10.dp)
                )
            },
            onValueChange = {
                if (it.length <= 16) cardNumber = it
            },
            label = { Text("Card number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            OutlinedTextField(
                value = expiry,
                visualTransformation = ExpirationDateMask(),
                onValueChange = {
                    if (it.length <= 4) expiry = it
                }, label = { Text("Expiry") },
                modifier = Modifier.width(200.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = cvc,
                onValueChange = { cvc = it },
                label = { Text("CVC") },
                singleLine = true,
                modifier = Modifier.width(200.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PaymentButton(
            cardNumber,
            expiry,
            name,
            cvc,
            amount
        )
    }
}

@Composable
fun PaymentButton(
    cardNumber: String,
    expMonth: String,
    expYear: String,
    cvc: String,
    amount: String
) {

    var isLoading by remember { mutableStateOf(false) }
    var paymentSuccess by remember { mutableStateOf(false) }
    var totalAmount = ""
    if(amount != "")
    {
        totalAmount = ((amount.toInt()) * 100).toString()
    }
    val scope = rememberCoroutineScope()
    var customerIdState = ""
    var emphKeyState = ""
    var clientSecretState = ""
    val apiKey = "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"

    Button(
        onClick = {
            scope.launch {
                isLoading = true
                val url = "https://api.stripe.com/v1/customers"
                val requestBody = RequestBody.create(null, "")
                try {
                    withContext(Dispatchers.IO) {
                        val client = OkHttpClient()

                        val request = Request.Builder()
                            .url(url)
                            .addHeader("Authorization", "Bearer $apiKey")
                            .post(requestBody)
                            .build()

                        val response = client.newCall(request).execute()
                        val responseText = response.body?.string() ?: "Error: Empty Response"

                        val jsonObject = JSONObject(responseText)

                        val customerId = jsonObject.optString("id", "")

                        customerIdState = customerId.toString()

                        val url2 = "https://api.stripe.com/v1/ephemeral_keys"

                        val requestBody2 = FormBody.Builder()
                            .add("customer", customerIdState)
                            .build()

                        val request2 = Request.Builder()
                            .url(url2)
                            .header("Authorization", "Bearer $apiKey")
                            .header("Stripe-Version", "2022-11-15")
                            .post(requestBody2)
                            .build()

                        val response2 = client.newCall(request2).execute()
                        val responseText2 = response2.body?.string() ?: "Error: Empty Response"

                        val jsonObject2 = JSONObject(responseText2)

                        val emphKey = jsonObject2.optString("id", "")

                        emphKeyState = emphKey.toString()

                        val url3 = "https://api.stripe.com/v1/payment_intents"

                        val requestBody3 = FormBody.Builder()
                            .add("customer", customerIdState)
                            .add("amount", totalAmount)
                            .add("currency", "cad")
                            .add("automatic_payment_methods[enabled]", "true")
                            .build()

                        val request3 = Request.Builder()
                            .url(url3)
                            .header("Authorization", "Bearer $apiKey")
                            .post(requestBody3)
                            .build()

                        val response3 = client.newCall(request3).execute()
                        val responseText3 = response3.body?.string() ?: "Error: Empty Response"

                        val jsonObject3 = JSONObject(responseText3)

                        val paymentIntent = jsonObject3.optString("client_secret", "")

                        clientSecretState = paymentIntent.toString()
                        isLoading = false
                        paymentSuccess = true
                    }
                } catch (e: IOException) {
                    customerIdState = "Error"
                }
            }
        },
        modifier = Modifier.fillMaxWidth().height(50.dp),
        enabled = !isLoading,
        colors = ButtonDefaults.buttonColors(
        backgroundColor = if (paymentSuccess) Color.White else MaterialTheme.colors.primary,
    ),
        border = BorderStroke(1.dp, if (paymentSuccess) MaterialTheme.colors.primary else Color.Transparent)
    ) {
        if (isLoading && !paymentSuccess) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.size(20.dp)
            )
        }
        else if (paymentSuccess) {
            Text("Success ", color = Color(0xFF4BB543))
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF4BB543),
                modifier = Modifier.size(20.dp)
            )
        }
        else {
            Text("Pay Now", color = Color.White)
        }
    }
}