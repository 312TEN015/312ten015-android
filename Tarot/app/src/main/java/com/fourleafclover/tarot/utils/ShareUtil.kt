package com.fourleafclover.tarot.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.harmonyShareViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.google.firebase.Firebase
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.androidParameters
import com.google.firebase.dynamiclinks.dynamicLink
import com.google.firebase.dynamiclinks.dynamicLinks
import com.google.firebase.dynamiclinks.iosParameters
import com.google.firebase.dynamiclinks.shortLinkAsync


enum class ShareLinkType {
    MY,     // 타로 결과 공유
    HARMONY // 방 코드 공유
}

enum class ShareActionType {
    OPEN_SHEET, // 공유 바텀 시트 열기
    COPY_LINK   // 링크 복사
}

private const val DYNAMIC_LINK_PREFIX = "https://fourleafclover.page.link"

// send ============================================================================================

// 1) 링크 타입에 따른 분기
fun setDynamicLink(
    context: Context,
    value: String,
    linkType: ShareLinkType,
    actionType: ShareActionType
){
    if (linkType == ShareLinkType.HARMONY) {
        if (harmonyShareViewModel.roomId.value.isNotEmpty()){
            if (harmonyShareViewModel.shortLink.isNotEmpty())
                doShare(context, actionType, harmonyShareViewModel.shortLink.toUri())
            else if (harmonyShareViewModel.dynamicLink.isNotEmpty())
                doShare(context, actionType, harmonyShareViewModel.dynamicLink.toUri())
            else if (harmonyShareViewModel.dynamicLink.isEmpty() && harmonyShareViewModel.shortLink.isEmpty())
                getDynamicLink(context, value, linkType, actionType)
            return
        }
    }

    getDynamicLink(context, value, linkType, actionType)
}

// 2) 다이나믹 링크 초기화
fun getDynamicLink(
    context: Context,
    value: String,
    linkType: ShareLinkType,
    actionType: ShareActionType
){
    val url = initLink(value, linkType)

    val dynamicLink = Firebase.dynamicLinks.dynamicLink {
        link = Uri.parse(url)
        domainUriPrefix = DYNAMIC_LINK_PREFIX
        androidParameters { }
        iosParameters("com.example.ios") { }
    }

    if (linkType == ShareLinkType.HARMONY)
        harmonyShareViewModel.shortLink = dynamicLink.uri.toString()

   val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync(ShortDynamicLink.Suffix.SHORT) {
        longLink = dynamicLink.uri
        link = Uri.parse(url)
        domainUriPrefix = DYNAMIC_LINK_PREFIX
        androidParameters { }
        iosParameters("com.example.ios") { }

   }
       .addOnSuccessListener { shortLink ->
            doShare(context, actionType, shortLink.shortLink)

            if (linkType == ShareLinkType.HARMONY)
                harmonyShareViewModel.shortLink = shortLink.shortLink.toString()

       }
       .addOnFailureListener {
            doShare(context, actionType, dynamicLink.uri)
       }
}

// 3) 공유하기 실행
fun doShare(
    context: Context,
    actionType: ShareActionType,
    link: Uri?
){
    when(actionType) {
        ShareActionType.OPEN_SHEET -> showShareSheet(context, link)

        ShareActionType.COPY_LINK ->  copyLink(context, link.toString())
    }
}

fun initLink(value: String, linkType: ShareLinkType): String {
    var url = "http://tarot-for-u.shop/share?"

    url += when (linkType) {
        ShareLinkType.MY -> {
            "tarotId=${value}"
        }
        ShareLinkType.HARMONY -> {
            "roomId=${value}"
        }
    }

    return url
}

// actions -----------------------------------------------------------------------------------------
fun showShareSheet(context: Context, link: Uri?){
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT,"${context.resources.getString(R.string.share_content)}\n\n$link")
    context.startActivity(Intent.createChooser(intent, null));
}

fun copyLink(context: Context, linkToCopy: String){
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("타로결과 공유하기", linkToCopy)
    clipboardManager.setPrimaryClip(clip)
}



// receive =========================================================================================
fun receiveShareRequest(activity: Activity, navController: NavHostController){
    Firebase.dynamicLinks
        .getDynamicLink(activity.intent)
        .addOnSuccessListener(activity) { pendingDynamicLinkData: PendingDynamicLinkData? ->
            // Get deep link from result (may be null if no link is found)
            if (pendingDynamicLinkData == null) return@addOnSuccessListener

            val deepLink = pendingDynamicLinkData.link ?: return@addOnSuccessListener

            val deepLinkUri = Uri.parse(deepLink.toString())

            // 타로 결과 공유
            if (deepLinkUri.getBooleanQueryParameter("tarotId", false)){
                val sharedTarotId = deepLinkUri.getQueryParameter("tarotId")!!
                getSharedTarotDetail(activity, navController, sharedTarotId)
                loadingViewModel.startLoading(navController, ScreenEnum.LoadingScreen, ScreenEnum.ShareDetailScreen)
            }

            // 궁합 초대
            if (deepLinkUri.getBooleanQueryParameter("roomId", false)){
                harmonyShareViewModel.roomId.value = deepLinkUri.getQueryParameter("roomId")!!
                MyApplication.connectSocket()
                if (MyApplication.socket.connected()){
                    navigateInclusive(navController, ScreenEnum.RoomGenderScreen.name)
                }else{
                    Toast.makeText(activity, "네트워크 상태를 확인 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }

            }

            activity.intent = null

        }
        .addOnFailureListener(activity) { e ->
            Log.w(
                "DynamicLink",
                "getDynamicLink:onFailure",
                e
            )
        }
}
