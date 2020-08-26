package com.example.prutc.peershare.util

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import com.example.prutc.peershare.data.entity.Peer

fun getPeer(context: Context, uri: Uri): Peer {
    val contentCursor = context.contentResolver?.query(uri, null, null, null, null)
    contentCursor?.let {
        if (it.moveToFirst()) {
            val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
            val hasPhone = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            if (hasPhone.equals("1", ignoreCase = true)) {
                val phones = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                    null,
                    null
                )
                phones!!.moveToFirst()
                val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                return Peer(Name = name, PromptPay = phoneNumber)
            }
        }
    }
    return Peer()
}