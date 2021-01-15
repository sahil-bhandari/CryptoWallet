package com.sahil.bitpandatest.secondtest

import android.annotation.SuppressLint
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

object ContactsComponent {
    /**
     * in the live code this is set after the view loaded; you can assume that this won't be null or empty
     * you can mock or change this if you want
     */
    private var contacts: List<Contact> = listOf(
        Contact("1", "A"),
        Contact("1", "A"),
        Contact("2", "B"),
        Contact("2", "A"),
        Contact("3", "B"),
        Contact("3", "C"),
        Contact("3", "C")
    )

    /**
     * todo : returned list must
     *  1. hold only unique entries (data NOT id)
     *  2. hold max three entries
     *  3. sorted by "last_used" (if you use a custom sort, i'd suggest to use the unix timestamp)
     */
    private fun getRecentContacts(): List<Contact> {
        return contacts.distinctBy { it.data }.sortedWith(sort).take(3)
    }

    /**
     * Sort wrt unix timestamp using comparator
     */
    private val sort = Comparator<Contact> { c1, c2 ->
        when {
            c1.lastUsed.unix > c2.lastUsed.unix -> 1
            c1.lastUsed.unix == c2.lastUsed.unix -> 0
            else -> -1
        }
    }

    /**
     * Format timestamp to date
     */
    @SuppressLint("SimpleDateFormat")
    private fun getStandardTimestamp(timestamp: Long, dateIso8601: String) : String {
        val sdf = SimpleDateFormat(dateIso8601)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = Date(timestamp)
        return sdf.format(date)
    }

    /**
    * fetch sorted recent contacts and display
    */
    fun getFinalResult() {
        val result = getRecentContacts()
        println("#######--Start--#######")
        result.forEach {
            println(StringBuilder().append(it.id).append(" ").append(it.data).append(" ").append( getStandardTimestamp(it.lastUsed.unix,it.lastUsed.dateISO8601)))
        }
        println("#######--Finish--#######")
    }
}