package com.example.catalogdemo.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.catalogdemo.R
import com.example.catalogdemo.domain.model.ProductDetail
import com.example.catalogdemo.domain.model.ProductItem

@ExperimentalMaterial3Api
@Composable
fun ProductScreen(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {}, // No title needed
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(painter = painterResource(R.drawable.outline_arrow_back_24),
                            contentDescription = "Back")
                    }

                }
            )
        },
        bottomBar = {
            BottomAppBar(
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button( onClick = { /* Add to cart, etc */ },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(189,37,154))
                    ) {
                        Text("Add To Cart", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    }
                    Button( onClick = { /* Buy now, etc */ },
                        shape = RectangleShape) {
                        Text("Buy Now",  style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    }
                }

            }
        }
    ) { innerPadding ->
       Column(modifier = Modifier.padding(innerPadding)){
           ProductImages(dummyItem.images)
           ProductInfo(dummyItem)
       }
    }
}

@Composable
fun ProductImages(images: List<String>){
    val pagerState = rememberPagerState(pageCount = {images.size})
    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        Column {
            HorizontalPager(
                state = pagerState
            ) { index ->
                AsyncImage(
                    model = images[index],
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                )
            }

            Row(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
                    .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(images.size) { iteration ->
                    val isSelected = pagerState.currentPage == iteration
                    val color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)
                    val size = if (isSelected) 10.dp else 8.dp

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(size)
                            .clip(CircleShape)
                            .background(color)
                            .align(Alignment.CenterVertically)
                    )
                }
            }

        }

    }
}

@Composable
fun ProductInfo(item: ProductDetail){
    Column(modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = item.title,
             style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium)
        Text(text = "RM${String.format(locale = Locale.UK, "%.2f", item.price)}",
            style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)

        Text("Product Description", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
        Text(text = item.description, style = MaterialTheme.typography.bodyMedium)

        Text("Category", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        Text(item.category, style = MaterialTheme.typography.bodyMedium)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("${item.rating}", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
            Icon(painter = painterResource(R.drawable.star_rate),
                contentDescription = "Rating",
                tint = Color.Unspecified)
            Text("Reviews", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


val dummyItem = ProductDetail(
    id = 1,
    title = "Dummy Product",
    description = "Dummy Product Description",
    category = "Dummy",
    images = listOf("app/src/main/res/drawable/birdie.webp", "app/src/main/res/drawable/toucan.webp"),
    price = 52.80f,
    rating = 3.4f,
)

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewProductDetail(){
    ProductScreen()
}