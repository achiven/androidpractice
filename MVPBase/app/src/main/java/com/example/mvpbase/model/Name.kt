package com.example.mvpbase.model

import java.io.Serializable

data class Name(
    var title: String? = null,
    var first: String? = null,
    var last: String? = null
) :
    Serializable {
}