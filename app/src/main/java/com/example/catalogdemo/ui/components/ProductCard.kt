package com.example.catalogdemo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.catalogdemo.R
import com.example.catalogdemo.domain.model.ProductItem

@Composable
fun ProductCard(item: ProductItem, navToDetail: (Int) -> Unit){
    Card(
        onClick = { navToDetail(item.id) }
    ){
        Column(modifier = Modifier.padding(12.dp)){
            AsyncImage(
                item.thumbnail,
                contentDescription = item.title
            )
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
            Text(
                text = "RM${item.price}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun SampleProductCard() {
    Card{
        Column(modifier = Modifier.padding(12.dp)){
            AsyncImage(
                R.drawable.ic_launcher_background,
                contentDescription = "Android Background"
            )
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Android Background",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
            Text(
                text = "RM9.99",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview
@Composable
fun PreviewCard(){
    SampleProductCard()
}
