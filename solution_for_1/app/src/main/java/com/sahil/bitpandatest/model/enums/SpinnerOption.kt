package com.sahil.bitpandatest.model.enums

enum class SpinnerOption (val value: String) {
    ALL("All"),
    CRYPTOCOINS("Cryptocoins"),
    FIATS("Fiats"),
    METALS("Metals");

    companion object {
        fun getOption(value: String): SpinnerOption =
            values().first { it.value == value }
    }

}