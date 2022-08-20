package com.compose.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.StringBuilder
import java.util.*

class MainViewModel:ViewModel() {

    // 历史记录算式
    private val _recordFlow:MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val recordFlow:StateFlow<List<String>> = _recordFlow

    // 当前输入的算式
    private val _calculatingFlow:MutableStateFlow<String> = MutableStateFlow("0")
    val calculatingFlow:StateFlow<String> = _calculatingFlow

    // 输入的操作数
    private val numbers: MutableList<Double> = mutableListOf()
    fun addANum(number:Double){
        numbers.add(number)
        inputtingNum.clear()
    }

    // 输入的操作符
    private val operationList:MutableList<Operation> = mutableListOf()

    fun addOperation(operation:Operation){
        if (operation == Operation.Minus){
            if (numbers.size > operationList.size){
                // -号 减号
                operationList.add(Operation.Minus)
            }else{
                // -号 负号
                inputtingNum.append("-")
            }
        }else if(operation == Operation.Plus){
            if (numbers.size == operationList.size){
                // do nothing +号 正号
            }else{
                operationList.add(operation)
            }
        }
    }

    // 用于计算的栈
    private val calculateStack:Stack<CalElement<Double>> = Stack()
    private fun calculateNow(){
        calculateStack.clear()
        for ((index,operation) in operationList.withIndex()){
            when(operation){
                Operation.Plus -> {
                    calculateStack.push(CalElement.Number(numbers[index]))
                    calculateStack.push(CalElement.Operate(Operation.Plus))
                }
                Operation.Minus -> {
                    calculateStack.push(CalElement.Number(numbers[index]))
                    calculateStack.push(CalElement.Operate(Operation.Plus))
                }
                Operation.Div -> {
                    // 算式最后是数字
                    if (numbers.size-1 > index){
                        // 不是第一个四则运算符
                        if (index>0){
                            when(operationList[index-1]){
                                // 上一个运算是加减
                                Operation.Plus,Operation.Minus ->{
                                    val result = numbers[index].div(numbers[index+1])
                                    calculateStack.push(CalElement.Number(result))
                                }
                                // 上一个运算是乘除
                                Operation.Times,Operation.Div ->{
                                    val result = (calculateStack.pop() as CalElement.Number).value.div(numbers[index+1])
                                    calculateStack.push(CalElement.Number(result))
                                }
                            }
                        }else{
                            val result = numbers[index].div(numbers[index+1])
                            calculateStack.push(CalElement.Number(result))
                        }
                    }else{
                    // 算式最后是四则运算符 不参与计算

                    }
                }
                Operation.Times -> {
                    // 算式最后是数字
                    if (numbers.size-1 > index){
                        // 不是第一个四则运算符
                        if (index>0){
                            when(operationList[index-1]){
                                // 上一个运算是加减
                                Operation.Plus,Operation.Minus ->{
                                    val result = numbers[index].times(numbers[index+1])
                                    calculateStack.push(CalElement.Number(result))
                                }
                                // 上一个运算是乘除
                                Operation.Times,Operation.Div ->{
                                    val result = (calculateStack.pop() as CalElement.Number).value.times(numbers[index+1])
                                    calculateStack.push(CalElement.Number(result))
                                }
                            }
                        }else{
                            val result = numbers[index].times(numbers[index+1])
                            calculateStack.push(CalElement.Number(result))
                        }
                    }else{
                        // 算式最后是四则运算符 不参与计算
                    }
                }
            }
        }
        var result = 0.0
        var operation:Operation? = null
        while (!calculateStack.isEmpty()){
            val popValue = calculateStack.pop()
            when(popValue){
                is CalElement.Operate -> {
                    operation = popValue.value
                }

                is CalElement.Number -> {
                    if (result == 0.0){
                        result = popValue.value
                    }else{
                        when(operation){
                            Operation.Plus -> {
                                result = popValue.value.plus(result)
                            }

                            Operation.Minus ->{
                                result = popValue.value.minus(result)
                            }
                        }
                    }
                }
            }
        }

        _calculatingResultFlow.value = result

    }

    // 按下等号后的结果  此结果不为空时按下数字键 将上条算式存入record 更新当前算式  按下操作符时同样将上条存入record，以结果加操作符号分别保存；
    private val _resultAfterEqualsFlow:MutableStateFlow<Double?> = MutableStateFlow(null)
    val resultAfterEqualsFlow:StateFlow<Double?> = _resultAfterEqualsFlow
    fun equals(){
        _resultAfterEqualsFlow.value = _calculatingResultFlow.value
    }

    // 当前算式的计算结果
    private val _calculatingResultFlow:MutableStateFlow<Double> = MutableStateFlow(0.0)
    val calculatingResultFlow:StateFlow<Double> = _calculatingResultFlow

    private val inputtingNum:StringBuilder = StringBuilder()
    fun appendInputtingNum(string:String){
        inputtingNum.append(string)
        numbers.removeLast()
        numbers.add(inputtingNum.toString().toDouble())
        calculateNow()
    }

    fun clearInputtingNum(){
        inputtingNum.clear()
    }

}


enum class Operation{
    Plus,Minus,Times,Div
}

sealed class CalElement<out T>{

    class Number(val value:Double):CalElement<Double>()

    class Operate(val value: Operation):CalElement<Double>()

}
