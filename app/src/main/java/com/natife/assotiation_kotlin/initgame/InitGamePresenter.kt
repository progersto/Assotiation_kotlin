package com.natife.assotiation_kotlin.initgame

import android.content.Intent
import android.util.Log
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.choose_how_play.ChooseHowPlayActivity
import java.util.ArrayList

class InitGamePresenter(private val mView: InitGameContract.View) : InitGameContract.Presenter {

    private val mRepository: InitGameContract.Repository

    private var listName = mutableListOf<String>()
    private var listColor = mutableListOf<Int>()
    private var flagStartGame = false
    private var listWords = mutableListOf<String>()


    init {
        this.mRepository = InitGameRepository()
    }


    override fun initPlayerList(listName: MutableList<String>?) {
        this.listName = mRepository.createListNamePlayers(listName)
        listColor = mRepository.createListColor()
        mView.showListPlayers(this.listName, listColor)
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
            val intent = Intent(mView.contextActivity(), ChooseHowPlayActivity::class.java)
            intent.putStringArrayListExtra("listWords", listWords as ArrayList<String>?)
            intent.putIntegerArrayListExtra("listColor", listColor as ArrayList<Int>?)
            intent.putStringArrayListExtra("listName", listName as ArrayList<String>?)
            mView.contextActivity().startActivity(intent)

        } else {
            if (listName.contains("")) {
                android.support.v7.app.AlertDialog.Builder(mView.contextActivity())
                        .setMessage(R.string.set_name)
                        .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                        .show()
            } else {
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
        mView.showSettingsDialog(flagStartGame)
    }

    override fun onDestroy() {

    }
}