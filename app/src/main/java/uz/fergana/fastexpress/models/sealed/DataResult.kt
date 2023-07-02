package uz.fergana.fastexpress.models.sealed

sealed class DataResult<T> {
    data class Success<T>(val result: T) : DataResult<T>()
    data class Error<T>(val message: String) : DataResult<T>()
}