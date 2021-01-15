package com.sahil.bitpandatest.model.currencies

open class CurrencyWithPrice(
    override val precision: Int,
    override val name: String,
    override val symbol: String,
    override val id: String,
    override val logo: String,
    open val price: Double,
) : Currency(precision, name, symbol, id, logo)