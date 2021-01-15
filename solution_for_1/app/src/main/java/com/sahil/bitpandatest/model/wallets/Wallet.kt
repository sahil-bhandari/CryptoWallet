package com.sahil.bitpandatest.model.wallets

 open class Wallet(
    open val id: String,
    open val name: String,
    open var balance: Double,
    open val isDefault: Boolean,
    open val deleted: Boolean,
    val currencyId: String) {
    //todo implement me
    fun reduceBalance(amount: Double) {
        var newBalance = balance - amount

        if (newBalance < 0)
            newBalance = 0.0

        this.balance = newBalance
    }

    //todo implement me
    fun addBalance(amount: Double) {
        this.balance += amount
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Wallet)
            return false

        return this.id == other.id &&
                this.name == other.name &&
                this.balance == other.balance &&
                this.isDefault == other.isDefault &&
                this.deleted == other.deleted
    }
}
