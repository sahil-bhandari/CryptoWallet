package com.sahil.bitpandatest.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sahil.bitpandatest.R
import com.sahil.bitpandatest.arch.WalletAdapter
import com.sahil.bitpandatest.arch.WalletViewModel
import com.sahil.bitpandatest.model.RecyclerViewDataModel
import com.sahil.bitpandatest.model.bundles.CurrencyInfo
import com.sahil.bitpandatest.model.currencies.CurrencyWithPrice
import com.sahil.bitpandatest.model.currencies.Fiat
import com.sahil.bitpandatest.model.enums.SpinnerOption
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {

    private val viewModel: WalletViewModel by viewModels()
//    private val viewModel = ViewModelProvider(this,WalletFactory())[WalletViewModel::class.java]
    lateinit var adapter: WalletAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadWalletSpinner()
        loadWalletList()
        loadToolbar()
        viewModel.dummyLiveData.observeForever(adapter::submitList)
        viewModel.fetchData()
        adapter.notifyDataSetChanged()
    }

    private fun loadWalletSpinner() {
        val selectionList = arrayOf("All", "Cryptocoins", "Fiats", "Metals")
//        selectionList.clear()
//        selectionList.add("All")
//        SpinnerOption.values().forEach { selectionList.add(it.toString()) }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, selectionList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        walletSelectionSP.adapter = adapter
        walletSelectionSP.onItemSelectedListener = this
    }

    private fun loadWalletList() {
        adapter = WalletAdapter(::showWalletList)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        walletRV.layoutManager = llm
        walletRV.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        walletRV.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun loadToolbar() {
        toolbar.apply {
            title="Welcome"
        }
        setSupportActionBar(toolbar)
    }

//    val filteredWalletList = wallets.filter {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    fun sortWalletList(wallets: List<IMPLEMENT_ME>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    fun showWalletList(wallets: List<IMPLEMENT_ME>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    private fun showWalletList(recyclerViewDataModel: RecyclerViewDataModel) {
        val walletData=recyclerViewDataModel.wallet
        val currencyData=recyclerViewDataModel.currency
        var currencyInfo=CurrencyInfo(
                walletData.name,
                currencyData.name,
                walletData.balance,
                -1.0,
                currencyData.logo,
                currencyData.symbol,
                currencyData.precision,
                0.0
        )
        if (recyclerViewDataModel.currency !is Fiat){
            currencyInfo.unit= (currencyData as CurrencyWithPrice).price
        }
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("key", currencyInfo)
        startActivity(intent)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedItem: String = parent?.getItemAtPosition(position) as String
        viewModel.fetchFilteredData(SpinnerOption.getOption(selectedItem))
        adapter.notifyDataSetChanged()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("spinner", "Nothing here...")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { viewModel.fetchSearchedData(it) }
        adapter.notifyDataSetChanged()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert!!")
                .setMessage("Do you want to leave this app?")
                .setPositiveButton(android.R.string.ok) { _, _ -> super.onBackPressed() }
                .setNegativeButton(android.R.string.cancel){ _, _ -> }
                .show()
    }
}