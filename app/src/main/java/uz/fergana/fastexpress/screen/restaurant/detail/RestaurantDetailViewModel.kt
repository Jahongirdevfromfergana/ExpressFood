package uz.fergana.fastexpress.screen.restaurant.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.fastexpress.models.ProductModel
import uz.fergana.fastexpress.models.RestaurantModel
import uz.fergana.fastexpress.models.sealed.DataResult
import uz.fergana.fastexpress.repository.UserRepository

class RestaurantDetailViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _successProductLiveData = MutableLiveData<List<ProductModel>>()
    var successProductLiveData : LiveData<List<ProductModel>> =_successProductLiveData

    private var _successDetailRestaurantLiveData = MutableLiveData<RestaurantModel>()
    var successDetailRestaurantLiveData : LiveData<RestaurantModel> =_successDetailRestaurantLiveData

    fun getProduct(request: Int) {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val result = repository.getProduct(request)) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _successProductLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }

    fun getDetailRestaurant(request: Int) {
        viewModelScope.launch {
            when (val result = repository.getDetailRestaurant(request)) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _successDetailRestaurantLiveData.value=(result.result)
                }
            }
        }
    }

}