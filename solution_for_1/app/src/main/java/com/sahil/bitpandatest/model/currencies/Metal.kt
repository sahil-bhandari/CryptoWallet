package com.sahil.bitpandatest.model.currencies

data class Metal(
    override var precision: Int = 3,
    override var name: String = "",
    override var symbol: String = "",
    override var id: String = "",
    override var logo: String = "",
    override var price: Double = 0.0
) : CurrencyWithPrice(precision, name, symbol, id, logo, price)