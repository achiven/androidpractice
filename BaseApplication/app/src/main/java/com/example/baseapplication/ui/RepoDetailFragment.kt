package com.example.baseapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.baseapplication.R
import com.example.baseapplication.data.api.GitApis.Companion.GIT_REPO_POSITION
import com.example.baseapplication.data.api.OrgResponse
import com.example.baseapplication.data.model.OrganizationModel
import kotlinx.android.synthetic.main.fragment_repo_detail.*

class RepoDetailFragment : Fragment() {

    private var repoDetail: OrgResponse? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { args ->
            val viewModel = ViewModelProvider(requireActivity()).get(OrganizationModel::class.java)
            repoDetail = viewModel.repos.value?.get(args.getInt(GIT_REPO_POSITION))

            repoDetail?.let {
                showData()
            }
        }
    }

    private fun showData(){

        repoDetail?.run {
            view?.let {
                Glide.with(it)
                    .load(this.owner.avatar_url)
                    .into(avatar_img)
            }
            repo_name.text = this.owner.login
        }

        showDetailText()
    }

    private fun showDetailText() {
        val sb = StringBuilder()

        repoDetail?.run {
            sb.append("Repo name : ${this.name}" + "\n")
            sb.append("Repo Description : ${this.description}" + "\n")
            sb.append("Repo # of stars : ${this.stargazers_count}" + "\n")
            sb.append("Repo # of forks : ${this.forks_count}" + "\n")
            sb.append("Repo # of watchers : ${this.watchers_count}")
        }

        tv_details.text = sb.toString()
    }
}