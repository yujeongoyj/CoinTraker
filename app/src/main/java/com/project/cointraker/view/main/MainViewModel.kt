package com.project.cointraker.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.project.cointraker.dataModel.UpDownDataSet
import com.project.cointraker.db.entity.InterestCoinEntity
import com.project.cointraker.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val dbRepository = DBRepository()

    lateinit var selectedCoinList: LiveData<List<InterestCoinEntity>>

    private val _arr15min = MutableLiveData<List<UpDownDataSet>>()
    val arr15min: LiveData<List<UpDownDataSet>>
        get() = _arr15min

    private val _arr30min = MutableLiveData<List<UpDownDataSet>>()
    val arr30min: LiveData<List<UpDownDataSet>>
        get() = _arr30min

    private val _arr45min = MutableLiveData<List<UpDownDataSet>>()
    val arr45min: LiveData<List<UpDownDataSet>>
        get() = _arr45min

    // CoinListFragment

    fun getAllInterestCoinData() = viewModelScope.launch {

        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList

    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            // true일 때 (selected 일때 )
            if (interestCoinEntity.selected) {
                interestCoinEntity.selected = false
            } else {
                interestCoinEntity.selected = true
            }

            dbRepository.updateInterestCoinData(interestCoinEntity)
        }


    // PriceCoinFragment
    // 1. 선택한 코인 리스트를 가져와서
    // 2. 반복문으로 하나씩 가져오기
    // 3. 저장된 코인 가격 리스트를 가져와서
    // 4. 시간대마다 어떻게 변경되었는지를 알려주는 로직
    fun getAllSeltectedCoinData() = viewModelScope.launch(Dispatchers.IO) {

        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val arr15min = ArrayList<UpDownDataSet>()
        val arr30min = ArrayList<UpDownDataSet>()
        val arr45min = ArrayList<UpDownDataSet>()

        for (data in selectedCoinList) {

            val coinName = data.coin_name

            // 코인은 시간별로 [0,1,2,3]식으로 저장이 되고 가장 마지막에 저장된 값이 최신값
            val oneCoinData = dbRepository.getOneSelectedCoinData(coinName).reversed()

            val size = oneCoinData.size
            if (size > 1) { // 현재와 15분전 가격을 비교하기 위해 2개 이상은 있어야 함
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr15min.add(upDownDataSet)

            }
            if (size > 2) { // 현재와 30분전 가격을 비교하기 위해
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr30min.add(upDownDataSet)


            }
            if (size > 3) {
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble()
                val upDownDataSet = UpDownDataSet(
                    coinName,
                    changedPrice.toString()
                )
                arr45min.add(upDownDataSet)
            }
        }

        withContext(Dispatchers.Main) {
            _arr15min.value = arr15min
            _arr30min.value = arr30min
            _arr45min.value = arr45min
        }


    }

}