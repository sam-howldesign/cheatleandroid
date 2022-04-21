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
    private val _boxes = getBoxes().toMutableStateList()
    val boxes: List<CheatleBoxData>
        get() = _boxes

    fun changeColor(box: CheatleBoxData){
        _boxes.set(
            box.id,
            box.copy(color = box.color.next())
        )
    }

    fun changeLetter(box: CheatleBoxData, newLetter: String){
        if (newLetter.length <= 1){
            _boxes.set(
                box.id,
                box.copy(letter = newLetter)
            )
        }
    }

}
///TODO: dick around here making a list of lists then change the code to understand that
private fun getAllBoxes() = List(5){ i -> List(5){ j -> CheatleBoxData(j) } }

private fun getBoxes() = List(5){ i -> CheatleBoxData(i) }