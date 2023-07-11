package com.example.workdash.services

object UserService {

    //Fetch the business ID based on the logged in user
//                    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
//                    val query = database.child("userProfile").orderByChild("uid").equalTo(currentUserUid)
//                    query.addListenerForSingleValueEvent(object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            for (snapshot in dataSnapshot.children) {
//                                // Access the value of isWorker from each matching employer profile
//                                val isWorker = snapshot.child("worker").getValue(Boolean::class.java)
//                                if (isWorker == true) {
//                                    //Broken
//                                } else {
//                                    //Get business Id
//                                }
//                            }
//                        }
//                        override fun onCancelled(databaseError: DatabaseError) {
//                            //TODO
//                        }
//                    })

    //TODO
    fun getCurrentUserId(): String {
        return ""
    }
}