package com.howldesign.cheatle_android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.howldesign.cheatle_android.ui.theme.CheatleandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm: CheatleViewModel by viewModels()
        setContent {
            CheatleandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CheatleApp(cheatleViewModel = vm)
                }
            }
        }
    }
}


@Composable
fun CheatleApp(
    cheatleViewModel: CheatleViewModel = viewModel()
) {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ){
        //make 5 words
        for (i in 0..4) {
            CheatleWord(
                wordNumber = i,
                cheatleViewModel = cheatleViewModel
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CheatleandroidTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            CheatleApp()
        }
    }
}