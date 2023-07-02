package uz.fergana.fastexpress.screen.restaurant.detail.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.fastexpress.models.AllRestaurant
import uz.fergana.fastexpress.models.RestaurantModel
import uz.fergana.fastexpress.models.request.MakeRatingRequest
import uz.fergana.fastexpress.models.sealed.DataResult
import uz.fergana.fastexpress.repository.UserRepository

class FeedbackViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _successOrderLiveData = MutableLiveData<Boolean>()
    var successOrderLiveData : LiveData<Boolean> =_successOrderLiveData

    private var _successRestaurantsLiveData = MutableLiveData<List<RestaurantModel>>()
    var successRestaurantsLiveData : LiveData<List<RestaurantModel>> =_successRestaurantsLiveData

    fun getMakeRating(request: MakeRatingRequest) {
        viewModelScope.launch {
            when (val result = repository.getMakeRating(request)) {
                is DataResult.Error<*> -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success<*> -> {
                    _successOrderLiveData.value = true
                }
            }
        }
    }

    fun getAllRestaurant() {
        viewModelScope.launch {
            when (val result = repository.getAllRestaurant(AllRestaurant())) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _successRestaurantsLiveData.value=(result.result)
                }
            }
        }
    }

}