package com.seweryn.cvapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class ActionHelper {
    companion object {
        fun makePhoneCall(context: Context, phoneNumber: String) {
            context.startActivity(
                Intent(Intent.ACTION_DIAL, Uri.parse("tel: $phoneNumber"))
            )
        }

        fun sendEmail(context: Context, emailAddress: String) {
            context.startActivity(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: $emailAddress"))
            )
        }
    }
}