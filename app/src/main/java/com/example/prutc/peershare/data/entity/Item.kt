package com.example.prutc.peershare.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    var name: String = "",
    var peers: List<Peer> = emptyList(),
    var price: Double = 0.0
): Parcelable

@Parcelize
data class PeerSample(
    var Name: String = "",
    var PromptPay: String = "",
    var PersonalTotalPrice: Double = 0.0
): Parcelable