package com.sahil.bitpandatest.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.sahil.bitpandatest.R
import com.sahil.bitpandatest.arch.WalletViewModel
import com.sahil.bitpandatest.model.bundles.CurrencyInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

//    private val viewModel: WalletViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        loadIntentData()
        loadToolbar()
    }

    private fun loadIntentData() {
        val bundle= intent.extras
        bundle.let {
            bundle?.apply {
                //Parcelable Data
                val currencyInfo: CurrencyInfo? = getParcelable("key")
                if (currencyInfo != null) {
                    nameTV.text=currencyInfo.name
                    balanceTV.text = StringBuilder()
                        .append(NumberFormat.getNumberInstance(Locale.ENGLISH).format(currencyInfo.balance))
                        .append(" ")
                        .append(currencyInfo.symbol)
                        .toString()
                    currencyTV.text = currencyInfo.currency
                    GlideToVectorYou.init()
                        .with(this@DetailActivity)
                        .load(Uri.parse(currencyInfo.logo), logoIV)

                    if (currencyInfo.unit>0.0){
                        priceLL.visibility=View.VISIBLE
                        totalLL.visibility=View.VISIBLE

                        priceTV.text = StringBuilder()
                            .append(NumberFormat.getNumberInstance(Locale.ENGLISH).format(currencyInfo.unit))
                            .append(" ")
                            .append(resources.getString(R.string.eur))
                            .toString()

                        totalTV.text = StringBuilder()
                            .append(NumberFormat.getNumberInstance(Locale.ENGLISH).format(String.format("%.${currencyInfo.precision}f", currencyInfo.balance*currencyInfo.unit).toDouble()))
                            .append(" ")
                            .append(resources.getString(R.string.eur))
                            .toString()
                    }
                }
            }
        }
    }

    private fun loadToolbar() {
        toolbar.apply {
            title="Wallet Details"
            setNavigationIcon(R.drawable.ic_arrow_back)
        }
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
    }

}