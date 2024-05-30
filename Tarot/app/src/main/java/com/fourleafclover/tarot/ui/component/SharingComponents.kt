package com.fourleafclover.tarot.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.ui.theme.TextB02M16
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.gray_2
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.gray_9

@Composable
@Preview
fun ShareLinkOrCopy(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        HowToShareButton(modifier = Modifier.weight(1f), iconResource = R.drawable.share_g2, text = "초대 링크 공유")
        HowToShareButton(modifier = Modifier.weight(1f), iconResource = R.drawable.unlink_g2, text = "초대 링크 복사")
    }
}

@Composable
fun HowToShareButton(
    modifier: Modifier = Modifier,
    iconResource: Int = R.drawable.share,
    text: String = "초대 링크 공유"
) {
    Box(
        modifier = getOutlinedRectangleModifier(
            borderColor = gray_7,
            fillColor = gray_9,
            cornerRadius = 10.dp
        )
            .padding(vertical = 15.dp)
            .clickable {  }
            .then(modifier),
        contentAlignment = Alignment.Center
    ){
        Row(horizontalArrangement = Arrangement.Center) {
            Image(
                modifier = Modifier.padding(end = 4.dp).size(20.dp),
                painter = painterResource(id = iconResource),
                contentDescription = null
            )
            TextB02M16(text = text, color = gray_2)
        }
    }
}