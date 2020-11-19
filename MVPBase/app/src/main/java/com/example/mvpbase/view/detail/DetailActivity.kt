package com.example.mvpbase.view.detail

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mvpbase.BaseActivity
import com.example.mvpbase.R
import com.example.mvpbase.model.User
import com.example.mvpbase.room.UserDatabase
import de.hdodenhof.circleimageview.CircleImageView


const val KEY_USER = "key_user"

class DetailActivity : BaseActivity(), DetailContract.View {

    private var tvDetailLIkeCnt: TextView? = null
    var ivDetailProfile: CircleImageView? = null
    private var btnDetailLike: Button? = null

    var presenter = DetailPresenter()
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        tvDetailLIkeCnt = findViewById(R.id.tv_detail_like_cnt)
        ivDetailProfile = findViewById(R.id.iv_detail_profile)
        btnDetailLike = findViewById(R.id.btn_detail_like)
        btnDetailLike?.setOnClickListener {
            presenter.clickEvent(UserDatabase.getInstance(applicationContext)!!.getUserDao(), user)
        }


        presenter.setView(this)

        getUserFromIntent()

    }

    private fun getUserFromIntent() {
        user = intent.getSerializableExtra(KEY_USER) as User
        title = user.getFullName()

        initView(user)

    }

    private fun initView(user: User) {
        tvDetailLIkeCnt?.setText(user.getLikeCnt())

        Glide.with(this)
            .load(user.picture.large)
            .into(ivDetailProfile)

    }

    override fun setText(text: String) {
        tvDetailLIkeCnt?.text = text
    }
}