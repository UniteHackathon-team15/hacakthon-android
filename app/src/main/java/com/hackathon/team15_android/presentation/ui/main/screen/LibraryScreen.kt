package com.hackathon.team15_android.presentation.ui.main.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hackathon.team15_android.R
import com.hackathon.team15_android.data.remote.dto.response.post.PostResponse
import com.hackathon.team15_android.presentation.ui.main.data.Test
import com.hackathon.team15_android.presentation.ui.main.item.NavItem
import com.hackathon.team15_android.presentation.ui.main.util.shadow
import com.hackathon.team15_android.presentation.viewmodel.PostListViewModel


@Composable
fun LibraryScreen(
    navController: NavController,
    mainViewModel: com.hackathon.team15_android.presentation.ui.main.MainViewModel,
    postListViewModel: PostListViewModel = hiltViewModel(),
) {

    val postList by rememberUpdatedState(newValue = postListViewModel.postList)

    postListViewModel.getPostList()


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
                painter = painterResource(R.drawable.ic_library),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 26.dp, top = 23.dp)
            )

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = "ROUTE",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Color.Black,
                ),
                modifier = Modifier
                    .padding(top = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        StoryGridView(
            list = postList,
            onItemClick = {
                mainViewModel.currentStory = it
                navController.navigate(NavItem.Detail.route)
            }
        )

    }
}

@Preview
@Composable
fun PreviewLibarary(){

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
                painter = painterResource(R.drawable.ic_library),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 26.dp, top = 23.dp)
            )

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = "ROUTE",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Color.Black,
                ),
                modifier = Modifier
                    .padding(top = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            StoryGridView(
                list = listOf<PostResponse>(
                    PostResponse(1,"세상에 이런일이","이떄ㅏ낒 이런 일은 없었다",""),
                    PostResponse(1,"세상에 이런일이","이떄ㅏ낒 이런 일은 없었다","")),
                onItemClick = {}
            )
        }
    }

}

@Composable
fun StoryGridView(
    list : List<PostResponse>?,
    onItemClick : (item : PostResponse) -> Unit
){

    if(list != null){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
        ) {
            itemsIndexed(
                list
            ) { index: Int, item: PostResponse ->
                ListItem(item, index, onItemClick)
            }
        }
    }
    else {
        Text(
            text = "이야기가 없습니다",
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )
    }

}

@Composable
fun ListItem(
    data : PostResponse,
    index : Int,
    onItemClick : (item : PostResponse) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onItemClick(data)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .shadow(
                    color = Color.LightGray,
                    offsetY = 2.dp,
                    blurRadius = 5.dp
                    )
                .height(160.dp)
                .width(160.dp)
                .background(Color.White)
        ){
            Image(
                painter = painterResource(id = R.drawable.apo),
                contentDescription = "",
                modifier = Modifier
                    .width(160.dp)
                    .height(70.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = data.title,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 5.dp).padding(top = 5.dp)
            )

            Text(
                text = data.summary,
                color = Color.Gray,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
    }

}

@Preview
@Composable
fun PreviewItem(){
    ListItem(
        PostResponse(1,"세상에 이런일이","이떄ㅏ낒 이런 일은 없었다",""),
        1,
        {}
    )
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