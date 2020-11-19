package com.example.mvpbase.view.recent

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvpbase.BaseActivity
import com.example.mvpbase.R
import com.example.mvpbase.adapter.MainAdapter
import com.example.mvpbase.model.User
import com.example.mvpbase.room.UserDatabase
import kotlinx.android.synthetic.main.activity_recent.*

class RecentActivity: BaseActivity(), RecentContract.View, MainAdapter.OnItemClickListener{

    var adapter = MainAdapter()
    var presenter = RecentPresenter()
    lateinit var progressBar : ProgressBar
    lateinit var clearBtn : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recent)
        title = "RECENT USER"

        progressBar = findViewById<ProgressBar>(R.id.progress_view)
        clearBtn= findViewById<Button>(R.id.btn_clear_all)

        // recycler view 초기화
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        adapter.setClickListener(this)

        // presenter 와 연결
        presenter.setView(this)
        presenter.loadData(UserDatabase.getInstance(this)!!.getUserDao())

        clearBtn.setOnClickListener {
            presenter.clearAll(UserDatabase.getInstance(this)!!.getUserDao())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.releaseView()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setItems(items: ArrayList<User>) {
        adapter.setItems(items)
    }

    override fun onClick(user: User) {
        presenter.deleteData(UserDatabase.getInstance(this)!!.getUserDao(), user)
    }
}