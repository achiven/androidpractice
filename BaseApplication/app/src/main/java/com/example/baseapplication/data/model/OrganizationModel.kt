package com.example.baseapplication.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baseapplication.R
import com.example.baseapplication.data.api.GitApiProvider
import com.example.baseapplication.data.api.GitApis.Companion.GIT_REPO_PARAM_PAGE
import com.example.baseapplication.data.api.GitApis.Companion.getGitOrgReposUrl
import com.example.baseapplication.data.api.OrgResponse
import com.example.baseapplication.data.constant.ErrorCode.Companion.HTTP_200_OK
import com.example.baseapplication.data.constant.ErrorCode.Companion.HTTP_403_ERROR
import com.example.baseapplication.data.constant.ErrorCode.Companion.HTTP_404_ERROR
import com.example.baseapplication.data.pagination.PaginationScrollListener.Companion.FIRST_PAGE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class OrganizationModel : ViewModel() {

    var repos = MutableLiveData<ArrayList<OrgResponse>>()

    private val disposable = CompositeDisposable()
    private val api = GitApiProvider.provideRepoApi()

    fun retrieveRepos(
        orgName: String,
        page: Int,
        showProgress: () -> Unit,
        hideProgress: () -> Unit,
        showToast: (message: Int) -> Unit
    ) {
        disposable.add(
            api.getOrgRepos(
                getGitOrgReposUrl(orgName)
                        + "?" + GIT_REPO_PARAM_PAGE + page.toString()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    showProgress()
                }
                .subscribe(
                    { results ->
                        CoroutineScope(Dispatchers.IO).launch {
                            CoroutineScope(Dispatchers.Main).launch {
                                if (page == FIRST_PAGE) {
                                    repos.value = results as ArrayList<OrgResponse>
                                    hideProgress()
                                } else {
                                    // pagination
                                    delay(2000)
                                    hideProgress()

                                    repos.value!!.addAll(results as ArrayList<OrgResponse>)
                                    repos.value = repos.value
                                }
                            }
                        }
                    },
                    {
                        hideProgress()
                        val code = (it as HttpException).code()

                        repos.value = ArrayList()

                        if (code != HTTP_200_OK) {
                            when (code) {
                                HTTP_404_ERROR -> showToast(R.string.http_404)
                                HTTP_403_ERROR -> showToast(R.string.http_403)
                                else -> showToast(R.string.http_else_error)
                            }
                        }
                    }
                )
        )

    }
}
