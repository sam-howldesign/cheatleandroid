package com.howldesign.cheatle_android

import android.content.Context
import android.renderscript.ScriptGroup
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream

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

    private var _allWords = mutableListOf<String>()

    private val _listOfWords = listOf<String>().toMutableStateList()
    val listOfWords: List<String>
        get() = _listOfWords

    private val _isLoading = mutableStateOf(true)
    val isLoading = _isLoading

    fun loadWords(context: Context) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val inputStream: InputStream = context.assets.open("english5letterwords.txt")
                val lineList = mutableListOf<String>()
                inputStream.bufferedReader().forEachLine { lineList.add(it) }

                launch(Dispatchers.Main) {
                    lineList.forEach{
                        _listOfWords.add(it)
                        _allWords.add(it)
                    }
                    _isLoading.value = false
                }
            }catch (e: Exception){
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    _isLoading.value = false
                }
            }
        }
    }


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

    fun filter(){
//TODO: figure out the filtering.  Probably the same as in iOS

    }

    fun reset(){
        val defaultBoxes = getAllBoxes()
        for (wordIndex in 0..4){
            _boxes.set(
                wordIndex,
                defaultBoxes[wordIndex]
            )
        }

        this.filter()
    }
}
private fun getAllBoxes() = List(5){ i -> List(5){ j -> CheatleBoxData(j) } }

//private fun getBoxes() = List(5){ i -> CheatleBoxData(i) }