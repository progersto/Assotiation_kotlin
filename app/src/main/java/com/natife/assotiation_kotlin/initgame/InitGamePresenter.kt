package com.natife.assotiation_kotlin.initgame

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.natife.assotiation_kotlin.R

class InitGamePresenter(
//Компоненты MVP приложения

        private val mView: InitGameContract.View) : InitGameContract.Presenter {
    private val mRepository: InitGameContract.Repository

    private var listName = mutableListOf<String>()
    private var listColor = mutableListOf<Int>()
    private var flagStartGame = false

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

    override fun btnNextClicked() {
        if (flagStartGame) {
            flagStartGame = false
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
        dialog.setContentView(R.layout.inform_dialog)
        dialog.show()
    }

    override fun onDestroy() {

    }
}