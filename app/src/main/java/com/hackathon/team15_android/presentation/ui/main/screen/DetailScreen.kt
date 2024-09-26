package com.hackathon.team15_android.presentation.ui.main.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hackathon.team15_android.R
import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.data.repository.PostRepository
import com.hackathon.team15_android.presentation.ui.main.MainViewModel
import com.hackathon.team15_android.presentation.ui.main.item.NavItem


@Composable
fun DetailLibraryScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {

    val scrollState = rememberScrollState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(){
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 26.dp, top = 23.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apo),
                    contentDescription = " Detail Library page Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
                Text(
                    text = mainViewModel.currentStory!!.title,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 18.dp, top = 15.dp)
                )

                Text(
                    text = mainViewModel.currentStory!!.summary,
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
                mainViewModel.getDetailPost(mainViewModel.currentStory!!.postId.toLong(),1)
//                mainViewModel.currentPage = DetailPostResponse("허허허",1,"앞으로 간다",2,"뒤로 간다",0,"")
                navController.navigate(NavItem.Story.route)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),

            modifier = Modifier
                .padding(top = 20.dp, start = 18.dp, end = 18.dp, bottom = 20.dp)
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



}


@Preview(showBackground = true)
@Composable
fun PreviewDetail() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(){
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 26.dp, top = 23.dp)
                    .clickable {
                    }
            )

            Column(
                modifier = Modifier
                    .weight(1F)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.apo3),
                    contentDescription = " Detail Library page Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
                Text(
                    text = "mainViewModel.currentStory!!.title",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 18.dp, top = 15.dp)
                )

                Text(
                    text = "mainViewModel.currentStory!!.summary",
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
}