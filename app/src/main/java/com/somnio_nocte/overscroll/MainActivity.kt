package com.somnio_nocte.overscroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.somnio_nocte.overscroll.ui.theme.OverscrollTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val colors = remember {
                listOf(
                    Color(186, 187, 241),
                    Color(140, 170, 238),
                    Color(133, 193, 220),
                    Color(153, 209, 219),
                    Color(129, 200, 190),
                    Color(166, 209, 137),
                    Color(229, 200, 144),
                    Color(239, 159, 118),
                    Color(234, 153, 156),
                    Color(231, 130, 132),
                    Color(202, 158, 230),
                    Color(244, 184, 228),
                    Color(238, 190, 190),
                    Color(242, 213, 207)
                )
            }

            OverscrollTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val scrollState = rememberLazyListState()
                    var list by remember { mutableStateOf(List(50) { colors.random() }) }
                    val overscroll = remember { Animatable(0f) }

                    Box(
                        Modifier.fillMaxWidth().padding(16.dp).statusBarsPadding(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.Refresh, "Update",
                            Modifier
                                .size(32.dp)
                                .graphicsLayer {
                                    alpha = ((overscroll.value - 150) / 300f).coerceIn(0f..1f)
                                    rotationZ = lerp(-180f, 0f, alpha)
                                }
                                .background(
                                    MaterialTheme.colorScheme.surfaceContainerLowest,
                                    RoundedCornerShape(16.dp)
                                )
                                .padding(4.dp)
                        )
                    }

                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .bouncedOverscroll(
                                scrollState,
                                overscroll,
                                gestureThreshold = 450,
                                onGestureDown = { list = List(50) { colors.random() } }
                            ),
                        state = scrollState,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = innerPadding
                    ) {
                        items(list.size) {
                            Box(Modifier
                                .animateItem()
                                .fillMaxWidth(.6f)
                                .height(64.dp)
                                .background(list[it], RoundedCornerShape(32.dp))
                            ) {
                                Text("$it", Modifier.align(Alignment.Center), color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}