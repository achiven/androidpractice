package com.example.mvpbase.model

import java.io.Serializable

class Login(
    var username: String? = null,
    var password: String? = null,
    var salt: String? = null,
    var md5: String? = null,
    var sha1: String? = null,
    var sha256: String? = null
) : Serializable {

}