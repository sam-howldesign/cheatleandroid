package com.howldesign.cheatle_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun LoadingScreen(){
    Column (horizontalAlignment = Alignment.CenterHorizontally){

        Spacer(modifier = Modifier.height(10.dp))
        CircularProgressIndicator()
        Text("Loading ...")
        Spacer(modifier = Modifier.height(10.dp))
    }
}



@Composable
fun CheatleApp(
    cheatleViewModel: CheatleViewModel = viewModel()
) {
    val context = LocalContext.current

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

        when {
            cheatleViewModel.isLoading.value -> LoadingScreen()
            else -> {
                Row {
                    Button(
                        onClick = { cheatleViewModel.filter() }
                    ){
                        Text("Filter")
                    }
                    Spacer(Modifier.width(10.dp))
                    Button(
                        onClick = { cheatleViewModel.reset() }
                    ){
                        Text("Reset")
                    }
                }

                LazyColumn{
                    items(cheatleViewModel.listOfWords){ wordItem ->
                        Text(wordItem)
                    }

                }
            }
        }


    }

    LaunchedEffect(true){
        cheatleViewModel.loadWords(context)
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