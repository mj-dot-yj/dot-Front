package com.example.dot.presentation.todo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.util.GlobalApplication
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoViewModel : ViewModel() {
    private var total = MutableLiveData<String>()
    val totalData : LiveData<String>
    get() = total

    interface onGetAllTodoListener {
        fun onSuccess(todoInfoResponseArray: JSONArray)

        fun onFailure(message: String)
    }

    interface onModifyStateListener {
        fun onSuccessModifyState(message: String)

        fun onFailureModifyState(message: String)
    }

    fun getAllTodo(idx: String, date: String, onGetAllTodoListener: onGetAllTodoListener) {
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")
        total.value = (total.value)?.plus(date)
        ApiObject.manageTodo().allTodoByUserIdAndDate(accessToken, idx, date).enqueue(object: Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                val todoList = response.body()!!.result.toString()
                val jsonArray = JSONArray(todoList)
                onGetAllTodoListener.onSuccess(jsonArray)
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("getAllTodo Fail", t.message.toString())
            }
        })
    }

    fun saveState(state: String, todoId: String, onModifyStateListener: onModifyStateListener) {
        val apiObject = ApiObject
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

        apiObject.manageTodo().modifyState(accessToken, state, todoId).enqueue(object: Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                when(response.code()) {
                    200 -> { onModifyStateListener.onSuccessModifyState("state 수정 성공") }
                    500 -> { onModifyStateListener.onFailureModifyState("state 수정 실패") }
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onModifyStateListener.onFailureModifyState("통신 실패")
            }
        })
    }
}