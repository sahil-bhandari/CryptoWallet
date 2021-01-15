package com.sahil.bitpandatest.arch

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sahil.bitpandatest.di.AppModule.provideData
import com.sahil.bitpandatest.model.RecyclerViewDataModel
import com.sahil.bitpandatest.model.enums.SpinnerOption

open class WalletViewModel  @ViewModelInject constructor(/*private val repository: Repository, @Assisted private val savedStateHandle: SavedStateHandle*/) : ViewModel(), LifecycleObserver {
    private val repository = provideData()

    private val dummyData = MutableLiveData<List<RecyclerViewDataModel>>()
    val dummyLiveData get() = dummyData

    fun fetchData() {
        dummyData.value = repository.getDummyData()
    }

    fun fetchSearchedData(name:String) {
        dummyData.value = if (repository.findByName(name).isNotEmpty()) repository.findByName(name) else repository.findBySymbol(name)
    }

    fun fetchFilteredData(spinnerOption: SpinnerOption) {
        when (spinnerOption) {
            SpinnerOption.ALL -> fetchData()
            SpinnerOption.FIATS -> dummyData.value = repository.getFiatWalletData()
            SpinnerOption.METALS -> dummyData.value = repository.getMetalWalletData()
            SpinnerOption.CRYPTOCOINS -> dummyData.value = repository.getCryptocoinWalletData()
        }
    }
}