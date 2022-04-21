package com.jjinse.playground

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jjinse.playground.ui.theme.PlaygroundTheme

class MainActivity : ComponentActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MaseratiSelectableCard()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@ExperimentalMaterialApi
@Composable
fun MaseratiSelectableCard() {

    var selected by remember { mutableStateOf(false)}

    val radius: Dp = if (selected) 30.dp else 0.dp


    Card(
        shape = RoundedCornerShape(topStart = radius),
        onClick = {selected = !selected},
    ) {
        Row {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ghibli),
                    contentDescription = "ghibli",
                    modifier = Modifier.size(80.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )

                if (selected) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = "check", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Column {
                Text(
                    text = "Maserati Ghibli",
                    style = MaterialTheme.typography.subtitle1
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewMaseratiSelectableCard() {
    PlaygroundTheme {
        MaseratiSelectableCard()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlaygroundTheme {
        Greeting("Android")
    }
}
