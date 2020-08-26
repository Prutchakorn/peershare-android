package com.example.prutc.peershare.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class BillSplit(
    @SerializedName("Id")var id: Int = 0,
    @SerializedName("OwnerPromptPay")var ownerPromptPay: String = "",
    @SerializedName("TotalPrice")var totalPrice: Double? = 0.0,
    @SerializedName("NumberOfPeople")var numberOfPeople: Int = 0,
//    @SerializedName("CreatedDateTime")var createdDateTime: Date = Date(),
    @SerializedName("IsActive")var isActive: Boolean = false,
    @SerializedName("Name") var name: String? = "",
    @SerializedName("Peers") var peers: List<Peer> = listOf(),
    @SerializedName("ServiceCharge") var serviceCharge: Int? = 0,
    @SerializedName("Vat") var vat: Double = 0.0
): Parcelable

@Parcelize
data class Peer(
    var Id: Long = 0,
    var BillSplitId: Long = 0,
    var Name: String = "",
    var IsPromptPay: Boolean = false,
    var PromptPay: String = "",
    var PersonalTotalPrice: Double = 0.0,
    var PersonalNetPrice: Double = 0.0,
    var StatusId: Long = 0,
//    var PaidDateTime: Date = Date(),
    var IsActive: Boolean = false,
    var UserAccount: Account? = Account(),
    var UserAccountId: Long? = 0,
    var Status: Status = Status()
): Parcelable

@Parcelize
data class Status(
    var ColorCode: String = "",
    var Description: String = "",
    var Id: Int = 0,
    var Name: String = ""
): Parcelable

@Parcelize
data class Account(
    @SerializedName("UserId") var id: Long = 0,
    @SerializedName("FirstName") var firstName: String = "",
    @SerializedName("LastName") var lastName: String = "",
    @SerializedName("Password") var password: String = "",
    @SerializedName("PromptPay") var promptPay: String = "",
//    @SerializedName("BirthDate") var birthDate: Date = Date(),
    @SerializedName("ProfileImageUrl") var profileImageUrl: String = ""
): Parcelable