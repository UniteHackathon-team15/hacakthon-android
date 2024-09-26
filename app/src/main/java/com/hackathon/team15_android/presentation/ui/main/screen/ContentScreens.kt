package com.hackathon.team15_android.presentation.ui.main.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.hackathon.team15_android.R
import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.presentation.ui.main.ChoiceData
import com.hackathon.team15_android.presentation.ui.main.MainViewModel
import com.hackathon.team15_android.presentation.ui.main.RouteData
import com.hackathon.team15_android.presentation.ui.main.data.Test
import com.hackathon.team15_android.presentation.ui.main.data.TestDataProvider
import com.hackathon.team15_android.presentation.ui.main.item.NavItem
import com.hackathon.team15_android.presentation.ui.main.util.TAG
import com.hackathon.team15_android.presentation.viewmodel.PostListViewModel
import kotlinx.coroutines.launch


@Composable
fun ChoiceItem(
    data : RouteData,
    index : Int,
    onClick : (i : Int) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        androidx.compose.material3.Text(
            text = data.text,
            color = Color(0xFF5B5B5B),
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                onClick(data.id)
            }
        )

    }
}

fun makeChoiceList(
    currentPage : DetailPostResponse?,
) : List<RouteData>?{

    if (currentPage == null) return null

    val result = mutableListOf<RouteData>()

    if(currentPage.firstOptionItem != 0 && currentPage.firstOptionContent != ""){
        result.add(RouteData(currentPage.firstOptionItem,currentPage.firstOptionContent))
    }
    if (currentPage.secondOptionItem != 0 && currentPage.secondOptionContent != ""){
        result.add(RouteData(currentPage.secondOptionItem,currentPage.secondOptionContent))
    }
    if (currentPage.thirdOptionItem != 0 && currentPage.thirdOptionContent != ""){
        result.add(RouteData(currentPage.thirdOptionItem,currentPage.thirdOptionContent))
    }
    if (result.isEmpty()) return null

    return result

}

@Composable
fun StoryContent(
    mainViewModel : MainViewModel,
    currentPage : DetailPostResponse?,
    choiceList : List<RouteData>?,
    isChanged : Boolean
){
    if(isChanged){

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column{
            Text(
                text = mainViewModel.currentStory!!.title,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(24.dp)
            )

            Column() {
                Image(
                    painter = painterResource(id = R.drawable.apo2),
                    contentDescription = "novel image",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (currentPage != null) {
                    Text(
                        text = currentPage!!.content,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                    )
                } else {
                    Text(
                        text = "이야기가 존재하지 않습니다",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                    )
                }

            }
        }

        Column{
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Black))

            if (choiceList != null) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(
                        choiceList
                    ) { index: Int, item: RouteData ->
                        ChoiceItem(item, index, {
                        mainViewModel.getDetailPost(mainViewModel.currentStory!!.postId.toLong(),it.toLong())
//                            mainViewModel.currentPage =
//                                DetailPostResponse("하하하", 1, "앞으로 안간다", 0, "", 0, "")
                            mainViewModel.isChanged = !isChanged
                        })
                    }
                }
            }
        }


    }
}

@Composable
fun StoryScreen(
    mainViewModel: MainViewModel
) {

    val currentPage by rememberUpdatedState(newValue = mainViewModel.currentPage)

    val scrollState = rememberScrollState()

    val isChanged by rememberUpdatedState(newValue = mainViewModel.isChanged)

    val choiceList = makeChoiceList(currentPage)
    Log.d(TAG,"choiceList : ${choiceList}")

    StoryContent(
        mainViewModel = mainViewModel,
        currentPage = currentPage,
        choiceList = choiceList,
        isChanged)

}



@Preview(showBackground = true)
@Composable
fun Preview() {
//    StoryScreen()
}





