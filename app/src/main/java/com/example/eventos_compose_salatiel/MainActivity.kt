package com.example.eventos_compose_salatiel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.eventos_compose_salatiel.ui.theme.EventosComposeSalatielTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventosComposeSalatielTheme {
                Surface{
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(){
    var globalCounter: Int by rememberSaveable { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly) {
        ContadorIndividual(
            numCountName = stringResource(R.string.count_name_button_1),
            globalCounter = { globalCounter += it },
        )
        ContadorIndividual(
            numCountName = stringResource(R.string.count_name_button_2),
            globalCounter = { globalCounter += it },
        )
        ContadorGlobal(
            globalCounter = globalCounter,
            globalCounterReset = { globalCounter = 0}
        )
    }
}



@Composable
fun ContadorIndividual(
    numCountName: String,
    globalCounter: (Int) -> Unit,
){
    val inputData = 1
    val clear = 0
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var numInputText: String by rememberSaveable { mutableStateOf(inputData.toString()) }
    var numCounter: Int by rememberSaveable { mutableStateOf(clear) }
    var numInput: Int by rememberSaveable { mutableStateOf(inputData) }
    var color: Color by rememberSaveable { mutableStateOf( Color.White) }
//    var color2: Color by rememberSaveable { mutableStateOf( Colors. }

    Column {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Button(onClick = {
                if (numInputText.isBlank()) {
                    numInputText = numInput.toString()
                }
                focusManager.clearFocus()
                numCounter += numInput
                globalCounter(numInput)
            }) {
                Text(text = "$numCountName ($numCounter)")
            }
            Text(modifier = Modifier.padding(horizontal = 10.dp), text = numCounter.toString())
            IconoBorrar(onClick = {numCounter = clear})
        }
        Row(verticalAlignment = Alignment.Bottom){
            Text(text = stringResource(R.string.increment_text) + ":")
            BasicTextField(
                value = numInputText,
                onValueChange = {
                    if(it.isNotBlank() && it.isDigitsOnly()){
                        numInputText = it
                        numInput = numInputText.toInt()
                    }else{
                        numInputText = ""

                    }
                },
                modifier = Modifier
                    .onFocusChanged { focusState ->
                        when {
                            focusState.hasFocus -> {
                                numInputText = ""
                            }
                        }
                    }
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = TextStyle(
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colors.onSurface
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(25.dp)
                            .aspectRatio(1f)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.primary,
                                shape = RoundedCornerShape(size = 5.dp)
                            )
                            .padding(horizontal = 5.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
fun ContadorGlobal(globalCounter: Int, globalCounterReset: () -> Unit){
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.global_text) + ":")
            Text(modifier = Modifier.padding(horizontal = 10.dp), text = globalCounter.toString())
            IconoBorrar(onClick = globalCounterReset)
        }
    }
}

@Composable
fun IconoBorrar(onClick: () -> Unit){
    if (isSystemInDarkTheme()){
        Icon(Icons.Filled.Delete, contentDescription = "null", modifier = Modifier.clickable { onClick() })
    }else{
        Icon(Icons.Outlined.Delete, contentDescription = "null", modifier = Modifier.clickable { onClick() })
    }
}