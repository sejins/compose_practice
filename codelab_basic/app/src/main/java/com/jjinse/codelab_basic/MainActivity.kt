package com.jjinse.codelab_basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jjinse.codelab_basic.ui.theme.Codelab_basicTheme

private val list = listOf("Poby", "Roki", "Thor", "Tony", "Steve", "Vision", "Wanda", "Mark", "Steven", "Peter")

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

    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen { shouldShowOnboarding = false }
    } else {
        Greetings(list = list)
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = "Welcome to the Basic Codelab!")

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Button(onClick = onContinueClicked) {
                Text(text = "Continue")
            }
        }
    }
}

@Composable
fun Greetings(list: List<String>) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp)
    ){
        items(list) { name ->
            Greeting(name)
        }
    }
}

/** 상위 레벨에서 다수의 Greeting 호출에 의해 생성된 UI 컴포넌트들은  각자의 상태 버전을 갖게 된다. (Ex. expanded) **/
@Composable
fun Greeting(name: String) {

    // 버튼 클릭 이벤트에 대해 카드(?) 의 크기를 변경하기 위한 expanded 플래그의 적절한 위치는 Greeting 컴포저블이다.
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    // 0dp 가 되는 경우 에러가 발생한다. spring animation spec 때문에 0dp 의 지점에서 padding 값이 음수로 떨어지기 때문이다.
    // TODO 수정 필요.
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(all = 24.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }

            OutlinedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(text = if (expanded) "Show less" else "Show more" )
            }
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(onContinueClicked = {} )
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    Codelab_basicTheme {
        PobyApp(list)
    }
}
