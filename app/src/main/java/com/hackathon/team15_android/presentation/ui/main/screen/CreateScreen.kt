package com.hackathon.team15_android.presentation.ui.main.screen


import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hackathon.team15_android.R
import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.presentation.ui.main.ChoiceData
import com.hackathon.team15_android.presentation.ui.main.EdgeData
import com.hackathon.team15_android.presentation.ui.main.MainViewModel
import com.hackathon.team15_android.presentation.ui.main.NodePosition
import com.hackathon.team15_android.presentation.ui.main.TreeNode
import com.hackathon.team15_android.presentation.ui.main.item.NavItem
import com.hackathon.team15_android.presentation.ui.main.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun CreateScreen(
    mainViewModel : MainViewModel,
    viewModel : EditViewModel = hiltViewModel(),
    navController : NavController
) {

    val choiceList by rememberUpdatedState(newValue = mainViewModel.choiceListArr[mainViewModel.currentNode.id])
    val context = LocalContext.current
    val isChanged by rememberUpdatedState(newValue = viewModel.isChanged)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(

        ){
            CreateTopBar()
            CreateContent(
                mainViewModel
            )
        }
//        BottomChoice(mainViewModel.choiceList)

        androidx.compose.material.Button(
            onClick = {
                navController.navigate(NavItem.Publication.route)
            },
            colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color.Gray),

            modifier = Modifier
                .padding(top = 20.dp, start = 18.dp, end = 18.dp, bottom = 20.dp)
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            androidx.compose.material.Text(
                text = "이야기 생성하기",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontSize = 18.sp,
            )
        }


    }

}


@Composable
fun CreateTopBar(
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,

        ){

        androidx.compose.material3.Text(
            text = "이야기 생성하기",
            color = Color(0xFF000000),
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,

            )
    }
}


@Composable
fun CreateContent(
    mainViewModel: MainViewModel
){
    var titleValue by remember { mutableStateOf(mainViewModel.storyTitle) }
    var summaryValue by remember { mutableStateOf(mainViewModel.storySummary) }

    LaunchedEffect(titleValue){
        mainViewModel.storyTitle = titleValue
    }

    LaunchedEffect(summaryValue){
        mainViewModel.storySummary = summaryValue
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            value = titleValue,
            onValueChange = {
                titleValue = it
            },
            decorationBox = { innerTextField ->
                if(titleValue.isEmpty()){
                    Text(
                        "제목을 입력해주세요",
                        color = Color(0xFFA5A5A5),
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
                else {
                    Text(
                        titleValue,
                        color = Color(0xFF000000),
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )

        )

        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            value = summaryValue,
            onValueChange = {
                summaryValue = it
            },
            decorationBox = { innerTextField ->
                if(summaryValue.isEmpty()){
                    Text(
                        "내용을 입력해주세요",
                        color = Color(0xFFA5A5A5),
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
                else {
                    Text(
                        summaryValue,
                        color = Color(0xFF000000),
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )

        )

    }
}



