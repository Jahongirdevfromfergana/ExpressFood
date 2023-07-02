package uz.fergana.fastexpress.screen.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.fastexpress.models.ProfileModel
import uz.fergana.fastexpress.models.sealed.DataResult
import uz.fergana.fastexpress.repository.UserRepository

class ProfileViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _profileLiveData = MutableLiveData<ProfileModel?>()
    var profileLiveData: LiveData<ProfileModel?> = _profileLiveData


    fun getProfile() {
        _progressLiveData.value = true
        viewModelScope.launch {
            val result = repository.getProfile()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _profileLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}