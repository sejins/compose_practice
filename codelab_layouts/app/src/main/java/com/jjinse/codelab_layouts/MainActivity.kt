package com.jjinse.codelab_layouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jjinse.codelab_layouts.ui.theme.Codelab_layoutsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Codelab_layoutsTheme {
                LayoutCodelab()
            }
        }
    }
}


// custom layout modifier
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout {
        measurable, constraints ->

        // Composable measurement
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun LayoutCodelab() {
    Scaffold(
        topBar = {
            TopAppBar( // 컴포즈에서 제공되는 상단 액션바에 해당하는 컴포저블. 이외에도 BottomNavigation, BottomDrawer 등 다양한 컴포저블이 존재한다.
                title = {
                    Text(text = "LayoutCodelab")
                },
                actions = { // 작업 항목 슬롯에 해당하는 actions 는 기본적으로 Row 를 사용하기 때문에 해당 블록은 RowScope 에 해당한다.
                    IconButton(onClick = { /* do something */ }) {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Favorite")
                    }
                    IconButton(onClick = { /*do something*/ }) {
                        Icon(imageVector = Icons.Filled.Share, contentDescription = "Share")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation() {
                BottomNavigationItem(
                    selected = false,
                    onClick = { /* do something */ },
                    icon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone") },
                    label = { Text(text = "phone") }
                )
                BottomNavigationItem(
                    selected = false,
                    onClick = { /* do something */ },
                    icon = { Icon(imageVector = Icons.Filled.Message, contentDescription = "Message") },
                    label = { Text(text = "message") }
                )
                BottomNavigationItem(
                    selected = false,
                    onClick = { /* do something */ },
                    icon = { Icon(imageVector = Icons.Filled.Book, contentDescription = "Book") },
                    label = { Text(text = "book") }
                )
            }
        }
    ) { innerPadding ->
        // 현재 BodyContent 컴포저블은 modifier 를 매개변수로 받고 있기 때문에 추가 설정이 두가지 위치에서 가능하다.
        // 1. 컴포저블 호출 시 : 컴포저블의 케이스 별로 설정을 주고 싶은 경우 사용한다.
        // 2. 컴포저블 내부 : 모든 컴포저블에 고유한 설정일 때 사용한다.
//        BodyContent(
//            Modifier.padding(innerPadding).padding(all = 8.dp)
//        )
        ScrollingList()
    }
}

@Composable
fun ScrollingList() {

    val listSize = 100

    // save the scrolling position with this state
    val scrollState = rememberLazyListState()

    // save the coroutine scope where animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Scroll to the top")
            }
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text(text = "Scroll to the bottom")
            }
        }

        LazyColumn(state = scrollState) {
            items(100) {
                ImageListItem(index = it)
            }
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"), // coil
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Item #$index",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "Hi there!")
        Text(text = "Thanks for going through the Layouts codelab")
    }
}

/** Modifier 를 첫번째 매개변수로 받아서 사용하는 것이 좋고, 기본값은 Modifier **/
@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {

    // 컴포저블의 첫번째 매개변수로 modifier 를 사용하는 것이 컨벤션이기 때문에, modifier 를 단독으로 사용하는 경우에는 매개변수 이름을 명시해주지 않아도 될 것 같다.
    Row(
        modifier
            .padding(8.dp)
            .background(MaterialTheme.colors.surface)
            .clip(RoundedCornerShape(4.dp))
            .clickable { /* Ignoring onClick */ }
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            // TODO Image goes here
        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Poby.peng", fontWeight = FontWeight.Bold)
            // CompositionLocalProvider 를 통해 하위 컴포지션 트리를 통해 암시적으로 데이터를 전달할 수 있다.
            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    Codelab_layoutsTheme {
        Text(
            text = "Hi there!",
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@Preview
@Composable
fun TextWithNormalPaddingToBaselinePreview() {
    Codelab_layoutsTheme {
        Text(
            text = "Hi there!",
            modifier = Modifier.firstBaselineToTop(32.dp)
        )
    }
}

@Preview
@Composable
fun LayoutCodelabPreview() {
    Codelab_layoutsTheme {
        LayoutCodelab()
    }
}

@Preview(showBackground = true)
@Composable
fun PhotographerCardPreview() {
    Codelab_layoutsTheme {
        PhotographerCard()
    }
}
