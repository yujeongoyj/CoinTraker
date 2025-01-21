package com.project.cointraker.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.cointraker.repository.NetWorkRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class SelectViewModel: ViewModel() {
    private val netWorkRepository = NetWorkRepository()

    fun getCurrentCoinList() = viewModelScope.launch {
        val result = netWorkRepository.getCurrentCoinList()

        Timber.d(result.toString())
    }
}