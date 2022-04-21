package com.howldesign.cheatle_android

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.howldesign.cheatle_android.ui.theme.CheatleandroidTheme



@Composable
fun CheatleBox(
    box: CheatleBoxData,
    onLetterChange: (String) -> Unit,
    onColorEnumChange: () -> Unit,
    modifier: Modifier = Modifier
){
    val color: Color = when (box.color) {
        BoxColors.white -> Color.White
        BoxColors.gray -> Color.Gray
        BoxColors.green -> Color.Green
        BoxColors.yellow -> Color.Yellow
    }

    Box (contentAlignment = Alignment.Center){
        Button(
            onClick = onColorEnumChange,
            colors = ButtonDefaults.buttonColors(backgroundColor = color),
            modifier = modifier
                .border(width = 1.dp, Color.Black)
                .width(65.dp)
                .height(65.dp)
        ){ }
        TextField(
            singleLine = true,
            value = box.letter,
            onValueChange = onLetterChange,
            modifier = modifier
                .width(50.dp)
                .height(50.dp)
                .border(width = 1.dp, color = Color.Black)
        )
    }
}


