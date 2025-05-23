package com.example.firstapp.ui

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(
    appViewModel: AppViewModel = viewModel(),
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        Image(
            painter = painterResource(id = R.drawable.progress_horizontal),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Name & Email
        Text(text = "John Doe", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = "johndoe@example.com", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Menu Items
        ProfileMenuItem(icon = Icons.Outlined.ShoppingCart, title = "My Cart") {
            navController.navigate("cart_screen") // Navigate to Cart Screen
        }

        ProfileMenuItem(icon = Icons.Outlined.FavoriteBorder, title = "Favorites") {
            navController.navigate("favorites_screen") // Navigate to Favorites Screen
        }

        ProfileMenuItem(icon = Icons.Outlined.Phone, title = "Contact Us") {
            navController.navigate("contact_us_screen") // Navigate to Contact Us Screen
        }

        ProfileMenuItem(icon = Icons.Outlined.Info, title = "Terms & Conditions") {
            navController.navigate("terms_conditions_screen") // Navigate to T&C
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Logout Button
        Row( modifier = Modifier.clickable {
            appViewModel.setLogoutStatus(true)
        })
        {
            Icon(painter = painterResource(id = R.drawable.ic_lock_lock), contentDescription = "Logout",
                modifier = Modifier.size(24.dp)
            )
            Text(text = "Logout",
                fontSize = 18.sp,
                modifier = Modifier.padding(
                    end = 14.dp,
                    start = 4.dp
                )
            )
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector =  Icons.Outlined.ShoppingCart, title: String, content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}