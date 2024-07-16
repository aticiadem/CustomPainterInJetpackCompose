## Using Custom Painter in Jetpack Compose

<div style="text-align: center;">
    <img src="https://miro.medium.com/v2/resize:fit:1400/format:webp/0*pgSjD3xlz1AHJT2L" alt="Description" style="max-width: 100%; height: auto;">
</div>

Jetpack Compose provides a `Painter` object to replace the `Drawable` APIs used for drawing graphics in Android. A `Painter` object represents something that can be drawn and influences the measurement and layout of the `composable` it is used in. For example, a `BitmapPainter` takes an `ImageBitmap` that can draw a `Bitmap` on the screen.

A `Painter` is different from a `DrawModifier`. A `DrawModifier` strictly draws within the bounds given to it and does not influence the measurement or layout of the `composable`.

### Creating a Custom Painter
To create a custom painter, extend the `Painter` class and implement the `onDraw` method. This method provides access to the `DrawScope` to draw custom graphics. You can also override the `intrinsicSize`, which will be used to influence the `Composable` that it is contained in.

Below is an example of a custom painter that overlays two images:

```kotlin
class OverlayImagePainter(
    private val image: ImageBitmap,
    private val imageOverlay: ImageBitmap,
    private val srcOffset: IntOffset = IntOffset.Zero,
    private val srcSize: IntSize = IntSize(image.width, image.height),
    private val overlaySize: IntSize = IntSize(imageOverlay.width, imageOverlay.height),
) : Painter() {

    private val size: IntSize = validateSize(srcOffset, srcSize)
    
    override fun DrawScope.onDraw() {
        drawImage(
            image,
            srcOffset,
            srcSize,
            dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt(),
            ),
        )

        drawImage(
            imageOverlay,
            srcOffset,
            overlaySize,
            dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            ),
            blendMode = BlendMode.Overlay,
        )
    }

    override val intrinsicSize: Size get() = size.toSize()

    private fun validateSize(srcOffset: IntOffset, srcSize: IntSize): IntSize {
        require(
            srcOffset.x >= 0 &&
            srcOffset.y >= 0 &&
            srcSize.width >= 0 &&
            srcSize.height >= 0 &&
            srcSize.width <= image.width &&
            srcSize.height <= image.height
        )
        return srcSize
    }
}
```
You can overlay any image on top of your source image with this custom painter as follows:
```kotlin
val rainbowImage = ImageBitmap.imageResource(id = R.drawable.rainbow)
val dogImage = ImageBitmap.imageResource(id = R.drawable.dog)
val customPainter = remember {
    OverlayImagePainter(dogImage, rainbowImage)
}

Image(
    painter = customPainter,
    contentDescription = stringResource(id = R.string.dog_content_description),
    contentScale = ContentScale.Crop,
    modifier = Modifier.wrapContentSize(),
)
```

<p align="center">
  <img src="https://miro.medium.com/v2/resize:fit:1256/format:webp/1*uccyb4W3RZMbMIuT5bB_gA.jpeg" alt="Description" style="max-width: 60%; height: auto;">
</p>

## Creating a Different Example Using Custom Painter

Now, instead of the `OverlayImagePainter` example, we will create our own custom painter and draw a new example. In this example, we will create a painter that draws a simple circle with a cross on top of it:
```kotlin
class CrossCirclePainter : Painter() {
    override val intrinsicSize: Size = Size(100f, 100f)

    override fun DrawScope.onDraw() {
        // Circle
        drawCircle(
            color = Color.Blue,
            radius = size.minDimension / 2,
            center = center,
        )

        // Cross
        val strokeWidth = 5f

        drawLine(
            color = Color.Red,
            start = Offset(center.x - size.minDimension / 4, center.y - size.minDimension / 4),
            end = Offset(center.x + size.minDimension / 4, center.y + size.minDimension / 4),
            strokeWidth = strokeWidth,
        )

        drawLine(
            color = Color.Red,
            start = Offset(center.x - size.minDimension / 4, center.y + size.minDimension / 4),
            end = Offset(center.x + size.minDimension / 4, center.y - size.minDimension / 4),
            strokeWidth = strokeWidth,
        )
    }
}
```
To use this painter within a Composable:
```kotlin
val customPainter = remember { CrossCirclePainter() }

Image(
    painter = customPainter,
    contentDescription = null,
    modifier = Modifier.size(100.dp),
)
```
## Drawing Inside a Box Using Custom Painter
You can also use the custom painter inside a `Box` with `Modifier.paint(customPainter)`:
```kotlin
val customPainter = remember { CrossCirclePainter() }

Box(
    modifier = Modifier
        .background(color = Color.Gray)
        .padding(30.dp)
        .background(color = Color.Yellow)
        .paint(customPainter),
) {
    // sonar - comment
}
```
Drawing graphics using a custom painter in Jetpack Compose is quite flexible and powerful. By extending the `Painter` class, you can create your own graphics and integrate them into your Composable elements. If you want to make a drawing that influences measurement or layout, you should use the `Painter` class. Otherwise, if you only need to draw within the given bounds, using a `DrawModifier` would be more appropriate.

<p align="center">
  <img src="https://miro.medium.com/v2/resize:fit:606/format:webp/1*tJvyB3FNkmOO2jSRQ1JAqg.jpeg" alt="Description" style="max-width: 60%; height: auto;">
</p>

You learned how to create custom graphics using two different custom painter examples and how to use these graphics within Composable elements. I hope you found it helpful :)
