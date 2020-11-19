package com.example.mvpbase.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//"location":{"street":{"number":6732,"name":"Avenue de la République"}
class Location
    : Serializable {

    @Embedded
    var street = Street()

    class Street(var number: String? = null,var name: String? = null) : Serializable
}