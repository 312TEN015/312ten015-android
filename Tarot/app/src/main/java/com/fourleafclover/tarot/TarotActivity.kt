package com.fourleafclover.tarot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.fourleafclover.tarot.CircularListStateImpl.Companion.Saver
import com.fourleafclover.tarot.ui.theme.TarotTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sqrt

class TarotActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            TarotTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }

            GreetingPreview()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

private val colors = listOf(
    Color.Red,
    Color.Green,
    Color.Blue,
    Color.Magenta,
    Color.Yellow,
    Color.Cyan,
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TarotTheme {
        CircularList(
            visibleItems = 12,
            modifier = Modifier.fillMaxWidth(),
            circularFraction = .65f,
            content = {
                for (i in 0 until 40) {
                    ListItem(
                        text = "Item #$i",
                        color = colors[i % colors.size],
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        )
    }
}

@Composable
// 1
fun CircularList(
    visibleItems: Int,
    modifier: Modifier = Modifier,
    state: CircularListState = rememberCircularListState(),
    circularFraction: Float = 1f,   // with 1f meaning perfectly circular
    overshootItems: Int = 3,
    content: @Composable () -> Unit,
) {
    // 2
    check(visibleItems > 0) { "Visible items must be positive" }
    check(circularFraction > 0f) { "Circular fraction must be positive" }

    // 3
    Layout(
        modifier = modifier.clipToBounds().drag(state),
        content = content,
    ) { measurables, constraints ->
        // 4
        val itemHeight = constraints.maxHeight / visibleItems
        // 5
        val itemConstraints = Constraints.fixed(width = constraints.maxWidth, height = itemHeight)
        // 6
        val placeables = measurables.map { measurable -> measurable.measure(itemConstraints) }
        // 7

        state.setup(
            CircularListConfig(
                contentHeight = constraints.maxHeight.toFloat(),
                numItems = placeables.size,
                visibleItems = visibleItems,
                circularFraction = circularFraction,
            )
        )

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            // 3
            for (i in state.firstVisibleItem..state.lastVisibleItem) {
                // 4
                placeables[i].placeRelative(state.offsetFor(i))
            }
        }
    }
}

@Composable
fun ListItem(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .padding(all = 8.dp)
                .clip(shape = CircleShape)
                .background(color = color)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

// 1
data class CircularListConfig(
    val contentHeight: Float = 0f,
    val numItems: Int = 0,
    val visibleItems: Int = 0,
    val circularFraction: Float = 1f,
    val overshootItems: Int = 0,
)

// 2
@Stable
interface CircularListState {
    val verticalOffset: Float
    val firstVisibleItem: Int
    val lastVisibleItem: Int

    suspend fun snapTo(value: Float)
    suspend fun decayTo(velocity: Float, value: Float)
    suspend fun stop()
    fun offsetFor(index: Int): IntOffset
    fun setup(config: CircularListConfig)
}

// 3
class CircularListStateImpl(
    currentOffset: Float = 0f,
) : CircularListState {

    private val animatable = Animatable(currentOffset)
    private var itemHeight = 0f
    private var config = CircularListConfig()
    private var initialOffset = 0f

    // 1
    private val decayAnimationSpec = FloatSpringSpec(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    )

    private val minOffset: Float
        get() = -(config.numItems - 1) * itemHeight

    override val verticalOffset: Float
        get() = animatable.value

    override val firstVisibleItem: Int
        get() = ((-verticalOffset - initialOffset) / itemHeight).toInt().coerceAtLeast(0)

    override val lastVisibleItem: Int
        get() = (((-verticalOffset - initialOffset) / itemHeight).toInt() + config.visibleItems)
            .coerceAtMost(config.numItems - 1)

    override suspend fun snapTo(value: Float) {
        // 2
        val minOvershoot = -(config.numItems - 1 + config.overshootItems) * itemHeight
        // 3
        val maxOvershoot = config.overshootItems * itemHeight
        animatable.snapTo(value.coerceIn(minOvershoot, maxOvershoot))
    }

    // 2
    override suspend fun decayTo(velocity: Float, value: Float) {
        val constrainedValue = value.coerceIn(minOffset, 0f).absoluteValue
        val remainder = (constrainedValue / itemHeight) - (constrainedValue / itemHeight).toInt()
        val extra = if (remainder <= 0.5f) 0 else 1
        val target =((constrainedValue / itemHeight).toInt() + extra) * itemHeight
        animatable.animateTo(
            targetValue = -target,
            initialVelocity = velocity,
            animationSpec = decayAnimationSpec,
        )
    }


    override suspend fun stop() {
        animatable.stop()
    }

    override fun setup(config: CircularListConfig) {
        this.config = config
        itemHeight = config.contentHeight / config.visibleItems
        initialOffset = (config.contentHeight - itemHeight) / 2f
    }

    override fun offsetFor(index: Int): IntOffset {
        // 1
        val maxOffset = config.contentHeight / 2f + itemHeight / 2f
        val y = (verticalOffset + initialOffset + index * itemHeight)
        // 2
        val deltaFromCenter = (y - initialOffset)
        // 3
        val radius = config.contentHeight / 2f
        // 4
        val scaledY = deltaFromCenter.absoluteValue * (config.contentHeight / 2f / maxOffset)
        // 5
        val x = if (scaledY < radius) {
            sqrt((radius * radius - scaledY * scaledY))
        } else {
            0f
        }
        return IntOffset(
            // 6
            x = (x * config.circularFraction).roundToInt(),
            y = y.roundToInt()
        )
    }

    companion object {
        val Saver = Saver<CircularListStateImpl, List<Any>>(
            save = { listOf(it.verticalOffset) },
            restore = {
                CircularListStateImpl(it[0] as Float)
            }
        )
    }
}

// 4
@Composable
fun rememberCircularListState(): CircularListState {
    val state = rememberSaveable(saver = CircularListStateImpl.Saver) {
        CircularListStateImpl()
    }
    return state
}


private fun Modifier.drag(
    state: CircularListState,
) = pointerInput(Unit) {
    val decay = splineBasedDecay<Float>(this)
    coroutineScope {
        while (true) {
            val pointerId = awaitPointerEventScope { awaitFirstDown().id }
            state.stop()
            // 2
            val tracker = VelocityTracker()
            awaitPointerEventScope {
                verticalDrag(pointerId) { change ->
                    val verticalDragOffset = state.verticalOffset + change.positionChange().y
                    launch {
                        state.snapTo(verticalDragOffset)
                    }
                    // 3
                    tracker.addPosition(change.uptimeMillis, change.position)
                    change.consumePositionChange()
                }
            }
            // 4
            val velocity = tracker.calculateVelocity().y
            // 5
            val targetValue = decay.calculateTargetValue(state.verticalOffset, velocity)
            launch {
                // 6
                state.decayTo(velocity, targetValue)
            }
        }
    }
}