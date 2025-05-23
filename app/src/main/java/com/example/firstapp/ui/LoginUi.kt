package com.example.firstapp.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.firstapp.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun LoginUi(appViewModel: AppViewModel) {
    val context = LocalContext.current
    val otp by appViewModel.otp.collectAsState()
    val verificationId by appViewModel.verificationId.collectAsState()
    val loading by appViewModel.loading.collectAsState()
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {}

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            appViewModel.setVerificationId(verificationId)
            Toast.makeText(context, "OTP Sent Successfully", Toast.LENGTH_SHORT).show()
            appViewModel.resetTimer()
            appViewModel.runTimer()
            appViewModel.setLoading(false)
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Login Screen",
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 10.dp)
                    .size(100.dp)
            )

            // Show login screen first, and OTP screen only after verificationId is set
            if (verificationId.isEmpty()) {
                LoginScreen(appViewModel = appViewModel, callbacks = callbacks)
            } else {
                OtpScreen(otp = otp, appViewModel = appViewModel, callbacks = callbacks)
            }

        }
        if (verificationId.isNotEmpty()){
            IconButton(onClick ={
                appViewModel.setVerificationId("")
                appViewModel.setOtp("")
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        if (loading){
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
                    .background(color = Color(255,255,255,190))
            ){
                CircularProgressIndicator()
                Text(text = "Loading")
            }
        }

    }
}
