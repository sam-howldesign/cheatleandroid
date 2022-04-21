package com.howldesign.cheatle_android

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.howldesign.cheatle_android.ui.theme.CheatleandroidTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CheatleWord(
    wordNumber: Int = 0,
    modifier: Modifier = Modifier,
    cheatleViewModel: CheatleViewModel = viewModel()
) {

    LazyRow(
        modifier = modifier
    ){
        items(
            items = cheatleViewModel.boxes[wordNumber],
            key = { box -> box.id }
        ){ box ->
            CheatleBox(
                box = box,
                onLetterChange = { newLetter -> cheatleViewModel.changeLetter(box, newLetter, wordNumber) },
                onColorEnumChange = { cheatleViewModel.changeColor(box, wordNumber) }
            )//end CheatleBox
        }
    }
}

@Preview(showBackground = true)
@Composable
fun cwPreview() {
    CheatleandroidTheme {
        CheatleWord()
    }
}

