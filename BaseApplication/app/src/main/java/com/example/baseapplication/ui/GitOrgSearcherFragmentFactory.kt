package com.example.baseapplication.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class GitOrgSearcherFragmentFactory(

): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        when(className){
            SearchFragment::class.java.name ->{
                super.instantiate(classLoader, className)
            }

            RepoDetailFragment::class.java.name ->{
                super.instantiate(classLoader, className)
            }
        }

        return super.instantiate(classLoader, className)
    }
}