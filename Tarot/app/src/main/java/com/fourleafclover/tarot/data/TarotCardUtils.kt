package com.fourleafclover.tarot.data

import android.content.Context
import android.content.res.Resources




fun getCardImageId(localContext: Context, cardNumber: String): Int {
    val resources: Resources = localContext.resources
    return resources.getIdentifier("tarot_$cardNumber", "drawable", localContext.packageName)
}