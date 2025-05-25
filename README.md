# Overscroll

Overscroll is a simple API for creating spring effect on scroll and gestures on overscroll for your Jetpack Compose app.

### `bouncedOverscroll`

A modifier that applies nestedScrollConnection and binds its overscroll animation to graphicsLayer for offset.
If you need to apply your own overscroll animation use delegateOverscroll.

### `delegateOverscroll`

Modifier that uses nestedScrollConnection under the hood and uses the passed animatable as the overscroll position state.

In addition to the iOS scrolling effect, this functionality has more practical uses, such as smoothly closing a modal window using scrolling.