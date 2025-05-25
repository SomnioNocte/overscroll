# Overscroll

Overscroll is a simple API for creating spring effect on scroll and gestures on overscroll for your Jetpack Compose app.

Example of use: 
```Kotlin
val scrollState = rememberScrollState()

Column(
    Modifier
        .fillMaxSize()
        .bouncedOverscroll(scrollState)
        .verticalScroll(scrollState)
        .safeContentPadding()
) {
    repeat(100) {
        Text("Some text $it")
    }
}
```

https://github.com/SomnioNocte/overscroll/blob/master/docs/Example1.mp4

### `bouncedOverscroll`

A modifier that applies nestedScrollConnection and binds its overscroll animation to graphicsLayer for offset.
If you need to apply your own overscroll animation use delegateOverscroll.

### `delegateOverscroll`

Modifier that uses nestedScrollConnection under the hood and uses the passed animatable as the overscroll position state.

``` Kotlin
fun Modifier.bouncedOverscroll(
    mainScroll: ScrollableState,
    overscrollOffset: Animatable<Float, AnimationVector1D>? = null,
    orientation: Orientation = Orientation.Vertical,
    gestureThreshold: Int? = null,
    onGestureUp: () -> Unit = {  },
    onGestureDown: () -> Unit = {  }
)
```

In addition to the iOS scrolling effect, this functionality has more practical uses, such as smoothly closing a modal window using scrolling.

### TODO

1. [ ] State for gestures, with confirmation or rejection to simplify the implementation of UI gesture indicators.
2. [ ] Fix visual bugs when overscrolling.