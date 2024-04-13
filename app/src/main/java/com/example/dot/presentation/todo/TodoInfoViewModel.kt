package com.example.dot.presentation.todo

import androidx.lifecycle.ViewModel
import com.example.dot.data.api.ApiObject
import com.example.dot.data.model.ApiResponse
import com.example.dot.data.model.TodoInfoResponse
import com.example.dot.util.GlobalApplication
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TodoInfoViewModel : ViewModel() {
    interface OnGetDataListener {
        fun onSuccessGetData(todoInfoResponse: TodoInfoResponse)
        fun onFailureGetData(message: String)
    }

    interface OnDeleteListener {
        fun onSuccess(message: String)
        fun onFailure(message: String)
    }

    fun showTodoInfo(idx: String, onGetDataListener: TodoInfoViewModel.OnGetDataListener) {
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")
        ApiObject.manageTodo().todoById(accessToken, idx).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                when (response.code()) {
                    200 -> {
                        val json = response.body()!!.result.toString()
                        val gson = Gson()
                        val todoInfoResponse = gson.fromJson(json, TodoInfoResponse::class.java)
                        todoInfoResponse.setTime()

                        onGetDataListener.onSuccessGetData(todoInfoResponse)
                    }
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onGetDataListener.onFailureGetData("통신에 실패하였습니다.")
            }
        })
    }

    fun deleteInfo(idx: String, onDeleteListener: TodoInfoViewModel.OnDeleteListener) {
        val accessToken = GlobalApplication.prefs.getString("accessToken", "")
        ApiObject.manageTodo().deleteTodo(accessToken, idx).enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                when (response.code()) {
                    200 -> {
                        onDeleteListener.onSuccess("할일을 삭제하였습니다.")
                    }

                    500 -> {
                        onDeleteListener.onFailure("삭제에 실패하였습니다.")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onDeleteListener.onFailure("통신에 실패하였습니다.")
            }
        })
    }
}
