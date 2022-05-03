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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jjinse.codelab_layouts.ui.theme.Codelab_layoutsTheme
import kotlinx.coroutines.launch

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

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

/**
 * 내장 기능을 사용하면 하위 요소가 실제로 측정되기 전에 하위 요소를 쿼리할 수 있다.
 *
 * (min|max)IntrinsicWidth : 이 height 에서 콘텐츠를 적절하게 그릴 수 있는 최소/최대 width 는 무엇인가요?
 * (min|max)IntrinsicHeight : 이 width 에서 콘텐츠를 적절하게 그릴 수 있는 최소/최대 height 는 무엇인가요?
 **/

@Composable
fun TwoText(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String
) {
    Row(modifier = modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min)) {

        Text(
            modifier = Modifier.weight(1f).wrapContentWidth(Alignment.CenterHorizontally).padding(4.dp).height(50.dp),
            text = text1
        )

        Divider(color = Color.Black, modifier = Modifier
            .fillMaxHeight()
            .width(1.dp))

        Text(
            modifier = Modifier.weight(1f).wrapContentWidth(Alignment.CenterHorizontally).padding(4.dp),
            text = text2
        )
    }
}

@Preview
@Composable
fun TwoTextPreview() {
    Codelab_layoutsTheme {
        TwoText(text1 = "text1", text2 = "text2")
    }
}

@Composable
fun Chip(
    modifier:Modifier = Modifier,
    text: String
) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(16.dp)
                .background(MaterialTheme.colors.secondary))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

/**
 * custom staggered grid layout
 * 현재 custom grid layout 자체는 스크롤이 불가능
 **/
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        // Keep track of the width of each row
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of the max height of each row
        val rowHeights = IntArray(rows) { 0 }

        // measurement of each Composable
        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid's width is the widest row
        val gridWidth =
            rowWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        val gridHeight =
            rowHeights.sumOf { it }.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        // Set the size of the parent layout
        layout(gridWidth, gridHeight) {
            // trace rowX per each row
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}

/** custom layout **/
@Composable
fun MyCustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // measure and position children given constraints logic here

        // List of measured children
        val placeables = measurables.map { measurable ->
            // measure each child
            measurable.measure(constraints)
        }

        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children in the parent layout

            placeables.forEach {
                it.placeRelative(x = 0, yPosition)
                yPosition += it.height
            }
        }
    }
}

/** custom layout modifier **/ 
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
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(all = 8.dp)
        )
//        ScrollingList()
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
    // StaggeredGrid 는 자체적으로 스크롤이 불가능한 커스텀 레이아웃 형태이기 때문에 스크롤 가능한 Row 로 래핑
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        StaggeredGrid(rows = 3) {
            topics.forEach {
                Chip(modifier = Modifier.padding(8.dp), text = it)
            }
        }
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
fun ChipPreview() {
    Chip(text = "This is a test")
}

@Preview
@Composable
fun MyCustomColumnPreview() {
    BodyContent()
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
