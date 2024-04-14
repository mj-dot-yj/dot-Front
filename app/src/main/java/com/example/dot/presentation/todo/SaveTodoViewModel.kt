package com.example.dot.presentation.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.TodoRequest
import com.example.dot.util.GlobalApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaveTodoViewModel: ViewModel() {

    interface OnFinishedSaveTodoListener {
        fun onSuccess(message: String)

        fun onFailure(message: String)
    }

    fun saveTodo(
        todoRequest: TodoRequest,
        onFinishedsaveTodoListener: OnFinishedSaveTodoListener
    ) {
        val apiObject = ApiObject
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

        apiObject.manageMember().todoSave(accessToken, todoRequest)
            .enqueue(object: Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    when(response.code()){
                        201 -> {
                            onFinishedsaveTodoListener.onSuccess("TodoSave 성공")
                        }

                        500 -> {
                            onFinishedsaveTodoListener.onFailure("TodoSave 실패")
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    onFinishedsaveTodoListener.onFailure("TodoSave 통신 실패")
                }
            })
    }
}