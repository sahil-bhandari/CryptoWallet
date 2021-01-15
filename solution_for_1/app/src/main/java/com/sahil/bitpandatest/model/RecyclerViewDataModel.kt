package com.sahil.bitpandatest.model

import com.sahil.bitpandatest.model.currencies.Currency
import com.sahil.bitpandatest.model.wallets.Wallet
import javax.inject.Inject

data class RecyclerViewDataModel @Inject constructor(val wallet: Wallet, val currency: Currency){

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is RecyclerViewDataModel)
            return false

        return this.wallet == other.wallet && this.currency == other.currency
    }

    override fun hashCode(): Int {
        var result = wallet.hashCode()
        result = 31 * result + currency.hashCode()
        return result
    }
}