package com.compose.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun Screen(modifier: Modifier = Modifier,viewModel:MainViewModel = viewModel()){
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

            // the calculating one witch user is inputting
            Text(text = currentState.value,
                fontSize = 40.sp,
                lineHeight = 44.sp,
                textAlign = TextAlign.End)

        }
    }
}


@Composable
fun InputArea(modifier: Modifier = Modifier){
    Surface(color = MaterialTheme.colorScheme.background,
        modifier = modifier.padding(8.dp)) {
        Column {
            LineButtons(pairs = listOf("C" to Color.LightGray,
                "⊗" to Color.LightGray,
                "%" to Color.LightGray,
                "÷" to Color.Red),modifier.padding(0.dp,4.dp))
            LineButtons(pairs = listOf("7" to Color.DarkGray,
                "8" to Color.DarkGray,
                "9" to Color.DarkGray,
                "×" to Color.Red),modifier.padding(0.dp,8.dp))
            LineButtons(pairs = listOf("4" to Color.DarkGray,
                "5" to Color.DarkGray,
                "6" to Color.DarkGray,
                "-" to Color.Red),modifier.padding(0.dp,8.dp))
            LineButtons(pairs = listOf("1" to Color.DarkGray,
                "2" to Color.DarkGray,
                "3" to Color.DarkGray,
                "+" to Color.Red),modifier.padding(0.dp,8.dp))
            LineButtons(pairs = listOf("0" to Color.DarkGray,
                "." to Color.DarkGray,
                "=" to Color.White)
                ,modifier.padding(0.dp,8.dp))
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
fun SingleButton(pair:Pair<String, Color>,
                 modifier: Modifier = Modifier, viewModel:MainViewModel = viewModel()){

    val calculating = viewModel.calculatingFlow.collectAsState()

    Button(onClick = {
         when(pair.first){
             in numbers -> {

             }
             "." -> {

             }
             in operations ->{

             }
             "C" ->{

             }
             "AC"->{

             }
             "%" ->{

             }
             "⊗" -> {

             }
         }


    },shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier.padding(4.dp, 0.dp).then(modifier)) {
        Text(text = pair.first, fontSize = 32.sp, color = pair.second)
    }
}

val numbers = listOf("0","1","2","3","4","5","6","7","8","9")
val operations = listOf("+","-","×","÷")


@Composable
fun LineButtons(pairs:List<Pair<String,Color>>,modifier: Modifier = Modifier,viewModel: MainViewModel = viewModel()){
    Row {
        for ((index,pair) in pairs.withIndex()){
            if(pairs.size == 3){
                when (index) {
                    0 -> {
                        SingleButton(pair, modifier = modifier
                            .weight(2.0F, true)
                            .aspectRatio(2F, true))
                    }
                    2 -> {
                        SingleButton(pair, modifier = modifier
                            .weight(1.0F, true)
                            .aspectRatio(1F, false)
                            .background(Color.Black, CircleShape))
                    }
                    else -> {
                        SingleButton(pair, modifier = modifier
                            .weight(1.0F, false)
                            .aspectRatio(1F))
                    }
                }

            }else{
                SingleButton(pair, modifier = modifier
                    .weight(1.0F, false)
                    .aspectRatio(1F))
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