package com.example.firstapp.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstapp.R
import com.example.firstapp.data.DataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StartScreen(
    appViewModel: AppViewModel,
    onCategoryClicked: (Int) -> Unit
) {
    LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item(
            span = { GridItemSpan(2) }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AutoScrollingBanner() // Auto-scrolling banner with indicators
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(108, 194, 111, 255)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Shop by Category",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                }
            }
        }

        items(DataSource.loadCategories()) {
            CategoryCard(
                stringResourceId = it.stringResourcesId,
                imageResourceId = it.imageResourceId,
                appViewModel = appViewModel,
                onCategoryClicked = onCategoryClicked
            )
        }
    }
}

//  Auto Scrolling Banner with Indicator Dots
@Composable
fun AutoScrollingBanner() {
    val bannerImages = listOf(
        R.drawable.megasale,
        R.drawable.supersale,
        R.drawable.chips,
        R.drawable.for_skin,
        R.drawable.off10,
        R.drawable.summer_sale,
        R.drawable.v_sale
    )

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentIndex by remember { mutableIntStateOf(0) }
    var progress by remember { mutableFloatStateOf(0f) }


    LaunchedEffect(Unit) {
        while (true) {
            progress = 0f

            while (progress < 1.5f){
                delay(50)
                    progress +=0.01f
                }
            coroutineScope.launch {
                currentIndex = (currentIndex + 1) % bannerImages.size
                listState.animateScrollToItem(currentIndex)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(bannerImages) { _, imageRes ->
                BannerItem(imageRes)
            }
        }

        // Progress Indicators
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            bannerImages.forEachIndexed { index, _ ->
                val fillProgress by animateFloatAsState(
                    targetValue = if (currentIndex == index) progress else 0f,
                    label = "Progress"
                )
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(4.dp)
                        .width(20.dp)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(50))
                        .background(if (currentIndex == index) Color.LightGray else Color.LightGray)
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxSize(fillProgress) // Progress fills
                            .background(Color.DarkGray)
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(50))
                    )
                }
            }
        }
    }
}

// Banner Item with Rounded Corners
@Composable
fun BannerItem(imageRes: Int) {
    Card(
        shape = RoundedCornerShape(16.dp), // Proper rounded corners
        modifier = Modifier
            .width(350.dp)
            .height(140.dp)
            .padding(horizontal = 11.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Banner Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)) // Ensures the image is rounded
        )
    }
}

// Category Card UI
@Composable
fun CategoryCard(
    stringResourceId: Int,
    imageResourceId: Int,
    appViewModel: AppViewModel,
    onCategoryClicked: (Int) -> Unit
) {
    val categoryName = stringResource(id = stringResourceId)
    Card(
        modifier = Modifier.clickable {
            appViewModel.updateClickText(categoryName)
            onCategoryClicked(stringResourceId)
        },
        colors = CardDefaults.cardColors(containerColor = Color(248, 221, 248, 255))
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = categoryName,
                fontSize = 17.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(imageResourceId),
                contentDescription = "Category Image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp)) // Makes images rounded
            )
        }
    }
}
