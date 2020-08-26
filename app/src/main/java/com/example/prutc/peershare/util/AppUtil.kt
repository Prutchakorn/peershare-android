package com.example.prutc.peershare.util

import android.content.Context
import com.example.prutc.peershare.data.entity.Account
import com.example.prutc.peershare.extension.toBirthDate
import com.example.prutc.peershare.extension.toDate

fun saveImage(context: Context, path: String) {
    val editor = context.getSharedPreferences("image", Context.MODE_PRIVATE).edit()
    with(editor) {
        putString("path", path)
        apply()
    }
}

fun retrieveImage(context: Context): String? {
    val sharedPreference = context.getSharedPreferences("image", Context.MODE_PRIVATE)
    return sharedPreference.getString("path", "")
}

fun saveProfile(context: Context, account: Account) {
    with(context.getSharedPreferences("user", Context.MODE_PRIVATE).edit()) {
        //        val accountJson = account.apply {
//            this.birthDate = birthDate.toBirthDate().toDate()
//        }.toJson()
//        putString("user", accountJson)
        putLong("id", account.id)
        putString("firstName", account.firstName)
        putString("lastName", account.lastName)
        putString("password", account.password)
        putString("promptPay", account.promptPay)
//        putString("birthDate", account.birthDate.toBirthDate())
        putString("profileUrlImage", account.profileImageUrl)
        apply()
    }
}

fun retrieveProfile(context: Context): Account? {
    context.getSharedPreferences("user", Context.MODE_PRIVATE).apply {
        //        val accountJson = getString("user", "")
//        return accountJson?.let { it.toObject<Account>()} ?: { null } ()
        return Account(
            id = getLong("id", 0),
            firstName = getString("firstName", "")!!,
            lastName = getString("lastName", "")!!,
            password = getString("password", "")!!,
            promptPay = getString("promptPay", "")!!,
//            birthDate = getString("birthDate", "")!!.toDate(),
            profileImageUrl = getString("profileImageUrl", "")!!
        )
    }
}