package com.natife.assotiation_kotlin.initgame

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.natife.assotiation_kotlin.R


class InitGameActivity : AppCompatActivity(), InitGameContract.View {
    override fun resourceForListName(): Resources {
        return resources
    }


    private var recyclerPlayers: RecyclerView? = null
    private var back: ImageView? = null
    private var settings: ImageView? = null
    private var btnAddPlayer: RelativeLayout? = null
    private var btnNext: RelativeLayout? = null
    private var textBtnNext: TextView? = null
    private var adapterPlayers: PlayersAdapter? = null
    private var viewRadioButton: View? = null
    private var textSelection: TextView? = null
    private var mPresenter: InitGameContract.Presenter? = null

//    override fun resourceForListName: Resources
//        get() = this.resources

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс InitGameContract.View
        mPresenter = InitGamePresenter(this)

        initView()

        recyclerPlayers!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mPresenter!!.initPlayerList()
    }//onCreate


    private fun initView() {
        recyclerPlayers = findViewById(R.id.recyclerViewListPlayer)
        textSelection = findViewById(R.id.textSelection)
        back = findViewById(R.id.back)
        settings = findViewById(R.id.settings)
        btnAddPlayer = findViewById(R.id.buttonAddPlayer)
        btnNext = findViewById(R.id.buttonNext)
        textBtnNext = findViewById(R.id.textBtnNext)
        viewRadioButton = findViewById(R.id.viewRadioButton)

        btnAddPlayer!!.setOnClickListener { mPresenter!!.btnAddPlayerClicked() }
        btnNext!!.setOnClickListener { mPresenter!!.btnNextClicked() }
        back!!.setOnClickListener { mPresenter!!.btnBackClicked() }
    }//initView


    override fun showListPlayers(listName: List<String>, listColor: List<Int>) {
        adapterPlayers = PlayersAdapter(this@InitGameActivity, listName, listColor)
        recyclerPlayers!!.adapter = adapterPlayers
    }

    override fun changeScreen(flagChange: Boolean) {
        recyclerPlayers!!.visibility = if (flagChange) View.GONE else View.VISIBLE
        viewRadioButton!!.visibility = if (flagChange) View.VISIBLE else View.GONE
        back!!.visibility = if (flagChange) View.VISIBLE else View.GONE
        settings!!.visibility = if (flagChange) View.VISIBLE else View.GONE
        btnAddPlayer!!.visibility = if (flagChange) View.GONE else View.VISIBLE
        textBtnNext!!.setText(if (flagChange) R.string.text_play else R.string.text_next)
        textSelection!!.setText(if (flagChange) R.string.text_selection_difficulty_level else R.string.text_selection_name)
    }

    companion object {

        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, InitGameActivity::class.java))
        }
    }
}
