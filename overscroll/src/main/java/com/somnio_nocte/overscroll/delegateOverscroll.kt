package com.somnio_nocte.overscroll

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.nestedscroll.nestedScroll

/**
 * Modifier that uses nestedScrollConnection under the hood and uses the passed animatable as the overscroll position state.
 * */
fun Modifier.delegateOverscroll(
    mainScroll: ScrollableState,
    overscrollOffset: Animatable<Float, AnimationVector1D>,
    orientation: Orientation = Orientation.Vertical,
    onGestureUp: () -> Unit = {  },
    onGestureDown: () -> Unit = {  }
) = composed {
    val scope = rememberCoroutineScope()

    nestedScroll(
        remember(
            mainScroll,
            overscrollOffset,
            orientation,
            onGestureDown,
            onGestureUp
        ) {
            nestedBouncedScrollConnection(
                mainScroll,
                overscrollOffset,
                orientation,
                scope,
                onGestureDown,
                onGestureUp
            )
        }
    )
}