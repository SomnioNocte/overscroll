package com.somnio_nocte.overscroll

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.exp

/**
 * A modifier that applies nestedScrollConnection and binds its overscroll animation to graphicsLayer for offset.
 *
 * If you need to apply your own overscroll animation use delegateOverscroll.
 * */
fun Modifier.bouncedOverscroll(
    mainScroll: ScrollableState,
    orientation: Orientation = Orientation.Vertical,
    onGestureUp: () -> Unit = {  },
    onGestureDown: () -> Unit = {  }
) = composed {
    val overscrollOffset = remember { Animatable(0f) }

    graphicsLayer {
        if(orientation == Orientation.Vertical) translationY = overscrollOffset.value / exp(1f)
        else translationX = overscrollOffset.value / exp(1f)
    }.delegateOverscroll(mainScroll, overscrollOffset, orientation, onGestureUp, onGestureDown)
}