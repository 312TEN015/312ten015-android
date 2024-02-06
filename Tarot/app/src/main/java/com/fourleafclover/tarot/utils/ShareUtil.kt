package com.fourleafclover.tarot.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.fourleafclover.tarot.R
import com.google.firebase.Firebase
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.androidParameters
import com.google.firebase.dynamiclinks.dynamicLink
import com.google.firebase.dynamiclinks.dynamicLinks
import com.google.firebase.dynamiclinks.iosParameters
import com.google.firebase.dynamiclinks.shortLinkAsync


fun showShareSheet(context: Context, link: Uri?){
    val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT,"${context.resources.getString(R.string.share_content)}\n\n$link")
    context.startActivity(Intent.createChooser(intent, context.resources.getString(R.string.share_title)))
}



fun setDynamicLink(context: Context, tarotId: String){
    val dynamicLink = Firebase.dynamicLinks.dynamicLink {
        link = Uri.parse("http://tarot-for-u.shop/share?tarotId=${tarotId}")
        domainUriPrefix = "https://fourleafclover.page.link"
        // Open links with this app on Android
        androidParameters { }
        // Open links with com.example.ios on iOS
        iosParameters("com.example.ios") { }
    }

    val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync(ShortDynamicLink.Suffix.SHORT) {
        longLink = dynamicLink.uri
        link = Uri.parse("http://tarot-for-u.shop/share" + "?tarotId=${tarotId}")
        domainUriPrefix = "https://fourleafclover.page.link"
        // Open links with this app on Android
        androidParameters { }
        // Open links with com.example.ios on iOS
        iosParameters("com.example.ios") { }
    }.addOnSuccessListener { shortLink ->
        // shortlink 만들기 성공한 경우 shortlink 사용
        showShareSheet(context, shortLink.shortLink)

    }.addOnFailureListener {
        // 실패한 경우 긴 링크 사용
        showShareSheet(context, dynamicLink.uri)
    }
}
