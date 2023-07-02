package uz.fergana.fastexpress.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.fastexpress.models.request.LoginRequest
import uz.fergana.fastexpress.models.response.LoginResponse
import uz.fergana.fastexpress.models.sealed.DataResult
import uz.fergana.fastexpress.repository.UserRepository

class SiginViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _siginLiveData = MutableLiveData<LoginResponse?>()
    var siginLiveData: LiveData<LoginResponse?> = _siginLiveData

    fun getSigin(request: LoginRequest) {
        _progressLiveData.value = true
        viewModelScope.launch {
            val result = repository.getSigin(request)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _siginLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}