# Overscroll

Overscroll is a library for creating spring effect on scroll and gestures on overscroll for your Jetpack Compose app.

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

Result: 

https://github.com/user-attachments/assets/2ba308d5-cd1f-4a9b-a929-bdb40016911c

### `bouncedOverscroll`

A modifier that applies nestedScrollConnection and binds its overscroll animation to graphicsLayer for offset.
If you need to apply your own overscroll animation use delegateOverscroll.

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

### `delegateOverscroll`

Modifier that uses nestedScrollConnection under the hood and uses the passed animatable as the overscroll position state.

In addition to the iOS scrolling effect, this functionality has more practical uses, such as smoothly closing a modal window using scrolling or well-known refresh gesture.

[Sample app](https://github.com/SomnioNocte/overscroll/blob/master/app/src/main/java/com/somnio_nocte/overscroll/MainActivity.kt):

https://github.com/user-attachments/assets/70296e0a-f616-4929-928c-77aa22114cbd

### TODO

1. [ ] State for gestures, with confirmation or rejection to simplify the implementation of UI gesture indicators.
2. [ ] Fix visual bugs when overscrolling.
