package com.jjinse.codelab_basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jjinse.codelab_basic.ui.theme.Codelab_basicTheme

private val list = listOf("poby", "roki", "thor")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Codelab_basicTheme {
                PobyApp(list)
            }
        }
    }
}

@Composable
private fun PobyApp(list: List<String>) {

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for(name in list) {
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {

    // import androidx.compose.material.Surface 같은 compose material 컴포넌트는 공통 작업에 대한 기능을 포함한다.
    // 예를들어 color 를 primary 로 가져가는 경우, 텍스트 색상은 onPrimary 값으로 세팅해야하는 것을 compose 는 알고있다.
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(all = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }

            OutlinedButton(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Show more")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    Codelab_basicTheme {
        PobyApp(list)
    }
}
