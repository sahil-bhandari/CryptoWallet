package com.sahil.bitpandatest.model.bundles

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrencyInfo(
    val name: String = "",
    val currency: String = "",
    val balance: Double = 0.0,
    var unit: Double = -1.0,
    val logo: String = "",
    val symbol: String = "",
    val precision: Int = 0,
    val total: Double = 0.0
) : Parcelable
