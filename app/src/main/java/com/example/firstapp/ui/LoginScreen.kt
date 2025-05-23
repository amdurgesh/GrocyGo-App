package com.example.firstapp.ui

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

@Composable
fun LoginScreen(
    appViewModel: AppViewModel,
    callbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
){
    val phoneNumber by appViewModel.phoneNumber.collectAsState()
    val context = LocalContext.current
    Text(text = "Login",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
        )
    Text(text = "Enter Phone No To Proceed",
        fontSize = 20.sp,
        modifier = Modifier.fillMaxWidth()
        )
    TextField(
        value = phoneNumber,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        onValueChange ={
            if(it.length <= 10 && it.all{ char -> char.isDigit() }){
                appViewModel.setPhoneNumber(
                    it
                )
            }

        },
        label = {
            Text(text = "Your Number")
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
    Button(
        onClick =  {
            if (phoneNumber.length == 10){
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber("+91${phoneNumber}") // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(context as Activity) // Activity (for callback binding)
                    .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            } else{
                Toast.makeText(context, "Invalid Number", Toast.LENGTH_SHORT).show()
            }
            appViewModel.setLoading(false)

        },
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text = "Send OTP")
    }

}