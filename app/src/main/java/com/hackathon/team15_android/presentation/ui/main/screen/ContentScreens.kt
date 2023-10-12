package com.hackathon.team15_android.presentation.ui.main.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.hackathon.team15_android.R
import com.hackathon.team15_android.presentation.ui.main.data.Test
import com.hackathon.team15_android.presentation.ui.main.data.TestDataProvider
import com.hackathon.team15_android.presentation.ui.main.item.NavItem
import com.hackathon.team15_android.presentation.viewmodel.PostListViewModel
import kotlinx.coroutines.launch

@Composable
fun LibraryScreen(
    navController: NavController,
    postListViewModel: PostListViewModel,
) {
    var selectedItem by remember { mutableStateOf<Test?>(null) }
    val svgImage: Painter = painterResource(R.drawable.ic_library)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
        ) {
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
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            LibraryLazyColumn(navController) { test ->
                selectedItem = test
            }
        }
    }
    selectedItem?.let {
        DetailLibraryScreen(navController = navController)
    }
}


@Composable
fun LibraryLazyColumn(navController: NavController, onItemClick: (Test) -> Unit) {
    val testItems = remember { TestDataProvider.libraryList }
    LazyColumn(contentPadding = PaddingValues(16.dp, 8.dp)) {
        items(
            items = testItems,
            itemContent = { TestListItem(it, onItemClick) }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TestListItem(test: Test, onItemClick: (Test) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    for (i in 1..30) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 12.dp),
            elevation = 4.dp,
            onClick = {
                coroutineScope.launch {
                    onItemClick(test)
                }
            }
        ) {
            Row {
                LibraryImage(test = test)
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = test.name,
                        modifier = Modifier
                    )
                    Text(text = test.content, modifier = Modifier.padding(0.dp, 2.dp))
                }
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

@Composable
fun StoryScreen() {
    var page by remember { mutableStateOf(1) }
    val imageModifier = Modifier
        .fillMaxWidth()

    val textButtonModifier = Modifier
        .width(390.dp)
        .height(58.dp)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Text(
            text = "모솔의 사랑이야기 - $page",
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(24.dp)
        )

        Column(
            modifier = Modifier
                .weight(1F)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.test2_image),
                contentDescription = "novel image",
                modifier = imageModifier,
                contentScale = ContentScale.FillWidth,
            )

            Text(
                text = "모솔의 사랑이야기",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 18.dp, top = 15.dp)
            )

            Text(
                text = "2023년, 어느 모솔의 사랑이야기가 온다 과연 주인공은 사랑을 쟁취할 수 있을까?",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 18.dp, top = 4.dp, end = 18.dp)
            )
        }

        TextButton(onClick = { page += 1 }) {
            Text(
                text = "다음",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontSize = 16.sp,
                modifier =
                textButtonModifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.White),
                            startY = 0f,
                            endY = 2f,
                        )
                    )
                    .padding(5.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    PublicationScreen()
}


@Composable
fun PublicationScreen() {
    var text by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Text(
            text = "이야기 생성하기",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            modifier = Modifier
                .padding(start = 18.dp, top = 23.dp)
        )
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 84.dp, bottom = 486.dp, end = 16.dp)
                .height(height = 178.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.gray), shape = RoundedCornerShape(16.dp)),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add_local_image),
                contentDescription = "Add Local Image Button",
                modifier = Modifier
                    .clickable {

                    }
                    .padding(top = 59.dp, bottom = 59.dp)
                    .height(60.dp)
                    .width(60.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_separator),
                contentDescription = "Add Image Button Separator",
                modifier = Modifier
                    .padding(start = 35.dp, top = 44.dp, bottom = 44.dp)
                    .height(100.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_add_ai_image),
                contentDescription = "Add AI Image Button Separator",
                modifier = Modifier
                    .padding(top = 59.dp, start = 25.dp)
            )

        }
        Column(

        ) {

        }
    }
}

@Composable
fun DetailLibraryScreen(navController: NavController) {
    val imageModifier = Modifier
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {
        Image(
            painter = painterResource(R.drawable.ic_back_button),
            contentDescription = "Detail Library page icon SVG Image",
            modifier = Modifier
                .padding(start = 26.dp, top = 23.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Column(
            modifier = Modifier
                .weight(1F)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.test1_image),
                contentDescription = " Detail Library page Image",
                contentScale = ContentScale.FillWidth,
                modifier = imageModifier
                    .padding(top = 24.dp)
            )
            Text(
                text = "모솔의 사랑이야기",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 18.dp, top = 15.dp)
            )

            Text(
                text = "2023년, 어느 모솔의 사랑이야기가 온다 과연 주인공은 사랑을 쟁취할 수 있을까?",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 18.dp, top = 4.dp, end = 18.dp)
            )

        }

    }
    Button(
        onClick = {
            navController.navigate(NavItem.Story.route)
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),

        modifier = Modifier
            .padding(top = 700.dp, start = 18.dp, end = 18.dp, bottom = 20.dp)
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.white),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = "이야기 체험하기",
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontSize = 18.sp,
        )
    }
}
