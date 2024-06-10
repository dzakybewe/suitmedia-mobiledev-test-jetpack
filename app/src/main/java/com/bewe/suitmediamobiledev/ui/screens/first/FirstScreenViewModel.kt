package com.bewe.suitmediamobiledev.ui.screens.first

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FirstScreenViewModel : ViewModel() {
    private val _nameText = MutableStateFlow("")
    val nameText: StateFlow<String> get() = _nameText

    private val _palindromeText = MutableStateFlow("")
    val palindromeText: StateFlow<String> get() = _palindromeText

    private val _isPalindrome = MutableStateFlow(false)
    val isPalindrome: StateFlow<Boolean> get() = _isPalindrome

    fun onNameTextChanged(newText: String) {
        _nameText.value = newText
    }

    fun onPalindromeTextChanged(newText: String) {
        _palindromeText.value = newText
    }

    fun checkPalindrome() {
        val text = _palindromeText.value
        val cleanedText = text.filter {
                it.isLetterOrDigit()
            }.lowercase()

        _isPalindrome.value = cleanedText == cleanedText.reversed()
    }

}