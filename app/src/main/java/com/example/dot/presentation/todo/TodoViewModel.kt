package com.example.dot.presentation.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.TodoInfoResponse
import com.example.dot.util.GlobalApplication
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoViewModel : ViewModel() {

    interface onGetAllTodoListener {
        fun onSuccess(todoInfoResponseArray: JSONArray)

        fun onFailure(message: String)
    }

    fun getAllTodo(idx: String, onGetAllTodoListener: onGetAllTodoListener) {
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")
        ApiObject.manageTodo().allTodoByUserId(accessToken, idx).enqueue(object: Callback<ApiResponse> {
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
}