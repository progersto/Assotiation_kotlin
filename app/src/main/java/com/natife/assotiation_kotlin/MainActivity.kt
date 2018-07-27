package com.natife.assotiation_kotlin

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.natife.assotiation_kotlin.R.string.text_selection_difficulty_level

import com.natife.assotiation_kotlin.adapters.PlayersAdapter
import java.util.*

class MainActivity : AppCompatActivity() {

    private var recyclerPlayers: RecyclerView? = null
    private var back: ImageView? = null
    private var settings: ImageView? = null
    private var btnAddPlayer: RelativeLayout? = null
    private var btnNext: RelativeLayout? = null
    private var textBtnNext: TextView? = null
    private var listName = mutableListOf<String>()
    private var listColor = mutableListOf<Int>()
    private var adapterPlayers: PlayersAdapter? = null
    private var viewRadioButton: View? = null
    private var flagSettingName: Boolean = true
    private var textSelection: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initPlayerList()
        recyclerPlayers!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapterPlayers = PlayersAdapter(this@MainActivity, listName, listColor)
        recyclerPlayers!!.adapter = adapterPlayers
    }//onCreate

    private fun initPlayerList() {
        listName = ArrayList()
        listColor = Arrays.asList(R.color.colorPlayer1, R.color.colorPlayer2, R.color.colorPlayer3,
                R.color.colorPlayer4, R.color.colorPlayer5, R.color.colorPlayer6)
        for (i in 0..2) {
            listName.add(resources.getString(R.string.name_player) + " " + (i + 1))
        }
    }//initPlayerList


    private fun initView() {
        recyclerPlayers = findViewById(R.id.recyclerViewListPlayer)
        textSelection = findViewById(R.id.textSelection)
        back = findViewById(R.id.back)
        settings = findViewById(R.id.settings)
        btnAddPlayer = findViewById(R.id.buttonAddPlayer)
        btnNext = findViewById(R.id.buttonNext)
        textBtnNext = findViewById(R.id.textBtnNext)
        viewRadioButton = findViewById(R.id.viewRadioButton)
        btnAddPlayer!!.setOnClickListener {
            if (listName.size <= 5)
                listName.add(resources.getString(R.string.name_player) + " " + (listName.size + 1))
            adapterPlayers = PlayersAdapter(this@MainActivity, listName, listColor)
            recyclerPlayers!!.adapter = adapterPlayers
        }
        btnNext!!.setOnClickListener {
            if (flagSettingName) {
                recyclerPlayers!!.visibility = View.GONE
                viewRadioButton!!.visibility = View.VISIBLE
                back!!.visibility = View.VISIBLE
                settings!!.visibility = View.VISIBLE
                btnAddPlayer!!.visibility = View.GONE
                textBtnNext!!.setText(R.string.text_play)
                textSelection!!.setText(R.string.text_selection_difficulty_level)
                flagSettingName = false
            } else {
                flagSettingName = true
                //start to play...

            }
        }
        back!!.setOnClickListener {
            recyclerPlayers!!.visibility = View.VISIBLE
            viewRadioButton!!.visibility = View.GONE
            back!!.visibility = View.GONE
            settings!!.visibility = View.GONE
            btnAddPlayer!!.visibility = View.VISIBLE
            textBtnNext!!.setText(R.string.text_next)
            textSelection!!.setText(R.string.text_selection_name)
            flagSettingName = true
        }

    }//initView

    companion object {

        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }


}
