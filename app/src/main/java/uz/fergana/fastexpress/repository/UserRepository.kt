package uz.fergana.fastexpress.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.fergana.fastexpress.base.BaseRepository
import uz.fergana.fastexpress.models.response.LoginResponse
import uz.fergana.fastexpress.models.response.RegistrationResponse
import uz.fergana.fastexpress.models.sealed.DataResult
import uz.fergana.fastexpress.networking.NetworkingObject
import uz.fergana.fastexpress.utils.PrefUtils
import uz.fergana.fastexpress.models.AllRestaurant
import uz.fergana.fastexpress.models.CategoryModel
import uz.fergana.fastexpress.models.OfferModel
import uz.fergana.fastexpress.models.ProductModel
import uz.fergana.fastexpress.models.ProfileModel
import uz.fergana.fastexpress.models.RestaurantFilter
import uz.fergana.fastexpress.models.RestaurantModel
import uz.fergana.fastexpress.models.TopRestaurantFilter
import uz.fergana.fastexpress.models.request.FoodsByIdsRequest
import uz.fergana.fastexpress.models.request.LoginRequest
import uz.fergana.fastexpress.models.request.MakeOrderRequest
import uz.fergana.fastexpress.models.request.MakeRatingRequest
import uz.fergana.fastexpress.models.request.RegistrationRequest

class UserRepository : BaseRepository() {
    val api = NetworkingObject.getClientInstance()

    suspend fun getOffers() = withContext(Dispatchers.IO) {
        try {
            val response = api.getOffers()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<OfferModel>>(e.localizedMessage)
        }
    }

    suspend fun getCategory() = withContext(Dispatchers.IO) {
        try {
            val response = api.getCategories()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<CategoryModel>>(e.localizedMessage)
        }
    }

    suspend fun getRestaurants() = withContext(Dispatchers.IO) {
        try {
            val response = api.getRestaurants(RestaurantFilter())
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<RestaurantModel>>(e.localizedMessage)
        }
    }

    suspend fun getTopRestaurants() = withContext(Dispatchers.IO) {
        try {
            val response = api.getTopRestaurants(TopRestaurantFilter())
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<RestaurantModel>>(e.localizedMessage)
        }
    }

    suspend fun getMakeRating(request: MakeRatingRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.getMakeRating(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext e.localizedMessage
        }
    }

    suspend fun getAllRestaurant(request: AllRestaurant) = withContext(Dispatchers.IO) {
        try {
            val response = api.getAllRestaurants(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<RestaurantModel>>(e.localizedMessage)
        }
    }

    suspend fun getProduct(request: Int) = withContext(Dispatchers.IO) {
        try {
            val response = api.getProduct(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<ProductModel>>(e.localizedMessage)
        }
    }

    suspend fun getDetailRestaurant(request: Int) = withContext(Dispatchers.IO) {
        try {
            val response = api.getDetailRestaurants(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<RestaurantModel>(e.localizedMessage)
        }
    }

    suspend fun getFoods() = withContext(Dispatchers.IO) {
        try {
            val response =
                api.getFoodsByIds(FoodsByIdsRequest(PrefUtils.getCartList().map { it.id }))
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<List<ProductModel>>(e.localizedMessage)
        }
    }

    suspend fun getMakeOrder(request: MakeOrderRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.getMakeOrder(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext e.localizedMessage
        }
    }

    suspend fun getRegistration(request: RegistrationRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.registration(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<RegistrationResponse>(e.localizedMessage)
        }
    }

    suspend fun getSigin(request: LoginRequest) = withContext(Dispatchers.IO) {
        try {
            val response = api.login(request)
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<LoginResponse>(e.localizedMessage)
        }
    }

    suspend fun getProfile() = withContext(Dispatchers.IO) {
        try {
            val response = api.getProfile()
            return@withContext wrapResponse(response)
        } catch (e: Exception) {
            return@withContext DataResult.Error<ProfileModel>(e.localizedMessage)
        }
    }
}