package com.jjinse.playground

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
                    val winnerList = List(15) { Person("Poby") }
                    ShowMaseratiSelectableCardList(winningList = winnerList)
                }
            }
        }
    }
}

data class Person(val name: String)

@ExperimentalMaterialApi
@Composable
fun ShowMaseratiSelectableCardList(winningList: List<Person>) {
    LazyColumn {
        items(winningList) { winner ->
            MaseratiSelectableCard(winner = winner)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MaseratiSelectableCard(winner: Person) {

    var selected by remember { mutableStateOf(false)}

    val radius: Dp by animateDpAsState(
        targetValue = if (selected) 30.dp else 0.dp
    )

    Card(
        shape = RoundedCornerShape(topStart = radius),
        onClick = {selected = !selected},
        modifier = Modifier.padding(all = 4.dp),
        backgroundColor = if (selected) Color.LightGray else MaterialTheme.colors.surface
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
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = winner.name,
                    style = MaterialTheme.typography.body1
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
        MaseratiSelectableCard(Person("Poby"))
    }
}
