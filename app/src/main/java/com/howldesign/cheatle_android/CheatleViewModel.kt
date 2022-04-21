package com.howldesign.cheatle_android

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class BoxColors { white, gray, yellow, green }

inline fun <reified T: Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}

data class CheatleBoxData(val id: Int, var letter: String = "", var color: BoxColors = BoxColors.white)

class CheatleViewModel: ViewModel() {
    private val _boxes = getAllBoxes().toMutableStateList()
    val boxes: List<List<CheatleBoxData>>
        get() = _boxes

    fun changeColor(box: CheatleBoxData, wordIndex: Int){
        val updatedWord: MutableList<CheatleBoxData> = _boxes.get(wordIndex).toMutableList()
        updatedWord.set(box.id, box.copy(color = box.color.next()))

        _boxes.set(
            wordIndex,
            updatedWord.toList()
        )
    }

    fun changeLetter(box: CheatleBoxData, newLetter: String, wordIndex: Int){
        val updatedWord: MutableList<CheatleBoxData> = _boxes.get(wordIndex).toMutableList()
        updatedWord.set(box.id, box.copy(letter = newLetter))

        if (newLetter.length <= 1){
            _boxes.set(
                wordIndex,
                updatedWord.toList()
            )
        }
    }

}
private fun getAllBoxes() = List(5){ i -> List(5){ j -> CheatleBoxData(j) } }

//private fun getBoxes() = List(5){ i -> CheatleBoxData(i) }