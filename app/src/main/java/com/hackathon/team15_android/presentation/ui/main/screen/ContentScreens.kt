package com.hackathon.team15_android.presentation.ui.main.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.hackathon.team15_android.R
import com.hackathon.team15_android.presentation.ui.main.data.Test
import com.hackathon.team15_android.presentation.ui.main.data.TestDataProvider
import com.hackathon.team15_android.presentation.ui.theme.Black

@Composable
fun LibraryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            val svgImage: Painter = painterResource(R.drawable.ic_library)
            Image(
                painter = svgImage,
                contentDescription = "Library page icon SVG Image",
                modifier = Modifier
                    .padding(start = 26.dp, top = 23.dp)
            )

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = "채종인 도서관",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Color.Black,
                ),
                modifier = Modifier
                    .padding(top = 24.dp)
            )
        }
        LibraryLazyColumn()
    }
}

@Composable
fun LibraryLazyColumn() {
    val testItems = remember { TestDataProvider.libraryList }
    LazyColumn(contentPadding = PaddingValues(16.dp, 8.dp)) {
        items(
            items = testItems,
            itemContent = { TestListItem(it) }
        )
    }
}

@Composable
fun TestListItem(test: Test) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 12.dp),
        elevation = 4.dp,
    ) {
        Row {
            LibraryImage(test = test)
            Column (
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = test.name)
                Text(text = test.content, modifier = Modifier.padding(0.dp, 2.dp))

            }
        }

    }
}

@Composable
fun LibraryImage(test: Test) {
    AsyncImage(
        model = test.image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun LibraryScreenPreView() {
    LibraryScreen()
}


@Composable
fun StoryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {

    }
}

@Composable
fun PublicationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {

    }
}