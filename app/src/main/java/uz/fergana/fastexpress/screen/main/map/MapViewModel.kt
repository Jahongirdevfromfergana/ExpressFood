package uz.fergana.fastexpress.screen.main.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.fastexpress.models.RestaurantModel
import uz.fergana.fastexpress.models.sealed.DataResult
import uz.fergana.fastexpress.repository.UserRepository

class MapViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _restaurantListLiveData = MutableLiveData<List<RestaurantModel>?>()
    var restaurantListLiveData: LiveData<List<RestaurantModel>?> = _restaurantListLiveData


    fun getRestaurant() {
        _progressLiveData.value = true
        viewModelScope.launch {
            val result = repository.getTopRestaurants()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _restaurantListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}