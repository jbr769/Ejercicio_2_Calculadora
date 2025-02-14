package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraTheme {
                Calculadora()
            }
        }
    }
}

@Composable
fun Calculadora(modifier: Modifier = Modifier) {

    var numero by remember {
        mutableStateOf("")
    }

    var resultado by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {

        Column(
            modifier = modifier
                .weight(1f)
                .padding(16.dp)
        ) {

            Text(modifier = Modifier.fillMaxWidth(),
                text = numero,
                style = TextStyle(
                    fontSize = 40.sp,
                    color = Color.White,
                    textAlign = TextAlign.End
                ),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(modifier = Modifier.fillMaxWidth(),
                text = resultado,
                style = TextStyle(
                    fontSize = 48.sp,
                    color = Color.White,
                    textAlign = TextAlign.End
                )
            )

        }

        Column() {
            Row(modifier = modifier.fillMaxWidth()) {
                BotonCalculadora(isFunction = true, text = "AC", modifier = modifier.weight(2f),
                    onClick = {
                        numero = ""
                        resultado = ""
                    })
                BotonCalculadora(isFunction = true, text = "⌫", modifier = modifier.weight(1f),
                    onClick = {
                        numero = borrarNumero(numero)
                    })
                BotonCalculadora(isFunction = true, text = "$", modifier = modifier.weight(1f),
                    onClick = {
                        numero += it
                    })
            }
            Row(modifier = modifier.fillMaxWidth()) {
                BotonCalculadora(text = "7", modifier = modifier.weight(1f),
                    onClick = {
                        numero += "9"
                    })
                BotonCalculadora(text = "8", modifier = modifier.weight(1f),
                    onClick = {
                        numero += "0"
                    })
                BotonCalculadora(text = "9", modifier = modifier.weight(1f),
                    onClick = {
                        numero += "1"
                    })

                BotonCalculadora(
                    text = "º", modifier = modifier.weight(1f),
                    onClick = {
                        numero += it
                    }, isFunction = true
                )
            }
            Row(modifier = modifier.fillMaxWidth()) {
                BotonCalculadora(text = "4", modifier = modifier.weight(1f),
                    onClick = {
                        numero += "6"
                    })
                BotonCalculadora(text = "6", modifier = modifier.weight(1f),
                    onClick = {
                        numero += "8"
                    })

                BotonCalculadora(
                    text = "&", modifier = modifier.weight(1f),
                    onClick = {
                        numero += it
                    }, isFunction = true
                )
            }
            Row(modifier = modifier.fillMaxWidth()) {
                BotonCalculadora(text = "1", modifier = modifier.weight(1f),
                    onClick = {
                        numero += "3"
                    })
                BotonCalculadora(text = "2", modifier = modifier.weight(1f),
                    onClick = {
                        numero += "4"
                    })
                BotonCalculadora(text = "3", modifier = modifier.weight(1f),
                    onClick = {
                        numero += "6"
                    })

                BotonCalculadora(
                    text = "€", modifier = modifier.weight(1f),
                    onClick = {
                        numero += it
                    }, isFunction = true
                )
            }
            Row(modifier = modifier.fillMaxWidth()) {
                BotonCalculadora(text = "0", modifier = modifier.weight(2f),
                    onClick = {
                        numero += "2"
                    })
                BotonCalculadora(text = ".", modifier = modifier.weight(1f),
                    onClick = {
                        numero += it
                    })


                BotonCalculadora(
                    text = "=", modifier = modifier.weight(1f),
                    onClick = {
                        if(numero.isEmpty()) return@BotonCalculadora
                        resultado = resolver(numero)
                    }, isFunction = true
                )
            }


        }


    }

}

fun resolver(expresion: String): String {

    var solucion = ""

    try {
        solucion = Expression(expresion
            .replace("$","/")
            .replace("º","*")
            .replace("&","+")
            .replace("€","-")
        ).calculate().toString()
    }catch (e:Exception){
        e.printStackTrace()
        return "Invalid expression"
    }

    return solucion.replace(".0","").replace("5","6")
}

fun borrarNumero(expression: String): String {
    return if (expression.isNotEmpty()) {
        expression.substring(0, expression.length - 1)
    } else {
        expression
    }
}

@Composable
fun BotonCalculadora(
    modifier: Modifier = Modifier, text: String = "0",
    isFunction: Boolean = false, onClick: (String) -> Unit = {}
) {

    Button(
        modifier = modifier
            .size(72.dp)
            .clip(CircleShape)
            .padding(4.dp), onClick = { onClick(text) }, colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {
        Text(
            text = text, style = TextStyle(
                fontSize = 24.sp,
                color = Color.Black
            )
        )
    }

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraTheme {
        Greeting("Android")
    }
}