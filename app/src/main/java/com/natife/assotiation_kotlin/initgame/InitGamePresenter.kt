package com.natife.assotiation_kotlin.initgame

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.Window
import com.natife.assotiation_kotlin.R

class InitGamePresenter(
//Компоненты MVP приложения

        private val mView: InitGameContract.View) : InitGameContract.Presenter {
    private val mRepository: InitGameContract.Repository

    private var listName = mutableListOf<String>()
    private var listColor = mutableListOf<Int>()
    private var flagStartGame = false
    private var listWords = mutableListOf<String>()


    init {
        this.mRepository = InitGameRepository()
    }


    override fun initPlayerList() {
        listName = mRepository.createListNamePlayers()
        listColor = mRepository.createListColor()
        mView.showListPlayers(listName, listColor)
    }

    override fun btnAddPlayerClicked() {
        if (listName.size <= 5) {
            listName = mRepository.addNamePlayerInList()
            mView.showListPlayers(listName, listColor)
        }
    }

    override fun btnNextClicked(difficultLevel: Int) {
        if (flagStartGame) {
            flagStartGame = false
            listWords = mRepository.createListWords(difficultLevel, mView.contextActivity())
            Log.d("ddd", "listWords = $listWords")
            //start to play...

        } else {
            if (listName.contains("")){
                android.support.v7.app.AlertDialog.Builder(mView.contextActivity())
                        .setMessage(R.string.set_name)
                        .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                        .show()
            }else {
                mView.changeScreen(true)
                flagStartGame = true
            }
        }
    }

    override fun btnBackClicked() {
        flagStartGame = false
        mView.changeScreen(false)
    }

    override fun btnSettingsClicked() {
        val dialog = Dialog(mView.contextActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (flagStartGame){
            dialog.setContentView(R.layout.dialog_settings_game)
        }else{
            dialog.setContentView(R.layout.dialog_inform)
        }
        dialog.findViewById<View>(R.id.number_of_circles_minus).setOnClickListener{}
        dialog.findViewById<View>(R.id.number_of_circles_plus).setOnClickListener{}
        dialog.findViewById<View>(R.id.time_game_minus).setOnClickListener{}
        dialog.findViewById<View>(R.id.time_game_plus).setOnClickListener{}
        dialog.findViewById<View>(R.id.time_move_plus).setOnClickListener{}
        dialog.findViewById<View>(R.id.time_move_minus).setOnClickListener{}
        dialog.show()
    }

    override fun onDestroy() {

    }
}