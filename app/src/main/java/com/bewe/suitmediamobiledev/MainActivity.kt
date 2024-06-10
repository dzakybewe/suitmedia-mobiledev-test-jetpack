package com.bewe.suitmediamobiledev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bewe.suitmediamobiledev.ui.theme.SuitmediaMobileDevTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuitmediaMobileDevTheme {
                SuitmediaApp()
            }
        }
    }
}
