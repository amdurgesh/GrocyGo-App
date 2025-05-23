package com.example.firstapp.ui


import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.firstapp.data.InternetItem
import com.google.firebase.auth.FirebaseAuth

enum class FirstAppScreen(val title: String){
    Start("GrocyGo"),
    Item("Choose Items"),
    Cart("Your Cart"),
    Profile("Your Profile")
}

var canNavigateBack = false
val auth = FirebaseAuth.getInstance()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstApp(
    appViewModel: AppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
    ){

    val user by appViewModel.user.collectAsState()
    val logoutClicked by appViewModel.logoutClicked.collectAsState()
    auth.currentUser?.let { appViewModel.setUser(it) }
    val isVisible by appViewModel.isVisible.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FirstAppScreen.valueOf(
        backStackEntry?.destination?.route?: FirstAppScreen.Start.name
    )
    canNavigateBack = navController.previousBackStackEntry != null
    val cartItems by appViewModel.cartItems.collectAsState()

    if (isVisible){
        OfferScreen()
    } else if (user == null){
        LoginUi(appViewModel = appViewModel)
    }

    else{
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    text = currentScreen.title,
                                    fontSize = 26.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                if (currentScreen == FirstAppScreen.Cart) {
                                    Text(
                                        text = "(${cartItems.size})",
                                        fontSize = 26.sp,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )
                                }
                            }
                            /*Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.clickable {
                                    navController.navigate(FirstAppScreen.Start.name){
                                       // popUpTo(0)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Notifications,
                                    contentDescription = "Notification",
                                    modifier = Modifier
                                        .width(30.dp)
                                        .height(35.dp)
                                )
                            }*/
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.clickable {
                                    if (currentScreen != FirstAppScreen.Cart){
                                        navController.navigate(FirstAppScreen.Cart.name) {

                                        }
                                    }
                                }
                            ) {
                                Box {
                                    Icon(
                                        imageVector = Icons.Outlined.ShoppingCart,
                                        contentDescription = "Cart",
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .width(30.dp)
                                            .height(35.dp)
                                    )
                                    if (cartItems.isNotEmpty())
                                        Card(
                                            modifier = Modifier.align(
                                                alignment = Alignment.TopCenter
                                            ).padding(3.dp)
                                                .padding(end = 10.dp),

                                            colors = CardDefaults.cardColors(
                                                containerColor = Color.Transparent
                                            )
                                        ){
                                            Text(text = cartItems.size.toString(),
                                                fontSize = 10.sp,
                                                color = Color.Black,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.
                                                padding(1.dp)
                                                    .offset(y = (-4).dp)
                                            )
                                        }
                                }
                                //Text(text = "Cart", fontSize = 10.sp)
                            }
                            //
                        }
                    },
                    navigationIcon = {
                        if (canNavigateBack){
                            IconButton(onClick = {
                                navController.navigateUp()
                            }){
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back Button")
                            }
                        }

                    }
                )
            },
            bottomBar = { AppBar(navController = navController,
                currentScreen = currentScreen,
                cartItems = cartItems
                )}
        ){
            NavHost(navController = navController,
                startDestination = FirstAppScreen.Start.name,
                Modifier.padding(it)
            ){
                composable(route = FirstAppScreen.Start.name){
                    StartScreen(
                        appViewModel = appViewModel,
                        onCategoryClicked = {
                            appViewModel.updateSelectedCategory(it)
                            navController.navigate(FirstAppScreen.Item.name)
                        })
                }
                composable(route = FirstAppScreen.Item.name){
                    InternetItemsScreen(appViewModel = appViewModel,
                        itemUiState = appViewModel.itemUiState
                        )
                }
                composable(route = FirstAppScreen.Cart.name ){
                    CartScreen(appViewModel = appViewModel,
                        onHomeButtonClicked = {
                            navController.navigate(FirstAppScreen.Start.name){
                                popUpTo(0)
                            }
                        })
                }
                composable(route = FirstAppScreen.Profile.name) {
                    ProfileScreen(appViewModel = appViewModel,
                        navController= navController)
                }
            }
        }
        if (logoutClicked){
            AlertBox(onYesButtonPressed = {
                appViewModel.setLogoutStatus(false)
                auth.signOut()
                appViewModel.clearData()
            },
                onNoButtonPressed = {
                    appViewModel.setLogoutStatus(false)
                }
            )
        }
    }
}
@Composable
fun AppBar(
    navController: NavHostController,
    currentScreen: FirstAppScreen,
    cartItems: List<InternetItem>,
    ){
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable {
                navController.navigate(FirstAppScreen.Start.name)
                {
                    popUpTo(0)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = "Home",
                        modifier = Modifier
                        .width(30.dp)
                    .height(35.dp)
            )
        }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.clickable {
                    navController.navigate(FirstAppScreen.Start.name){
                        popUpTo(0)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Search",
                    modifier = Modifier
                        .width(30.dp)
                        .height(35.dp)
                )
            }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.clickable {

                    navController.navigate(FirstAppScreen.Profile.name) {
                    }

            }
        ){
            Icon(imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Account",
                modifier = Modifier
                    .width(30.dp)
                    .height(35.dp)
            )
        }
    }

}
@Composable
fun AlertBox(
    onYesButtonPressed: ()-> Unit,
    onNoButtonPressed:()-> Unit
){
    AlertDialog(
        title = {
            Text(text = "Logout", fontWeight = FontWeight.Bold )
        },
        containerColor = Color.White,
        text = {
            Text(text = "Are you sure ")
        },
        confirmButton = {
            TextButton(onClick = {
                onYesButtonPressed()
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onNoButtonPressed()
            }) {
                Text(text = "No")
            }
        },
        onDismissRequest = {
            onNoButtonPressed
        }
    )
}