package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.ui.component.AppBarPlain
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.component.getOutlinedRectangleModifier
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateSaveState
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.TextH03SB18
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.transparent
import com.fourleafclover.tarot.ui.theme.white

@Preview
@Composable
fun RoomCreateScreen(navController: NavHostController = rememberNavController()) {

    Column(modifier = getBackgroundModifier(backgroundColor_2)) {
        AppBarPlain(
            navController,
            "",
            backgroundColor = backgroundColor_2,
            backButtonResource = R.drawable.arrow_left_white
        )

        Column(
            modifier = getBackgroundModifier(backgroundColor_2)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            TextH02M22(
                text = "초대방을 만들고, 궁합을 함께 볼 상대방을 초대해보세요.",
                modifier = Modifier.padding(vertical = 24.dp),
                color = white
            )

            Box(
                modifier = Modifier
                    .padding(bottom = 34.dp)
                    .background(color = white, shape = RoundedCornerShape(10.dp))
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .clickable { navigateSaveState(navController, ScreenEnum.RoomGenderScreen.name) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 44.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    TextH03SB18(text = "초대방 생성하기", modifier = Modifier)

                    Image(
                        painter = painterResource(id = R.drawable.arrow_right_black),
                        contentDescription = null,
                        alignment = Alignment.CenterEnd
                    )
                }
            }

            Column(
                modifier = getOutlinedRectangleModifier(
                    borderColor = gray_7,
                    fillColor = transparent,
                    cornerRadius = 10.dp
                )
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 28.dp, top = 24.dp, end = 28.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextB03M14(
                    text = "궁합 운세 보기 3단계\uD83D\uDD2E",
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .background(
                            color = Color(0x20B479FF),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    color = white
                )

                DescriptionStep(
                    number = "1",
                    title = "초대방을 생성해주세요.",
                    imageResourceId = R.drawable.pencil
                )
                DescriptionStep(
                    number = "2",
                    title = "궁합을 함께 볼 상대방에게 \n초대방 링크를 전달해요.",
                    imageResourceId = R.drawable.unlink
                )
                DescriptionStep(
                    number = "3",
                    title = "상대방이 초대방에 입장하면 \n함께 궁합 보기를 시작해요!",
                    imageResourceId = R.drawable.happy_outline
                )

            }


        }
    }


}

@Composable
fun DescriptionStep(
    number: String,
    title: String,
    imageResourceId: Int
) {

    Row(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row {
            TextB03M14(
                text = number,
                modifier = Modifier
                    .size(22.dp)
                    .background(
                        color = gray_6,
                        shape = CircleShape
                    ),
                color = white,
                textAlign = TextAlign.Center,
            )

            TextB03M14(
                text = title,
                modifier = Modifier.padding(start = 8.dp),
                color = white
            )
        }

        Image(
            modifier = Modifier.background(shape = CircleShape, color = gray_8).padding(14.dp),
            painter = painterResource(id = imageResourceId),
            contentDescription = null,
            alignment = Alignment.Center,
        )

    }
}