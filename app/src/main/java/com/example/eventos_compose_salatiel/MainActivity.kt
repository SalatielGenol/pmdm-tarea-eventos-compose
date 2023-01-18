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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    val (globalCounter, globalCounterOnChange) = rememberSaveable { mutableStateOf("0") }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly) {
            ContadorIndividual(
                numCountName = stringResource(R.string.count_name_button_1),
                globalCounter = globalCounter,
                globalCounterOnChange = globalCounterOnChange
            )
            ContadorIndividual(
                numCountName = stringResource(R.string.count_name_button_2),
                globalCounter = globalCounter,
                globalCounterOnChange = globalCounterOnChange
            )
            ContadorGlobal(
                globalCounter = globalCounter,
                globalCounterOnChange = globalCounterOnChange
            )
    }
}

@Composable
fun ContadorIndividual(
    numCountName: String,
    globalCounter: String,
    globalCounterOnChange: (String) -> Unit,
){
    val (numInput, numInputOnChange) = rememberSaveable { mutableStateOf("1") }
    val (numCounter, numCounterOnChange) = rememberSaveable { mutableStateOf("0") }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                numCounterOnChange((numCounter.toInt() + numInput.toInt()).toString())
                globalCounterOnChange((globalCounter.toInt() + numInput.toInt()).toString())
            }) {
                Text(text = "$numCountName ($numCounter)")
            }
            Text(modifier = Modifier.padding(horizontal = 10.dp), text = numCounter)
            IconoBorrar(modifier = Modifier.clickable { numCounterOnChange("0") })
        }
        Row(verticalAlignment = Alignment.Bottom){
            Text(text = stringResource(R.string.increment_text) + ":")
            BasicTextField(
                value = numInput,
                onValueChange = {
                    if (it.length > 1) {
                        numInputOnChange("1")
                    }else{
                        numInputOnChange(it)
                    }
                },
                modifier = Modifier.onFocusChanged { focusState ->
                    when {
                        focusState.hasFocus -> numInputOnChange("")
                    } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = TextStyle(
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colors.onSurface
                ),
                decorationBox = {
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
                        it()
                    }
                }
            )
        }
    }
}

@Composable
fun ContadorGlobal(globalCounter: String, globalCounterOnChange: (String) -> Unit){
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.global_text) + ":")
            Text(modifier = Modifier.padding(horizontal = 10.dp), text = globalCounter)
            IconoBorrar(modifier = Modifier.clickable { globalCounterOnChange("0") })
        }
    }
}

@Composable
fun IconoBorrar(modifier: Modifier = Modifier){
    if (isSystemInDarkTheme()){
        Icon(Icons.Filled.Delete, contentDescription = "null", modifier = modifier)
    }else{
        Icon(Icons.Outlined.Delete, contentDescription = "null", modifier = modifier)
    }
}