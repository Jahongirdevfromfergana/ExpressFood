package uz.fergana.fastexpress.screen.main.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fergana.fastexpress.models.sealed.DataResult
import uz.fergana.fastexpress.repository.UserRepository
import uz.fergana.fastexpress.utils.PrefUtils
import uz.fergana.fastexpress.models.ProductModel

class CartViewModel : ViewModel() {
    val repository = UserRepository()

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _foodListLiveData = MutableLiveData<List<ProductModel>?>()
    var foodListLiveData: LiveData<List<ProductModel>?> = _foodListLiveData

    fun getFoods() {
        _progressLiveData.value = true
        viewModelScope.launch {
            when (val result = repository.getFoods()) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    result.result?.forEach {
                        it.cartCount = PrefUtils.getCartCount(it.id)
                    }
                    _foodListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}