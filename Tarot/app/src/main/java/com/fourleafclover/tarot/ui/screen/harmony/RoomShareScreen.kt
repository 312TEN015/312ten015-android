package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.harmonyViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.ButtonNext
import com.fourleafclover.tarot.ui.component.ButtonText
import com.fourleafclover.tarot.ui.component.HowToShareButton
import com.fourleafclover.tarot.ui.component.ShareLinkOrCopy
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.component.getOutlinedRectangleModifier
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateSaveState
import com.fourleafclover.tarot.ui.theme.TextB02M16
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextB04M12
import com.fourleafclover.tarot.ui.theme.TextCaptionM12
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.TextH03SB18
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getPickedTopic
import org.json.JSONObject

@Preview
@Composable
fun RoomShareScreen(navController: NavHostController = rememberNavController()) {

    Column(modifier = getBackgroundModifier(backgroundColor_2)) {
        AppBarClose(
            navController = navController,
            pickedTopicTemplate = getPickedTopic(pickedTopicNumber),
            backgroundColor = backgroundColor_2,
            isTitleVisible = false
        )

        Column(
            modifier = getBackgroundModifier(backgroundColor_2)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.letter),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 45.dp, bottom = 74.dp)
                )

                TextH02M22(
                    text = "궁합 초대 방이 생성되었어요!",
                    color = white,
                    modifier = Modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
                TextB02M16(
                    text = "궁합을 볼 상대방에게 초대 링크를 전달하고,\n함께 궁합 운세 보기를 시작해요!",
                    color = gray_5,
                    modifier = Modifier.padding(bottom = 40.dp),
                    textAlign = TextAlign.Center
                )

                ShareLinkOrCopy()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.alert),
                        contentDescription = null
                    )
                    TextB04M12(
                        text = "1시간 안에 모두 입장하지 않으면 초대방이 사라져요!",
                        color = gray_6,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }


            }

            ButtonNext(
                onClick = { loadingViewModel.startLoading(navController, ScreenEnum.RoomInviteLoadingScreen, ScreenEnum.RoomEnteringScreen) },
                enabled = { true },
                content = { ButtonText(true, "초대방 입장") }
            )
        }


    }
}

