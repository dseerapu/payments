package com.dharma.paymentsapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "payments")
@Parcelize
data class Payment(

    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val type: String,

    val amount: Double,

    val bankProvider : String? = null,

    val details: String? = null
) : Parcelable