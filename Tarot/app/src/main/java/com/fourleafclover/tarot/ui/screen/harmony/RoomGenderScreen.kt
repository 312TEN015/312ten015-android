package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.pickedTopicNumber
import com.fourleafclover.tarot.ui.component.AppBarClose
import com.fourleafclover.tarot.ui.component.ButtonNext
import com.fourleafclover.tarot.ui.component.ButtonText
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.component.getOutlinedRectangleModifier
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateSaveState
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.GenderViewModel
import com.fourleafclover.tarot.ui.theme.TextB02M16
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.TextH03SB18
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_3
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.highlightPurple
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getPickedTopic

const val WOMAN = 0
const val MAN = 1

@Preview
@Composable
fun RoomGenderScreen(
    navController: NavHostController = rememberNavController(),
    genderViewModel: GenderViewModel = GenderViewModel()
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
                .verticalScroll(rememberScrollState())
        ) {

            TextB02M16(text = "그 사람과의 운명을 확인해봐요.", color = gray_3)
            TextH02M22(text = "자신의 성별을 선택해주세요.", color = white)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                GenderButton(
                    modifier = Modifier.weight(1f),
                    genderViewModel = genderViewModel,
                    gender = WOMAN
                )

                GenderButton(
                    modifier = Modifier.weight(1f),
                    genderViewModel = genderViewModel,
                    gender = MAN
                )
            }

            ButtonNext(
                onClick = { navigateSaveState(navController, ScreenEnum.RoomNicknameScreen.name) },
                enabled = { genderViewModel.isCompleted() },
                content = { ButtonText(genderViewModel.isCompleted()) }
            )

        }

    }

}

@Composable
fun GenderButton(modifier: Modifier, genderViewModel: GenderViewModel, gender: Int) {
    Button(
        modifier = getOutlinedRectangleModifier(
            borderColor = gray_7,
            fillColor = gray_9,
            cornerRadius = 10.dp
        )
            .padding(vertical = 16.dp)
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            containerColor = gray_9,
            contentColor = highlightPurple,
            disabledContainerColor = gray_9,
            disabledContentColor = gray_2
        ),
        onClick = { genderViewModel.updatePickedGender(gender) },
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(
                        id =
                        if (genderViewModel.isSelected(gender)) {
                            if (gender == WOMAN) R.drawable.woman_selected
                            else R.drawable.man_selected
                        } else {
                            if (gender == WOMAN) R.drawable.woman_unselected
                            else R.drawable.man_unselected
                        }
                    ),
                    contentDescription = null,
                    alignment = Alignment.Center,
                )

                TextH03SB18(
                    text = if (gender == WOMAN) "여자" else "남자",
                    color = if (genderViewModel.isSelected(gender)) highlightPurple else gray_2,
                    modifier = Modifier.padding(top = 33.dp)
                )
            }

        }
    )
}