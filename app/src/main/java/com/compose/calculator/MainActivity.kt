package com.compose.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.calculator.ui.theme.CalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Calculator()
                }
            }
        }
    }
}


@Composable
fun Calculator(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Screen( Modifier.weight(1F))
        InputArea()
    }
}


@Composable
fun Screen(modifier: Modifier = Modifier,viewModel:MainViewModel = viewModel(), ){
    val recordState = viewModel.recordFlow.collectAsState()
    val currentState = viewModel.calculatingFlow.collectAsState()
    Surface(shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)){
        Column(horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier.padding(16.dp)) {
            for (record in recordState.value){
                Text(text = record, fontSize = 24.sp)
            }
            for (inputs in currentState.value){
                if (inputs.contains("=")){
                    val calculating = inputs.split("=")
                    Text(text = calculating[0], fontSize = 40.sp,lineHeight = 44.sp, textAlign = TextAlign.End)
                    Text(text = " =" + calculating[1], fontSize = 40.sp)
                }
            }

        }
    }
}


@Composable
fun InputArea(modifier: Modifier = Modifier){
    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(8.dp)) {
        Column() {
            LineButtons(strs = arrayOf("C","⊗","%","÷"),modifier.padding(0.dp,8.dp))
            LineButtons(strs = arrayOf("7","8","9","×"),modifier.padding(0.dp,8.dp))
            LineButtons(strs = arrayOf("4","5","6","-"),modifier.padding(0.dp,8.dp))
            LineButtons(strs = arrayOf("1","2","3","+"),modifier.padding(0.dp,8.dp))
            LineButtons(strs = arrayOf("0",".","="),modifier.padding(0.dp,8.dp))
        }
    }

}


/**
 *  num: 123 456 789 0 .
 *  + - ÷ ×  %  =
 *  C  AC  DEL ⊗
 *
 */
@Composable
fun SingleButton(str:String = "1", modifier: Modifier = Modifier,viewModel:MainViewModel = viewModel()){

    val calculating = viewModel.calculatingFlow.collectAsState()

    Button(onClick = {
                     when(str){
                         "1","2","3","4","5","6","7","8","9" -> {
                             if (calculating.value == listOf("0")){

                             }else{

                             }
                         }
                         "+","-","×","÷" ->{

                         }
                         "%"-> {

                         }
                         "."-> {

                         }
                         "0" ->{

                         }
                         "C","AC","⊗"->{

                         }

                     }

                     }, modifier = modifier) {
        Text(text = str, fontSize = 32.sp)
    }
}


@Composable
fun LineButtons(strs:Array<String>,modifier: Modifier = Modifier,viewModel: MainViewModel = viewModel()){
    Row {
        for ((index,str) in strs.withIndex()){
            if(strs.size == 3 && index == 0){
                SingleButton(str, modifier = modifier.weight(2.0F,true))
            }else{
                SingleButton(str, modifier = modifier.weight(1.0F,true))
            }
            if (index < strs.size - 1){
                Spacer(modifier = Modifier.size(8.dp))
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CalTheme {
        Calculator()
    }
}