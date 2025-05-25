package com.somnio_nocte.overscroll

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.exp

private fun mainAxes(target: Offset, orientation: Orientation): Float {
    return if(orientation == Orientation.Vertical) target.y else target.x
}

private fun mainAxes(target: Velocity, orientation: Orientation): Float {
    return if(orientation == Orientation.Vertical) target.y else target.x
}

fun nestedBouncedScrollConnection(
    scrollState: ScrollableState,
    overscrollOffset: Animatable<Float, AnimationVector1D>,
    orientation: Orientation,
    scope: CoroutineScope,
    onGestureDown: () -> Unit = {  },
    onGestureUp: () -> Unit = {  },
): NestedScrollConnection {
    return object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val isRightAxis =
                if(available.x != 0f) orientation == Orientation.Horizontal
                else orientation == Orientation.Vertical

            if(isRightAxis && source == NestedScrollSource.UserInput) {
                if(overscrollOffset.targetValue != 0f) {
                    return if((overscrollOffset.value + mainAxes(available, orientation)) > 0 && !scrollState.canScrollBackward ||
                        (overscrollOffset.value + mainAxes(available, orientation)) < 0 && !scrollState.canScrollForward) {
                        scope.launch { overscrollOffset.snapTo((mainAxes(available, orientation) / exp(.5f)) + overscrollOffset.value) }
                        available
                    } else {
                        scope.launch { overscrollOffset.animateTo(0f, spring(1f, 1500f)) }
                        Offset.Zero
                    }
                }
            }

            return Offset.Zero
        }

        override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
            val isRightAxis =
                if(available.x != 0f) orientation == Orientation.Horizontal
                else orientation == Orientation.Vertical

            return if(isRightAxis && source == NestedScrollSource.UserInput) {
                scope.launch { overscrollOffset.snapTo((mainAxes(available, orientation) / exp(.5f)) + overscrollOffset.value) }
                available
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val isRightAxis =
                if(available.x != 0f) orientation == Orientation.Horizontal
                else orientation == Orientation.Vertical

            if(isRightAxis) {
                if(overscrollOffset.targetValue != 0f) {
                    scope.launch { overscrollOffset.animateTo(0f, spring(1f, 200f), mainAxes(available, orientation) / 1.15f) }
                    return available.copy(y = overscrollOffset.value * -2 + 250f)
                }

                scope.launch { overscrollOffset.animateTo(0f, spring(1f, 1500f)) }
            }

            return Velocity.Zero
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            val isRightAxis =
                if(available.x != 0f) orientation == Orientation.Horizontal
                else orientation == Orientation.Vertical

            if(isRightAxis) {
                scope.launch { overscrollOffset.animateTo(0f, spring(1f, 200f), mainAxes(available, orientation) / 1.15f) }

                if(overscrollOffset.value > 250f && mainAxes(available, orientation) > 1500f ||
                    overscrollOffset.value > 125f && mainAxes(available, orientation) > 2500f)
                    onGestureDown()
                else if(overscrollOffset.value < 250f && mainAxes(available, orientation) < 1500f ||
                    overscrollOffset.value < 125f && mainAxes(available, orientation) < 2500f)
                    onGestureUp()

                return available
            } else {
                return Velocity.Zero
            }
        }
    }
}