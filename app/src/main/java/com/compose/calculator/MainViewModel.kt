package com.compose.calculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel:ViewModel() {

    // 历史记录算式
    private val _recordFlow:MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val recordFlow:StateFlow<List<String>> = _recordFlow

    // 当前计算的算式
    private val _calculatingFlow:MutableStateFlow<List<String>> = MutableStateFlow(listOf("0"))
    val calculatingFlow:StateFlow<List<String>> = _calculatingFlow

    // 当前计算的结果 作为第一个算子
    private val _calculatingResultFlow:MutableStateFlow<Double> = MutableStateFlow(0.0)
    val calculatingResultFlow:StateFlow<Double> = _calculatingResultFlow

    // 当前的运算符
    private val _calculationTypeFlow:MutableStateFlow<String> = MutableStateFlow("")
    val calculationTypeFlow:StateFlow<String> = _calculationTypeFlow

    // 用户输入时的临时计算结果
    private val _calculatingTempResultFlow:MutableStateFlow<Double> = MutableStateFlow(0.0)
    val calculatingTempResultFlow:StateFlow<Double> = _calculatingTempResultFlow

    // 

}