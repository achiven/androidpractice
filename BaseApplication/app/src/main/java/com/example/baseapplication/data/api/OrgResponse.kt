package com.example.baseapplication.data.api

data class OrgResponse(
    val id: Int,
    val owner: Owner,
    val name: String,
    val description: String,
    val stargazers_count: Int,
    val forks_count: Int,
    val watchers_count: Int
) {
}

data class Owner(
    val avatar_url: String,
    val login: String
)