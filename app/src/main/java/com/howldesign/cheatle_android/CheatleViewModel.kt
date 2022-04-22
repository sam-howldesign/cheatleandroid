package com.howldesign.cheatle_android

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        _listOfWords.clear()
        val yellowLetters = mutableListOf<String>()
        val grayLetters = mutableListOf<String>()

        for (wordIndex in 0..4){
            //foreach word guess
            for (letterIndex in 0..4){
                val box = _boxes[wordIndex][letterIndex]
                if (box.color == BoxColors.yellow){
                    yellowLetters.add(box.letter)
                }
                if (box.color == BoxColors.gray){
                    grayLetters.add(box.letter)
                }
            }
        }//end for wordindex

        allWords@ for (possibleWord in _allWords){
            //skip any blank lines
            if (possibleWord.length != 5) {
                continue@allWords
            }

            for(grayLetter in grayLetters){
                if (possibleWord.contains(grayLetter, ignoreCase = true)){
                    //skip any word with any gray letter
                    continue@allWords
                }
            }

            for (wordIndex in 0..4){
                for (letterIndex in 0..4){
                    val box = _boxes[wordIndex][letterIndex]
                    if (
                        box.color == BoxColors.green
                        && possibleWord[letterIndex].toString() != box.letter
                    ){
                        //this letter is green but not in the possible word at this index so skip
                        continue@allWords
                    }

                    if (
                        box.color == BoxColors.yellow
                        && possibleWord[letterIndex].toString() == box.letter
                    ){
                        //this letter is yellow and the possible has it in the same spot so skip
                        continue@allWords
                    }
                }//end for each letter
            }//end for each word

            for (yellowLetter in yellowLetters){
                if (!possibleWord.contains(yellowLetter, ignoreCase = true)){
                    //this word does not contain all of the yellow letters
                    continue@allWords
                }
            }

            //passed all tests so keep this one
            _listOfWords.add(possibleWord)
        }//end for each allwords

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
