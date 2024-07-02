package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.harmonyViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.ButtonNext
import com.fourleafclover.tarot.ui.component.ButtonText
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.NicknameViewModel
import com.fourleafclover.tarot.ui.theme.TextB02M16
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextCaptionM12
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.errorRed
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.transparent
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getPickedTopic

@Preview
@Composable
fun RoomNicknameScreen(
    navController: NavHostController = rememberNavController(),
    nicknameViewModel: NicknameViewModel = remember { NicknameViewModel() }
) {

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
                .padding(top = 24.dp)
        ) {

            Column(modifier = Modifier.weight(1f)) {
                TextB02M16(text = "그 사람과의 운명을 확인해봐요.", color = gray_3)
                TextH02M22(text = "궁합을 보러 갈 준비가 되셨나요?", color = white)

                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .background(shape = RoundedCornerShape(10.dp), color = gray_7)
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextField(
                        modifier = Modifier.weight(1f),
                        textStyle = getTextStyle(
                            fontSize = 16,
                            fontWeight = FontWeight.Bold,
                            color = white
                        ),
                        onValueChange = { newText -> nicknameViewModel.updateNickname(newText) },
                        value = nicknameViewModel.getNicknameString(),
                        placeholder = {
                            TextB03M14(
                                text = "별명을 입력해주세요.",
                                color = gray_5
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            cursorColor = white,
                            focusedTextColor = gray_3,
                            focusedContainerColor = transparent,
                            unfocusedContainerColor = transparent,
                            disabledContainerColor = transparent,
                            focusedIndicatorColor = transparent,
                            disabledIndicatorColor = transparent,
                            unfocusedIndicatorColor = transparent
                        )
                    )

                    // 갯수 표시
                    Row(modifier = Modifier) {
                        Text(
                            text = "${nicknameViewModel.getNicknameLength()}", style = getTextStyle(
                                fontSize = 14,
                                fontWeight = FontWeight.Medium,
                                color = highlightPurple,
                            )
                        )

                        Text(
                            text = "/10", style = getTextStyle(
                                fontSize = 14,
                                fontWeight = FontWeight.Medium,
                                color = gray_6,
                            )
                        )

                    }

                }

                TextCaptionM12(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .alpha(if (nicknameViewModel.getIsCaptionVisible()) 1f else 0f),
                    text = "최대 10글자를 입력할 수 있어요.",
                    color = errorRed
                )
            }

            ButtonNext(
                onClick = {
                    harmonyViewModel.setUserNickname(nicknameViewModel.getNicknameString())

                    // 새로 방을 생성
                    if (harmonyViewModel.roomCode.value.isEmpty()) {
                        loadingViewModel.startLoading(
                            navController,
                            ScreenEnum.RoomCreateLoadingScreen,
                            ScreenEnum.RoomShareScreen
                        )
                    }
                    // 초대받아 입장
                    else{
                        loadingViewModel.startLoading(
                            navController,
                            ScreenEnum.RoomInviteLoadingScreen,
                            ScreenEnum.RoomEnteringScreen
                        )
                    }
                },
                enabled = { nicknameViewModel.isCompleted() },
                content = { ButtonText(isEnabled = nicknameViewModel.isCompleted(), text = "입력완료") }
            )
        }


    }

}