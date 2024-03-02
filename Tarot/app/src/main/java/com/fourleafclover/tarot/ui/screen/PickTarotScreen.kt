
package com.fourleafclover.tarot.ui.screen

import android.util.Log
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.getRandomCards
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.tarotInputDto
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.backgroundModifier
import com.fourleafclover.tarot.ui.component.setStatusbarColor
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.theme.backgroundColor_1
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_4
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getCardImageId
import com.fourleafclover.tarot.utils.getPickedTopic
import kotlin.math.roundToInt


@Preview
@Composable
fun PickTarotScreen(navController: NavHostController = rememberNavController()) {
    val localContext = LocalContext.current
    var showIndicator by remember { mutableStateOf(MyApplication.prefs.isPickCardIndicateComplete()) }
    setStatusbarColor(LocalView.current, backgroundColor_1)

    Box(modifier = backgroundModifier) {

        Column {

            AppBarClose(navController = navController,
                pickedTopicTemplate = getPickedTopic(pickedTopicNumber),
                backgroundColor_1)


            val dash = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)))
            var cardNumber by remember { mutableIntStateOf(1) }
            var cardSelected by remember { mutableStateOf(false) }

            var firstCardPicked by remember { mutableStateOf(false) }
            var secondCardPicked by remember { mutableStateOf(false) }
            var thirdCardPicked by remember { mutableStateOf(false) }

            var firstCardNumber by remember { mutableIntStateOf(-1) }
            var secondCardNumber by remember { mutableIntStateOf(-1) }
            var thirdCardNumber by remember { mutableIntStateOf(-1) }

            var nowSelected by remember { mutableIntStateOf(-1) }

            val pxToMove = with(LocalDensity.current) { -32.dp.toPx().roundToInt() }


            val cards = remember { mutableStateListOf<Int>().apply{ addAll(getRandomCards()) } }


            Text(
                text = if (cardNumber == 1) "첫 번째 카드를 골라주세요."
                else if(cardNumber == 2) "두 번째 카드를 골라주세요."
                else "세 번째 카드를 골라주세요.",
                style = getTextStyle(22, FontWeight.Medium, white),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 32.dp)
            )

            Column(modifier = Modifier.fillMaxSize()) {

                Column(modifier = Modifier.weight(1f)) {

                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center)
                    {

                        Box(modifier = Modifier
                            .width(54.dp)
                            .drawBehind {
                                drawRoundRect(
                                    color = gray_4,
                                    style = dash,
                                    alpha = if (firstCardPicked) 0f else 1f
                                )
                            }) {

                            Image(painter = painterResource(
                                id = if (firstCardPicked) getCardImageId(localContext, firstCardNumber.toString())
                                else R.drawable.tarot_front),
                                contentDescription = null,
                                modifier = Modifier,
                                alpha = if(firstCardPicked) 1f else 0f)
                            Text(text = "첫번째\n 카드",
                                style = getTextStyle(fontSize = 12, fontWeight = FontWeight.Normal, color = gray_4),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.Center)
                                    .alpha(if (firstCardPicked) 0f else 1f),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp)

                        }

                        Box(modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .width(54.dp)
                            .drawBehind {
                                drawRoundRect(
                                    color = gray_4,
                                    style = dash,
                                    alpha = if (secondCardPicked) 0f else 1f
                                )
                            }) {

                            Image(painter = painterResource(
                                id = if (secondCardPicked) getCardImageId(localContext, secondCardNumber.toString())
                                else R.drawable.tarot_front),
                                contentDescription = null,
                                modifier = Modifier,
                                alpha = if(secondCardPicked) 1f else 0f)
                            Text(text = "두번째\n 카드",
                                style = getTextStyle(fontSize = 12, fontWeight = FontWeight.Normal, color = gray_4),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.Center)
                                    .alpha(if (secondCardPicked) 0f else 1f),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp)

                        }

                        Box(modifier = Modifier
                            .width(54.dp)
                            .drawBehind {
                                drawRoundRect(
                                    color = gray_4,
                                    style = dash,
                                    alpha = if (thirdCardPicked) 0f else 1f
                                )
                            }) {

                            Image(painter = painterResource(
                                id = if (thirdCardPicked) getCardImageId(localContext, thirdCardNumber.toString())
                                else R.drawable.tarot_front),
                                contentDescription = null,
                                modifier = Modifier,
                                alpha = if(thirdCardPicked) 1f else 0f)
                            Text(text = "세번째\n 카드",
                                style = getTextStyle(fontSize = 12, fontWeight = FontWeight.Normal, color = gray_4),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.Center)
                                    .alpha(if (thirdCardPicked) 0f else 1f),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp)

                        }
                    }


                    // 카드덱
                    Column(modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center) {

                        LazyRow(
                            modifier = Modifier
                                .wrapContentWidth()
                                .weight(1f),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy((-46).dp)
                        ) {

                            items(cards.size) { index ->

                                val offset by animateIntOffsetAsState(
                                    targetValue = if (nowSelected == index) {
                                        IntOffset(0, pxToMove)
                                    } else {
                                        IntOffset.Zero
                                    },
                                    label = "offset"
                                )


                                Image(painter = painterResource(id = R.drawable.tarot_front),
                                    contentDescription = "$index",
                                    modifier = Modifier
                                        .width(80.dp)
                                        .offset {
                                            offset
                                        }
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            Log.d("", "nowSelected: $index")
                                            cardSelected = true
                                            nowSelected = index
                                        })
                            }

                        }

                        // 인디케이터
                        Image(painter = painterResource(id = R.drawable.tarot_pick_indicator),
                            contentDescription = "",
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 24.dp, bottom = 36.dp),
                            alpha = if(showIndicator) 1f else 0f
                        )

                    }

                }


                Button(
                    onClick = {
                        if (cardNumber == 1){
                            firstCardPicked = true
                            cardSelected = false
                            firstCardNumber = cards[nowSelected]
                            cards.remove(firstCardNumber)
                            cardNumber = 2
                        }
                        else if (cardNumber == 2){
                            secondCardPicked = true
                            cardSelected = false
                            secondCardNumber = cards[nowSelected]
                            cards.remove(secondCardNumber)
                            cardNumber = 3

                        }
                        else if (cardNumber == 3) {
                            thirdCardNumber = cards[nowSelected]
                            cards.remove(thirdCardNumber)
                            thirdCardPicked = true

                            tarotInputDto.cards = arrayListOf(firstCardNumber, secondCardNumber, thirdCardNumber)

                            navigateInclusive(navController, ScreenEnum.LoadingScreen.name)
                        }

                        Log.d("", "${cards.size}, {${cards.joinToString (",")}}")
//                    Log.d("", "cardNum: $cardNumber, cardVal: ${entireCards[cards[nowSelected]]}, 1: $firstCardIndex, 2: $secondCardIndex, 3: $thirdCardIndex")
                        nowSelected = -1

                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(bottom = 34.dp)
                        .padding(horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = highlightPurple,
                        contentColor = gray_1,
                        disabledContainerColor = gray_5,
                        disabledContentColor = gray_6
                    ),
                    enabled = cardSelected
                ) {
                    Text(text = "선택완료", modifier = Modifier.padding(vertical = 8.dp),
                        style = getTextStyle(
                            fontSize = 16,
                            fontWeight = FontWeight.Medium
                        ))
                }
            }
        }

        if (showIndicator)
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    showIndicator = false
                    MyApplication.prefs.setPickCardIndicateComplete()
                })
    }
}
