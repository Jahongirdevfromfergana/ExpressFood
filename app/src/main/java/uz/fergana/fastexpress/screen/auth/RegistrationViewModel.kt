package uz.fergana.fastexpress.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.fastexpress.models.request.RegistrationRequest
import uz.fergana.fastexpress.models.response.RegistrationResponse
import uz.fergana.fastexpress.models.sealed.DataResult
import uz.fergana.fastexpress.repository.UserRepository

class RegistrationViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _registrationLiveData = MutableLiveData<RegistrationResponse?>()
    var registrationLiveData: LiveData<RegistrationResponse?> = _registrationLiveData

    fun getRegistration(request: RegistrationRequest) {
        _progressLiveData.value = true
        viewModelScope.launch {
            val result = repository.getRegistration(request)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _registrationLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}