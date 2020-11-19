package com.example.mvpbase.constant

interface Constant {

    companion object{
        public val BASE_URL: String
            get() = "http://api.randomuser.me/"

        public val RANDOM_USER_URL: String
            get() = "?results=10"
    }
}