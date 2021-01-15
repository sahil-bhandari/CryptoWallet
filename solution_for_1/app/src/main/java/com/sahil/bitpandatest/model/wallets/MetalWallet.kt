package com.sahil.bitpandatest.model.wallets

data class MetalWallet(
    override val id: String = "",
    override val name: String = "",
    override var balance: Double = 0.0,
    override val isDefault: Boolean = false,
    override val deleted: Boolean = false,
    val metalId: String = "", ) : Wallet(id, name, balance, isDefault, deleted, metalId)