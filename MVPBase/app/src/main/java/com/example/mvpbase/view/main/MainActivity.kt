package com.example.mvpbase.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvpbase.BaseActivity
import com.example.mvpbase.R
import com.example.mvpbase.adapter.MainAdapter
import com.example.mvpbase.model.User
import com.example.mvpbase.room.UserDatabase
import com.example.mvpbase.view.detail.DetailActivity
import com.example.mvpbase.view.detail.KEY_USER
import com.example.mvpbase.view.recent.RecentActivity
import com.example.mvpbase.view.recent.RecentPresenter
import javax.inject.Inject


class MainActivity : BaseActivity(), MainAdapter.OnItemClickListener, MainContract.View {

    @Inject
    lateinit var adapter:MainAdapter

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var recentBtn: Button
    private lateinit var clearRecentBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_view)

        title = "Random User"

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        adapter.setClickListener(this)

        // presenter 와 연결
        presenter.setView(this)

        // 랜덤 유저 데이터를 받아옵니다.
        presenter.loadData()

        // RXEventBus를 연결 합니다.
        presenter.setRxEvent()

        recentBtn = findViewById(R.id.btn_recent_user)
        recentBtn.setOnClickListener {
            val intent = Intent(this, RecentActivity::class.java)
            startActivity(intent)
        }

        clearRecentBtn = findViewById(R.id.btn_clear_recent_all_user)
        clearRecentBtn.setOnClickListener {
            var presenter = RecentPresenter()
            presenter.clearAll(UserDatabase.getInstance(this)!!.getUserDao())
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.releaseView()
    }


    override fun onClick(user: User) {

//         UserDataBase에 저장합니다.
        presenter.addUser(
            UserDatabase.getInstance(application)!!.getUserDao(),
            user!!
        )


//         DetailActivity로 이동합니다.
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(KEY_USER, user)
        startActivity(intent)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hidePregress() {
        progressBar.visibility = View.GONE
    }

    // 아이템을 어댑터에 연결해 줍니다.
    override fun setItems(items: ArrayList<User>) {
        adapter.setItems(items)
    }

    override fun updateView(user: User) {
        adapter.updateView(user)
    }


}