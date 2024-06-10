package com.bewe.suitmediamobiledev.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bewe.suitmediamobiledev.ui.theme.ButtonBgColor

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    hint: String,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonBgColor
        )
    ) {
        Text(text = hint)
    }
}