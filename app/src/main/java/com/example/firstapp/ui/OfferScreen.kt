package com.example.firstapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.firstapp.R
import java.nio.file.WatchEvent

@Composable
fun OfferScreen(){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(color = Color(23,185, 201, 255))
    ){
        Image(painter = painterResource(id = R.drawable.offer60), contentDescription = "offer",
            modifier = Modifier
                .padding(end = 10.dp)
                .offset(y = 80.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Image(painter = painterResource(id = R.drawable.get_offer), contentDescription = "Offer",
            modifier = Modifier
                .height(850.dp)
                .width(1000.dp)
                .offset(y = (-20).dp)
            )

    }
}