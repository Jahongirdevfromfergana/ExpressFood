package uz.fergana.fastexpress.screen.main.order.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.fastexpress.models.request.MakeOrderRequest
import uz.fergana.fastexpress.models.sealed.DataResult
import uz.fergana.fastexpress.repository.UserRepository

class CheckoutViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _successOrderLiveData = MutableLiveData<Boolean>()
    var successOrderLiveData: LiveData<Boolean> = _successOrderLiveData

    fun getMakeOrder(request: MakeOrderRequest) {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val result = repository.getMakeOrder(request)) {
                is DataResult.Error<*> -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success<*> -> {
                    _successOrderLiveData.value = true
                }
            }
            _progressLiveData.value = false
        }
    }
}