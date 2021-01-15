package com.sahil.bitpandatest.model.currencies

open class Currency(
    open val precision: Int,
    open val name: String,
    open val symbol: String,
    open val id: String,
    open val logo: String
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Currency)
            return false

        return this.id == other.id &&
                this.name == other.name &&
                this.symbol == other.symbol &&
                this.logo == other.logo &&
                this.precision == other.precision
    }
}
