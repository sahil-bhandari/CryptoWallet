package com.sahil.bitpandatest.model.wallets

data class FiatWallet(
    override val id: String = "",
    override val name: String = "",
    override var balance: Double = 0.0,
    override val isDefault: Boolean = false,
    override val deleted: Boolean = false,
    val fiatId: String = ""
) : Wallet(id, name, balance, isDefault, deleted, fiatId)