package com.hackathon.team15_android.presentation.ui.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackathon.team15_android.presentation.ui.main.util.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    var nodeList = mutableListOf<TreeNode>()

    var id = 1

    var rootNode = TreeNode(
        id++,
        "시작 노드 입니다",
        depth = 0,
        width = 0
    ).also {
        nodeList.add(it)
    }

    var currentNode : TreeNode = rootNode

//    var previousNode : TreeNode? = null

    var storyTitle : String = "살아야만 한다"

    var choiceListArr = Array(1000) { mutableListOf<ChoiceData>() }


















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

    fun addExistedNode(prevNode : TreeNode, node : TreeNode, choice : Int, choiceText : String){

        prevNode.choice[choice-1] = ChoiceData(node, choiceText)

        viewModelScope.launch(Dispatchers.Main){
            makePosition()
        }
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

data class NodePosition(
    val node : TreeNode,
    val x : Int,
    val y : Int
)

data class EdgeData(
    var prevNode : TreeNode,
    var node : TreeNode
)