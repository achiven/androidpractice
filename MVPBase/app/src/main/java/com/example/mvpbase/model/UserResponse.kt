package com.example.mvpbase.model

import com.google.gson.annotations.SerializedName


class UserResponse {
    @SerializedName("results")                      // 수신 하는 데이터 중 사용하려는 필드를 표현
    var userList: List<User>? = listOf()
}
