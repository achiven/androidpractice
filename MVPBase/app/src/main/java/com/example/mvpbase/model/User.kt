package com.example.mvpbase.model

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
{"gender":"female","name":{"title":"Mrs","first":"Charlie","last":"Tremblay"},"location":{"street":{"number":7708,"name":"Queen St"},"city":"Princeton","state":"Nova Scotia","country":"Canada","postcode":"S8X 8C8","coordinates":{"latitude":"-68.0375","longitude":"57.6865"},"timezone":{"offset":"+8:00","description":"Beijing, Perth, Singapore, Hong Kong"}},"email":"charlie.tremblay@example.com","login":{"uuid":"4e9dcca7-4232-4707-9032-3d957f95a3c5","username":"blackduck871","password":"citizen","salt":"jmkDbDwb","md5":"8f43fe5eda02685d0db196f237d93550","sha1":"0a166b2448511abece279b1bf4acacdbc77e0ed6","sha256":"a6215c2e1e50c510a2c96b11359920b7f2b7e87854d03b947acc3a188dc0276c"},"dob":{"date":"1977-03-27T10:48:08.565Z","age":43},"registered":{"date":"2007-09-19T16:10:24.548Z","age":13},"phone":"963-627-3822","cell":"811-892-3407","id":{"name":"","value":null},"picture":{"large":"https://randomuser.me/api/portraits/women/44.jpg","medium":"https://randomuser.me/api/portraits/med/women/44.jpg","thumbnail":"https://randomuser.me/api/portraits/thumb/women/44.jpg"},"nat":"CA"}
 */

@Entity(tableName = "userTable")
data class User(
    /*
     변수 이름과 SerializedName 이 같으면 생략 가능하다.
     SerializedName 를 이용해서 json 데이터와 변수 이름을 다르게 지정할 수 있다.
        ex)
            @SerializedName("gender")
            var sex: String = "",
     */

    var gender: String = "maleDefault",

    @Embedded
    var name: Name,

    @Embedded
    var location: Location,

    @Embedded
    var login: Login,

    @Embedded
    var picture: Picture,

    @PrimaryKey
    @NonNull
    var email: String = "emailDefault",

    var phone: String = "phone",
    var cell: String = "cell"

) : Serializable {

    public var linkCnt = 0

    fun getFullName(): String? {
        return name.title + "." + name.first + " " + name.last
    }

    fun getLikeCnt(): String {
        return "Like : $linkCnt"
    }

}
