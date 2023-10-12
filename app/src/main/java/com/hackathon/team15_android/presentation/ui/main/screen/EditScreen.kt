package com.hackathon.team15_android.presentation.ui.main.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material.TextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hackathon.team15_android.R
import com.hackathon.team15_android.presentation.ui.main.ChoiceData
import com.hackathon.team15_android.presentation.ui.main.MainViewModel
import okhttp3.internal.wait

@Composable
fun EditScreen(
    mainViewModel : MainViewModel,
    viewModel : EditViewModel = hiltViewModel()
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
            TopBar(mainViewModel.storyTitle, mainViewModel.currentNode.id)
            EditContent(
                {
                    mainViewModel.currentNode.text = it
                },
                mainViewModel
            )
        }

        fun selectChoice(choice : List<ChoiceData>) : Int?{
            if (choice.size <= 2 ) return choice.size+1
            else return null
        }
//        BottomChoice(mainViewModel.choiceList)
        BottomChoice(
            choiceList,
            { text ->

                val choice = selectChoice(choiceList)

                if (choice == null) Toast.makeText(context,"선택지는 3개까지 만들 수 있습니다",Toast.LENGTH_LONG).show()
                else {
                    val node = mainViewModel.addNode(
                        mainViewModel.currentNode,
                        "",
                        choice,
                        text
                    )
                    choiceList.add(
                        ChoiceData(node,text)
                    )

                }
            },
            isChanged,
            viewModel = viewModel
        )

    }

}

@Preview
@Composable
fun PreviewEditScreen(){

    EditScreen(MainViewModel())
}

@Composable
fun TopBar(
    title : String,
    id : Int
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,

    ){

        Image(
            modifier = Modifier
                .height(24.dp)
                .width(24.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = ""
        )
        
        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = "${title} - ${id}",
            color = Color(0xFF000000),
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,

        )
    }
}

@Composable
fun EditContent(
    onTextChange : (text : String) -> Unit,
    mainViewModel: MainViewModel
){
    var textValue by remember { mutableStateOf(mainViewModel.currentNode.text) }

    LaunchedEffect(textValue){
        onTextChange(textValue)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            value = textValue,
            onValueChange = {
                textValue = it
            },
            decorationBox = { innerTextField ->
                if(textValue.isEmpty()){
                    Text(
                        "내용을 입력해주세요",
                        color = Color(0xFFA5A5A5),
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
                else {
                    Text(
                        textValue,
                        color = Color(0xFF000000),
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

        )

    }
}

@Composable
fun ListItem(
        data : ChoiceData,
        index : Int,
        onRemoveClick : (i : Int) -> Unit
    ){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        Text(
            text = "${data.text} -> ${data.DestinationNode.id}",
            color = Color(0xFF5B5B5B),
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        )

        Image(
            modifier = Modifier
                .height(20.dp)
                .width(20.dp)
                .clickable {
                    onRemoveClick(index)
                },
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "",
        )
    }
}

@Composable
fun BottomChoice(
    choiceList : MutableList<ChoiceData>,
    onAddNode : (text : String) -> Unit,
    isChanged : Boolean,
    viewModel: EditViewModel
){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ){

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .height(2.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ){
            itemsIndexed(
                choiceList
            ){index: Int, item: ChoiceData ->

                ListItem(item,index,{})

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,

        ){

            var textValue by remember{ mutableStateOf("") }

            BasicTextField(
                modifier = Modifier,
                value = textValue,
                onValueChange = {
                    textValue = it
                },
                decorationBox = { innerTextField ->
                    if(textValue.isEmpty()){
                        Text(
                            "내용을 입력해주세요",
                            color = Color(0xFFA5A5A5),
                            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }
                    else {
                        Text(
                            textValue,
                            color = Color(0xFF000000),
                            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            )

            Spacer(modifier = Modifier.width(5.dp))

            Image(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .clickable {
                        onAddNode(textValue)
                        textValue = ""
                        viewModel.isChanged = !isChanged
                    },
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = ""
            )


        }


    }



}