package com.project.cointraker.view.intro
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.cointraker.dataStore.MyDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class IntroViewModel : ViewModel() {

    // MutableLiveData<Boolean>()은 값을 변경할 수 있는 변수
    private val _first = MutableLiveData<Boolean>()
    // LiveData<Boolean>타입은 읽기 전용 프로퍼티
    // 외부로부터 데이터를 캡슐화하여 외부에서 값을 변경할 수 없음
    val first : LiveData<Boolean>
        get() = _first

    fun checkFirstFlag() = viewModelScope.launch {

        delay(2000)

        val getData = MyDataStore().getFirstData()

        _first.value = getData
        Timber.d(getData.toString())
    }
}