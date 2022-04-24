package com.jjinse.codelab_basic

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),

    ) {
        CardContent(name)
    }
}

@Composable
fun CardContent(name: String) {

    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier.padding(all = 24.dp)
            .animateContentSize(animationSpec = spring( // padding 값을 매뉴얼하게 수정하는 대신 animateContentSize 로 애니메이션을 적용한다.
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "Hello,")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = "Composem ipsum color sit lazy, padding theme elit, sed do bouncy.".repeat(4)
                )
            }
        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(id = R.string.show_less)
                } else {
                    stringResource(id = R.string.show_more)
                }
            )
        }
    }
}

@Preview(
    widthDp = 320,
    heightDp = 320
)
@Composable
fun OnboardingScreenPreview() {
    Codelab_basicTheme {
        OnboardingScreen(onContinueClicked = {} )
    }
}

@Preview(
    name = "DefaultPreviewDark",
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Codelab_basicTheme {
        Greetings(list = list)
    }
}
