package com.jjinse.tutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.jjinse.tutorial.ui.theme.TutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorialTheme {
                MessageCard(message = Message("poby", "hello!"))
            }
        }
    }

    data class Message(val author: String, val content: String)


    @Composable
    fun MessageCard(message: Message) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.i4),
                contentDescription = "BMW i4",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
                // modifier 는 뷰 속성에 대한 체이닝이 가능하다.
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = message.author,
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.subtitle2
                ) // theme 에 정의된 attribute 를 쉽게 참조할 수 있다.
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(all = 4.dp)
                    )
                }
            }
        }
    }


    // Preview 를 하기 위해서는 default parameter 가 반드시 존재해야하기 떄문에 다음과 같이 preview 용 composable 메서드를 생성할 수 있다.
    @Preview(name = "Light mode")
    @Preview(
        name = "Dark mode",
        showBackground = true,
        uiMode = Configuration.UI_MODE_NIGHT_YES
    )
    @Composable
    fun PreviewMessageCard() {
        TutorialTheme {
            MessageCard(message = Message("poby", "This is a test"))
        }
    }
}
