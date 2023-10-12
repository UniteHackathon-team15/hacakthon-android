package com.hackathon.team15_android.presentation.ui.main.screen


import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.res.painterResource
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
import com.hackathon.team15_android.presentation.ui.main.EdgeData
import com.hackathon.team15_android.presentation.ui.main.MainViewModel
import com.hackathon.team15_android.presentation.ui.main.NodePosition
import com.hackathon.team15_android.presentation.ui.main.TreeNode
import com.hackathon.team15_android.presentation.ui.main.item.NavItem
import com.hackathon.team15_android.presentation.ui.main.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PublicationScreen(

    mainViewModel : MainViewModel,
    viewModel : PublicationViewModel = hiltViewModel(),
    navController: NavController
){

    val coroutineScope = rememberCoroutineScope()
    val positionList by rememberUpdatedState(newValue = mainViewModel.nodePositionList)
    val edgeList by rememberUpdatedState(newValue = mainViewModel.edgeList)

    val isChanged by rememberUpdatedState(newValue = mainViewModel.isChanged)

    val currentNode by rememberUpdatedState(newValue = mainViewModel.currentNode)

    DisposableEffect(Unit){
        coroutineScope.launch(Dispatchers.Main) {
            mainViewModel.makePosition()
        }
        onDispose {  }

    }

    DraggableAndZoomableView(
        positionList,
        edgeList,
        {

//            if (it.choice[0] == null){
//                mainViewModel.addNode(it,"${mainViewModel.id}번째 페이지입니다",1,"")
//                Log.d(TAG, "choice1 - ${mainViewModel.id}번째 페이지입니다")
//
//            }
//            else if (it.choice[1] == null){
//                mainViewModel.addNode(it,"${mainViewModel.id}번째 페이지입니다",2,"")
//                Log.d(TAG, "choice2 - ${mainViewModel.id}번째 페이지입니다")
//            }
//            else if (it.choice[2] == null){
//                mainViewModel.addNode(it,"${mainViewModel.id}번째 페이지입니다",3,"")
//                Log.d(TAG, "choice3 - ${mainViewModel.id}번째 페이지입니다")
//            }
//            else{
//                Log.d(TAG, "미친거 아니야?")
//            }

            mainViewModel.currentNode = it
            mainViewModel.isChanged = !mainViewModel.isChanged

        },
        LocalContext.current,
        isChanged,
        mainViewModel,
        onEditClick = {
            Log.d(TAG," - onEditClick")
            navController.navigate(NavItem.Edit.route)
                      },
        {}
    )
}

@Preview
@Composable
fun PreviewPublicationScreen(){
    PublicationScreen(MainViewModel(), navController = rememberNavController())
}

@Composable
fun DraggableAndZoomableView(
    positionList : MutableList<NodePosition>,
    edgeList: MutableList<EdgeData>,
    onNodeClick : (node : TreeNode) -> Unit,
    context : Context,
    isChanged : Boolean,
    mainViewModel: MainViewModel,
    onEditClick : () -> Unit,
    onPlusClick : () -> Unit
) {
    var offset by remember { mutableStateOf(Offset(0f, 0f)) }
    var scale by remember { mutableStateOf(1f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5F)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->

                        offset = offset.plus(pan)
                        scale *= zoom

                    }
                }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
        ) {

            drawNodes(positionList, edgeList, onNodeClick, context)

            if(isChanged){}
        }

        BottomPage(
            mainViewModel = mainViewModel,
            onEditClick = onEditClick,
            onPlusClick
        )

    }
}

@Composable
fun BottomPage(
    mainViewModel : MainViewModel,
    onEditClick : () -> Unit,
    onPlusClick : () -> Unit
){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column{
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(Color.Black)
            )

            Text(
                text = "${mainViewModel.storyTitle} - ${mainViewModel.currentNode.id}",
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp)
            )
            Text(
                text = mainViewModel.currentNode.text,
                color = Color(0xFF898989),
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(enabled = true) {
                    onEditClick()

                }
            ){
                Image(
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = ""
                )
                Text(
                    text = "편집하기",
                    color = Color(0xFF6574FF),
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    onPlusClick()
                }
            ){
                Image(
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = ""
                )
                Text(
                    text = "존재하는 페이지에 선택지 추가하기",
                    color = Color(0xFF5B5B5B),
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                )
            }
        }



    }
}

@Composable
fun drawNodes(
    positionList : MutableList<NodePosition>,
    edgeList : MutableList<EdgeData>,
    onNodeClick : (node : TreeNode) -> Unit,
    context : Context
){
    for (i in edgeList){
        Canvas(
            modifier = Modifier
                .wrapContentSize()
        ) {
            drawLine(
                color = Color.Black, // 선의 색상을 설정합니다.
                start = Offset(
                    ConvertDPtoPX(context,i.prevNode.width * 100 + 20 + 100).toFloat(),
                    ConvertDPtoPX(context,i.prevNode.depth * 200 + 20 + 100).toFloat()),   // 시작 점을 설정합니다.
                end = Offset(
                    ConvertDPtoPX(context,i.node.width * 100 + 20 + 100).toFloat(),
                    ConvertDPtoPX(context,i.node.depth * 200 + 20 + 100).toFloat()),
                strokeWidth = 10F// 끝 점을 설정합니다.
            )
        }
    }

    for (i in positionList){

        Log.d(TAG, "${i.node.text} - ${i.x}, ${i.y}")

        val node = i.node

        Log.d(TAG,"${node.text} 생성!")

        Box(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .offset(
                    x = (i.x * 100 + 100).dp,
                    y = (i.y * 200 + 100).dp
                )
        ) {

            Button(
                onClick = {
                    onNodeClick(node)
                },
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .align(Alignment.Center),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "${node.id}",
                    color = Color(0xFFFFFFFF),
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            }

        }
    }

}

fun ConvertDPtoPX(context: Context, dp: Int): Int {
    val density = context.resources.displayMetrics.density
    return Math.round(dp.toFloat() * density)
}