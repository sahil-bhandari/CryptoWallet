package com.sahil.bitpandatest.arch

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.sahil.bitpandatest.R
import com.sahil.bitpandatest.model.RecyclerViewDataModel
import com.sahil.bitpandatest.model.currencies.Metal
import java.text.NumberFormat
import java.util.*

class WalletAdapter(private val callback: (RecyclerViewDataModel) -> Unit) : ListAdapter<RecyclerViewDataModel, WalletAdapter.WalletViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecyclerViewDataModel>() {
            override fun areItemsTheSame(oldItem: RecyclerViewDataModel, newItem: RecyclerViewDataModel): Boolean {
                return oldItem.wallet.id == newItem.wallet.id
            }

            override fun areContentsTheSame(oldItem: RecyclerViewDataModel, newItem: RecyclerViewDataModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet, parent, false)
        return WalletViewHolder(view)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val recyclerViewDataModel = getItem(position)
        val (wallet,currency) = recyclerViewDataModel
        holder.titleTV.text = wallet.name
        holder.balanceTV.text = getBalance(recyclerViewDataModel)
        holder.itemView.setOnClickListener {
            callback.invoke(recyclerViewDataModel)
        }
        GlideToVectorYou.init()
            .with(holder.logoIV.context)
            .load(Uri.parse(currency.logo), holder.logoIV)
    }

    private fun getBalance(recyclerViewDataModel: RecyclerViewDataModel): String {
        val (wallet, currency) = recyclerViewDataModel
        val balance = StringBuilder()
            .append(NumberFormat.getNumberInstance(Locale.ENGLISH).format(wallet.balance))
            .append(" ")
            .append(currency.symbol)

        if (currency is Metal)
            balance.append(" (${currency.name})")

        return balance.toString()
    }

    class WalletViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTV: TextView = view.findViewById(R.id.titleTV)
        val logoIV: ImageView = view.findViewById(R.id.logoIV)
        val balanceTV: TextView = view.findViewById(R.id.balanceTV)
    }

}
