package com.sahil.bitpandatest.model.currencies

data class Fiat(
    override var precision : Int = 2,
    override var name : String = "",
    override var symbol : String = "",
    override var logo : String = "",
    override var id : String = ""
) : Currency(precision, name, symbol, id, logo)