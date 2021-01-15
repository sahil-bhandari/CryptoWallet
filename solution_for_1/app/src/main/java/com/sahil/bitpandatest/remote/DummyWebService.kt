package com.sahil.bitpandatest.remote

import com.sahil.bitpandatest.model.currencies.Cryptocoin
import com.sahil.bitpandatest.model.currencies.Fiat
import com.sahil.bitpandatest.model.currencies.Metal
import com.sahil.bitpandatest.model.wallets.CryptocoinWallet
import com.sahil.bitpandatest.model.wallets.FiatWallet
import com.sahil.bitpandatest.model.wallets.MetalWallet
import javax.inject.Inject

class DummyWebService @Inject constructor() {

    fun getCryptoWallets(): List<CryptocoinWallet> {
        return DummyData.dummyCryptoWalletList
    }

    fun getMetalWallets(): List<MetalWallet> {
        return DummyData.dummyMetalWalletList
    }

    fun getFiatWallets(): List<FiatWallet> {
        return DummyData.dummyEURWallet
    }

    fun getCryptocoins(): List<Cryptocoin> {
        return DummyData.cryptocoins
    }

    fun getMetals(): List<Metal> {
        return DummyData.metals
    }

    fun getFiats(): List<Fiat> {
        return DummyData.fiats
    }

//    fun getCurrencies(): List<Currency> {
//        val currencies = mutableListOf<Currency>()
//        currencies.apply {
//            addAll(DummyData.cryptocoins)
//            addAll(DummyData.metals)
//            addAll(DummyData.fiats)
//        }
//        return currencies
//    }

}