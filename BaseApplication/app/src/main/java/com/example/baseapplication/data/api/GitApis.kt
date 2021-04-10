package com.example.baseapplication.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface GitApis {
    @GET
    fun getOrgRepos(@Url url:String): Observable<List<OrgResponse>>

    companion object {
        val GIT_BASE_URL: String
            get() = "https://api.github.com/"
        val GIT_ORG_SEARCH_URL: String
            get() = "https://api.github.com/orgs"

        val GIT_REPO_POSITION
            get() = "repo_position"

        val GIT_REPO_PARAM_PAGE: String
            get() = "page="

        // example
        // https://api.github.com/orgs/microsoft/repos?per_page=10&page=1
        fun getGitOrgReposUrl(orgName: String) = "$GIT_ORG_SEARCH_URL/$orgName/repos"
    }
}