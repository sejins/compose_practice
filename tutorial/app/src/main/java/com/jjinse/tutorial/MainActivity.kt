package com.jjinse.tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.jjinse.tutorial.ui.theme.TutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard("android")
        }
    }


    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello! $name")
    }


    // Preview 를 하기 위해서는 default parameter 가 반드시 존재해야하기 떄문에 다음과 같이 preview 용 composable 메서드를 생성할 수 있다.
    @Preview
    @Composable
    fun PreviewMessageCard() {
        MessageCard(name = "preview test")
    }
}
