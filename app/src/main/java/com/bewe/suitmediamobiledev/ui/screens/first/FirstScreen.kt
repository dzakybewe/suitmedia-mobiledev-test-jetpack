package com.bewe.suitmediamobiledev.ui.screens.first

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bewe.suitmediamobiledev.R
import com.bewe.suitmediamobiledev.ui.components.MyButton

@Composable
fun FirstScreen(
    modifier: Modifier = Modifier,
    viewModel: FirstScreenViewModel = viewModel(),
    navigateToSecondScreen: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        FirstScreenContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            navigateToSecondScreen = navigateToSecondScreen
        )
    }
}

@Composable
fun FirstScreenContent(
    modifier: Modifier = Modifier,
    viewModel: FirstScreenViewModel,
    navigateToSecondScreen: (String) -> Unit,
) {
    val nameText by viewModel.nameText.collectAsState()
    val palindromeText by viewModel.palindromeText.collectAsState()
    val isPalindrome by viewModel.isPalindrome.collectAsState()

    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background_first_screen),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(34.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.foto_bewe),
                contentDescription = "Dev Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(164.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(58.dp))

            MyTextField(
                value = nameText,
                onValueChange = { newText -> viewModel.onNameTextChanged(newText) },
                placeholder = "Name"
            )

            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(
                value = palindromeText,
                onValueChange = { newText -> viewModel.onPalindromeTextChanged(newText) },
                placeholder = "Palindrome"
            )

            Spacer(modifier = Modifier.height(58.dp))

            MyButton(
                onClick = {
                    if (palindromeText.isNotEmpty()) {
                        viewModel.checkPalindrome()
                        showDialog = true
                    } else {
                        Toast.makeText(context, "Fill in the palindrome field", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                hint = "CHECK"
            )

            Spacer(modifier = Modifier.height(14.dp))

            MyButton(
                onClick = {
                    if (nameText.isNotEmpty()) {
                        navigateToSecondScreen(nameText)
                    } else {
                        Toast.makeText(context, "Fill in the name field", Toast.LENGTH_SHORT).show()
                    }
                },
                hint = "NEXT"
            )
        }

        if (showDialog) {
            val message = if (isPalindrome) {
                "isPalindrome"
            } else {
                "not palindrome"
            }

            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    MyButton(onClick = { showDialog = false }, hint = "OK")
                },
                text = {
                    Text(message)
                }
            )
        }
    }
}

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(text = placeholder)
        },
        singleLine = true,
        colors = TextFieldDefaults.colors()
    )
}

