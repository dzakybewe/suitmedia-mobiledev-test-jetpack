package com.bewe.suitmediamobiledev.ui.screens.second

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bewe.suitmediamobiledev.ui.components.MyButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    modifier: Modifier = Modifier,
    name: String,
    navigateToThirdScreen: () -> Unit,
    navigateBack: () -> Unit,
    selectedUserName: String?
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Second Screen",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 20.dp, bottom = 20.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "Welcome",
                fontWeight = FontWeight.W400,
                fontSize = 12.sp
            )
            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedUserName ?: "Selected User Name",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            }

            MyButton(onClick = navigateToThirdScreen , hint = "Choose a User")
        }
    }
}