package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.SubjectHarmony
import com.fourleafclover.tarot.ui.component.AppBarCloseCreateChatWithDialog
import com.fourleafclover.tarot.ui.component.AppBarCloseWithDialog
import com.fourleafclover.tarot.ui.component.ButtonNext
import com.fourleafclover.tarot.ui.component.ButtonText
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.component.getOutlinedRectangleModifier
import com.fourleafclover.tarot.ui.navigation.PreventBackPressed
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
import com.fourleafclover.tarot.ui.theme.purple400
import com.fourleafclover.tarot.ui.theme.white

const val WOMAN = 0
const val MAN = 1

@Preview
@Composable
fun RoomGenderScreen(
    navController: NavHostController = rememberNavController(),
    genderViewModel: GenderViewModel = GenderViewModel()
) {
    PreventBackPressed()

    Column(modifier = getBackgroundModifier(backgroundColor_2)) {
        AppBarCloseCreateChatWithDialog(
            navController = navController,
            pickedTopicTemplate = SubjectHarmony,
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

    Box(
        modifier = getOutlinedRectangleModifier(
            borderColor = if (genderViewModel.isSelected(gender)) {
                purple400
            } else {
                gray_7
            },
            fillColor = gray_9,
            cornerRadius = 10.dp
        )
            .padding(vertical = 16.dp)
            .clickable {
                genderViewModel.updatePickedGender(gender)
            }
            .then(modifier),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
}