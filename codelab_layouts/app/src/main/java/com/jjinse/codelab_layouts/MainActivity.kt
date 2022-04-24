package com.jjinse.codelab_layouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jjinse.codelab_layouts.ui.theme.Codelab_layoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Codelab_layoutsTheme {
                PhotographerCard()
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


@Preview(showBackground = true)
@Composable
fun PhotographerCardPreview() {
    Codelab_layoutsTheme {
        PhotographerCard()
    }
}
