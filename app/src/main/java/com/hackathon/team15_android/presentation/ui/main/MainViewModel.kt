package com.hackathon.team15_android.presentation.ui.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackathon.team15_android.data.remote.dto.PostStoryRequest
import com.hackathon.team15_android.data.remote.dto.response.post.DetailPostResponse
import com.hackathon.team15_android.data.remote.dto.response.post.PostResponse
import com.hackathon.team15_android.data.repository.AiRepository
import com.hackathon.team15_android.data.repository.PostRepository
import com.hackathon.team15_android.presentation.ui.main.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val aiRepository: AiRepository
): ViewModel() {

    var nodeList = mutableListOf<TreeNode>()

    var id = 1

    var rootNode = TreeNode(
        id++,
        "",
        depth = 0,
        width = 0
    ).also {
        nodeList.add(it)
    }

    var currentNode : TreeNode = rootNode

//    var previousNode : TreeNode? = null

    var storyTitle : String = ""

    var storySummary : String = ""

    var choiceListArr = Array(1000) { mutableListOf<ChoiceData>() }

    var currentStory : PostResponse? = null

    var currentPage : DetailPostResponse? = null

    var aiText = mutableStateOf("")

    fun findNodeById(id : Int) : TreeNode {
        return nodeList.find { it.id == id }!!
    }

    fun getDetailPost(storyId : Long, pageId : Long) = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
            postRepository.getDetailPost(
                postDetailId = pageId,
                postId = storyId
            )
        }.onSuccess {
            currentPage = it

        }.onFailure {
            Log.d(TAG, "getDetailPost에러 - $it ")
        }
    }

    fun getFirstStory(question : String) = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
            aiRepository.getFirstStory(
                question
            )
        }.onSuccess {
            aiText.value = it
            Log.d(TAG,"MainViewModel - getFirstStory() : $it")
        }.onFailure {
            Log.d(TAG, "getFirstStory에러 - $it ")
        }
    }

    fun postStory(storyRequest: PostStoryRequest) : Boolean {

        var result = false

        viewModelScope.launch(Dispatchers.IO){



            kotlin.runCatching {
                postRepository.postStory(storyRequest)
            }.onSuccess {
                nodePositionList.clear()
                positionArr = Array<Array<TreeNode?>>(1000){
                    Array<TreeNode?>(1000){null}
                }
                edgeList.clear()
                widthArr = Array(1000){0}
                edgeArr = Array(1000){Array<EdgeData?>(3){null} }

                result = true

                Log.d(TAG,"MainViewModel - postStory() : $it")
            }.onFailure {
                Log.d(TAG, "postStory에러 - $it ")

            }

        }

        return result

    }


    var positionArr by mutableStateOf(Array<Array<TreeNode?>>(1000){
        Array<TreeNode?>(1000){null}
    })

    var nodePositionList = mutableListOf<NodePosition>()

    var edgeList = mutableListOf<EdgeData>()

    var widthArr = Array(1000){0}

    var edgeArr = Array(1000){Array<EdgeData?>(3){null} }

    var isChanged by mutableStateOf(false)

    fun addNode(prevNode : TreeNode, text : String, choice : Int, choiceText : String) : TreeNode{


        val node = TreeNode(id++, text, depth = prevNode.depth+1, width = 0)

        nodeList.add(node)

        prevNode.choice[choice-1] = ChoiceData(node, choiceText)

        Log.d(TAG, "addNode: ")

        viewModelScope.launch(Dispatchers.Main){
            makePosition()
        }

        return node

    }

//    fun addExistedNode(prevNode : TreeNode, node : TreeNode, choice : Int, choiceText : String){
//
//        prevNode.choice[choice-1] = ChoiceData(node, choiceText)
//
//        viewModelScope.launch(Dispatchers.Main){
//            makePosition()
//        }
//
//        val choiceList = choiceListArr[currentNode.id]
//        choiceList.add(
//            ChoiceData(node,choiceText)
//        )
//    }

    fun addExistedNode(prevNode: TreeNode, node: TreeNode, choice: Int, choiceText: String) {
        prevNode.choice[choice - 1] = ChoiceData(node, choiceText)

        // 기존 노드를 추가할 때 깊이 및 너비 충돌을 방지
        viewModelScope.launch(Dispatchers.Main) {
            // 이미 배치된 노드들 사이에서 겹침을 방지하기 위해 하위 노드들을 아래로 밀어내기
//            if (prevNode.depth >= node.depth) {
//                makeChildDown(node, prevNode.depth - node.depth + 1)
//            }
            // 전체 노드 배치 업데이트
            makePosition()
        }

        // 선택지 추가
        val choiceList = choiceListArr[currentNode.id]
        choiceList.add(ChoiceData(node, choiceText))
    }

    suspend fun makeChildDown(node : TreeNode, how : Int){

        for (i in node.choice){
            if (i != null){
                makeChildDown(i.DestinationNode,how)
            }
        }

        println("${node.depth}, ${node.width} - ${positionArr[node.depth][node.width]!!.text} -> null")
        positionArr[node.depth][node.width] = null
        widthArr[node.depth]--
        println("depth : ${node.depth}")
        node.depth += how
        println("depth : ${node.depth}")

        node.width = widthArr[node.depth]++
        positionArr[node.depth][node.width] = node
        Log.d(TAG, "${node.depth}, ${node.width} - ${positionArr[node.depth][node.width]!!.text}")

    }

    suspend fun makePosition(){

        widthArr = Array(1000){0}
        edgeArr = Array(1000){Array<EdgeData?>(3){null} }
        positionArr = Array<Array<TreeNode?>>(1000){
            Array<TreeNode?>(1000){null}
        }

        for (i in nodeList){
            i.isVisited = false
        }

        suspend fun recursion(prevNode : TreeNode?, node : TreeNode){

            if(!node.isVisited){
                node.isVisited = true
                node.width = widthArr[node.depth]
                Log.d(TAG, "${node.depth}, ${widthArr[node.depth]} - ${node.text}")
                positionArr[node.depth][widthArr[node.depth]++] = node

            }

            if(prevNode != null){
                if (prevNode.depth >= node.depth) {
                    makeChildDown(node,prevNode.depth - node.depth + 1 )
                }
            }

            for (i in 0..node.choice.size - 1) {
                if (node.choice[i] != null) {

                    if(edgeArr[node.id][i] == null){
                        edgeArr[node.id][i] = EdgeData(node, node.choice[i]!!.DestinationNode)
                        recursion(node,node.choice[i]!!.DestinationNode)
                    }

                }
            }

        }

        recursion(null, rootNode)

        PositionArrToList()
        EdgeArrToList()

        isChanged = !isChanged
    }

//    suspend fun makePosition() {
//        // 초기화
//        widthArr = Array(1000) { 0 }
//        edgeArr = Array(1000) { Array<EdgeData?>(3) { null } }
//        positionArr = Array<Array<TreeNode?>>(1000) {
//            Array<TreeNode?>(1000) { null }
//        }
//
//        for (i in nodeList) {
//            i.isVisited = false
//        }
//
//        suspend fun recursion(prevNode: TreeNode?, node: TreeNode) {
//            if (!node.isVisited) {
//                node.isVisited = true
//                // 너비 충돌 방지: 같은 깊이에 이미 노드가 있을 경우 위치를 조정
//                if (positionArr[node.depth][widthArr[node.depth]] != null) {
//                    makeChildDown(node, 1)
//                }
//                node.width = widthArr[node.depth]++
//                positionArr[node.depth][node.width] = node
//            }
//
//            if (prevNode != null && prevNode.depth >= node.depth) {
//                makeChildDown(node, prevNode.depth - node.depth + 1)
//            }
//
//            for (i in node.choice.indices) {
//                node.choice[i]?.let {
//                    if (edgeArr[node.id][i] == null) {
//                        edgeArr[node.id][i] = EdgeData(node, it.DestinationNode)
//                        recursion(node, it.DestinationNode)
//                    }
//                }
//            }
//        }
//
//        recursion(null, rootNode)
//        PositionArrToList()
//        EdgeArrToList()
//
//        isChanged = !isChanged
//    }

    suspend fun PositionArrToList(){
        nodePositionList.clear()

        for (i in 0..999){
            for (j in 0..999){

                if (positionArr[i][j] != null){

                    Log.d(TAG, "ArrToList: ${positionArr[i][j]!!.text} - ${i}, ${j}")

                    nodePositionList.add(NodePosition(positionArr[i][j]!!,j,i))
                }

            }
        }
    }

    fun EdgeArrToList(){
        edgeList.clear()

        for (i in edgeArr){
            for (j in i){
                if (j != null){
                    edgeList.add(EdgeData(j.prevNode,j.node))
                }
            }
        }

    }

    fun combineData() : PostStoryRequest{

        val list = mutableListOf <RequestData>()

        for (i in positionArr){
            for (j in i){
                if (j != null){
                    list.add(RequestData(
                        stage_id = j.id,
                        content = j.text,
                        first_option_id = if (j.choice[0] == null) 0 else j.choice[0]!!.DestinationNode.id,
                        first_option_content = if (j.choice[0] == null) "" else j.choice[0]!!.text,
                        second_option_id = if (j.choice[1] == null) 0 else j.choice[1]!!.DestinationNode.id,
                        second_option_content = if (j.choice[1] == null) "" else j.choice[1]!!.text,
                        third_option_id = if (j.choice[2] == null) 0 else j.choice[2]!!.DestinationNode.id,
                        third_option_content = if (j.choice[2] == null) "" else j.choice[2]!!.text,

                    ))
                }
            }
        }

        var result = PostStoryRequest(
            title = storyTitle,
            summary = storySummary,
            image = "",
            post_details_list = list
        )

        return result
    }

}



data class TreeNode(
    val id : Int,
    var text : String,
    var choice : Array<ChoiceData?> = arrayOf(null,null,null),
    var depth : Int,
    var width : Int,
    var isVisited : Boolean = false
)

data class ChoiceData(
    var DestinationNode : TreeNode,
    var text: String
)

data class RouteData(
    val id : Int,
    val text : String
)

data class NodePosition(
    val node : TreeNode,
    val x : Int,
    val y : Int
)

data class EdgeData(
    var prevNode : TreeNode,
    var node : TreeNode
)

data class RequestData(
    val stage_id : Int,
    val content : String,
    val first_option_id : Int,
    val first_option_content : String,
    val second_option_id : Int,
    val second_option_content : String,
    val third_option_id : Int,
    val third_option_content : String
)