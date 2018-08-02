package com.natife.assotiation_kotlin.choose_how_play

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.natife.assotiation_kotlin.R

class ChooseHowPlayActivity() : AppCompatActivity(), ChooseHowPlayContract.View {


    private var mPresenter: ChooseHowPlayContract.Presenter? = null
    private var listName: List<String>? = null
    private var listColor: List<Int>? = null
    private var listWords: List<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_how_play)

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс Contract.View
        mPresenter = ChooseHowPlayPresenter(this)

        listName = intent.getStringArrayListExtra("listName")
        listColor = intent.getIntegerArrayListExtra("listColor")
        listWords = intent.getStringArrayListExtra("listWords")

        initViews();
    }

    private fun initViews() {

    }

    override fun contextActivity(): Context {
       return this
    }

    override fun showResultDialog() {

    }
}
