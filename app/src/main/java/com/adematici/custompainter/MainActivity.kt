package com.adematici.custompainter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adematici.custompainter.ui.theme.CustomPainterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomPainterTheme {
                val customPainter = remember { CrossCirclePainter() }
                val customPainter2 = remember { CrossCirclePainter() }

                Column(modifier = Modifier.statusBarsPadding()) {
                    Box {
                        Image(
                            painter = customPainter,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(color = Color.Gray)
                            .padding(30.dp)
                            .background(color = Color.Yellow)
                            .paint(customPainter2),
                    ) {
                        // sonar - comment
                    }
                }
            }
        }
    }
}
