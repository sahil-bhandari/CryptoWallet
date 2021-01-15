package com.sahil.bitpandatest.arch.repository

import com.sahil.bitpandatest.model.RecyclerViewDataModel
import com.sahil.bitpandatest.model.currencies.Cryptocoin
import com.sahil.bitpandatest.model.currencies.Fiat
import com.sahil.bitpandatest.model.currencies.Metal
import com.sahil.bitpandatest.model.wallets.CryptocoinWallet
import com.sahil.bitpandatest.model.wallets.FiatWallet
import com.sahil.bitpandatest.model.wallets.MetalWallet
import com.sahil.bitpandatest.model.wallets.Wallet
import com.sahil.bitpandatest.remote.DummyWebService
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor (private val webservice: DummyWebService) {

    fun findBySymbol(symbol: String): List<RecyclerViewDataModel> {
        val dummyDataList = getDummyData()
        return dummyDataList.filter {
            it.currency.symbol.toLowerCase(Locale.getDefault()).contains(symbol.toLowerCase(Locale.getDefault()))
        }
    }

    fun findByName(name: String): List<RecyclerViewDataModel> {
        val dummyDataList = getDummyData()
        return dummyDataList.filter {
            it.currency.name.toLowerCase(Locale.getDefault()).contains(name.toLowerCase(Locale.getDefault()))
        }
    }

    fun getDummyData(): List<RecyclerViewDataModel> {
        val fiatWallets = getFiatWalletData()
        val cryptocoinWallets = getCryptocoinWalletData()
        val metalWallets = getMetalWalletData()
        val wallets = mutableListOf<RecyclerViewDataModel>()

        return wallets.apply {
            addAll(fiatWallets)
            addAll(cryptocoinWallets)
            addAll(metalWallets)
        }
    }

    fun getFiatWalletData(): List<RecyclerViewDataModel> {
        var fiatWallets = webservice.getFiatWallets().filter { !it.deleted }
        fiatWallets = sortWallet(fiatWallets)

        return fiatWallets.map { wallet ->
            RecyclerViewDataModel(wallet, currency = getFiatCurrency(wallet))
        }
    }

    fun getMetalWalletData(): List<RecyclerViewDataModel> {
        var metalWallets = webservice.getMetalWallets().filter { !it.deleted }
        metalWallets = sortWallet(metalWallets)

        return metalWallets.map { wallet ->
            RecyclerViewDataModel(wallet, currency = getMetalCurrency(wallet))
        }
    }

    fun getCryptocoinWalletData(): List<RecyclerViewDataModel> {
        var cryptocoinWallets = webservice.getCryptoWallets().filter { !it.deleted }
        cryptocoinWallets = sortWallet(cryptocoinWallets)

        return cryptocoinWallets.map { wallet ->
            RecyclerViewDataModel(wallet, currency = getCryptocoinCurrency(wallet))
        }
    }

    private fun getFiatCurrency(wallet: FiatWallet): Fiat {
        val fiats = webservice.getFiats()
        return fiats.first { it.id == wallet.fiatId }
    }

    private fun getMetalCurrency(wallet: MetalWallet): Metal {
        val metals = webservice.getMetals()
        return metals.first { it.id == wallet.metalId }
    }

    private fun getCryptocoinCurrency(wallet: CryptocoinWallet): Cryptocoin {
        val cryptocoins = webservice.getCryptocoins()
        return cryptocoins.first { it.id == wallet.cryptocoinId }
    }

    private fun <T : Wallet> sortWallet(wallets: List<T>): List<T> {
        val sortedWallets = wallets.toMutableList()

        for(i in sortedWallets.indices) {
            for(j in i + 1 until sortedWallets.size) {
                val iWallet = sortedWallets[i]
                val jWallet = sortedWallets[j]
                val isNotSorted: Boolean = iWallet.currencyId == jWallet.currencyId && iWallet.balance > jWallet.balance

                if (isNotSorted) {
                    for (k in j downTo i + 1)
                        sortedWallets[k] = sortedWallets[k - 1]
                    sortedWallets[i] = jWallet
                }
            }
        }
        return sortedWallets
    }

}