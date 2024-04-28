package com.example.dot.presentation.todo

import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.TodoRequest
import com.example.dot.util.GlobalApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoEditViewModel : ViewModel() {

    interface OnFinishedListener {
        fun onSuccess(message: String)

        fun onFailure(message: String)
    }

    fun saveTodo(
        todoRequest: TodoRequest,
        onFinishedListener: OnFinishedListener,
        todoId: String
    ) {
        val apiObject = ApiObject
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")

        apiObject.manageTodo().modifyTodo(accessToken, todoRequest, todoId)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    when (response.code()) {
                        200 -> {
                            onFinishedListener.onSuccess("Todo를 수정하였습니다.")
                        }

                        500 -> {
                            onFinishedListener.onFailure("수정에 실패하였습니다.")
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    onFinishedListener.onFailure("통신에 실패하였습니다.")
                }
            })
    }
}