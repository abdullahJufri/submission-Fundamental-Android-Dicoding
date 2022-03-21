package com.bangkit.submission2github.ui.viewmodels

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.submission2github.data.FavoriteRepository
import com.bangkit.submission2github.data.local.entity.FavoriteEntity
import com.bangkit.submission2github.data.remote.api.ApiConfig
import com.bangkit.submission2github.data.remote.model.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val _listDetail = MutableLiveData<DetailResponse>()
    val listDetail: LiveData<DetailResponse> = _listDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val mFavoriteUserRepository: FavoriteRepository =
        FavoriteRepository(application)

    fun insert(user: FavoriteEntity) {
        mFavoriteUserRepository.insert(user)
    }

    fun delete(id: Int) {
        mFavoriteUserRepository.delete(id)
    }

    fun getAllFavorites(): LiveData<List<FavoriteEntity>> = mFavoriteUserRepository.getAllFavorites()




    fun getUserItem(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listDetail.postValue(response.body())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "UserDetailModel"
    }
}
