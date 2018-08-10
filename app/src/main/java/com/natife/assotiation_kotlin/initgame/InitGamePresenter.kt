package com.natife.assotiation_kotlin.initgame

import android.content.Intent
import android.os.Parcelable
import android.util.Log
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.choose_how_play.ChooseHowPlayActivity
import java.util.ArrayList

class InitGamePresenter(private val mView: InitGameContract.View) : InitGameContract.Presenter {
    private val mRepository: InitGameContract.Repository = InitGameRepository.getInstance()
    private var playerList = mutableListOf<Player>()
    private var flagStartGame = false
    private var listWords = mutableListOf<String>()


    override fun initPlayerList(listWithName: MutableList<Player>?) {
        this.playerList = mRepository.createListPlayer(listWithName)
        mView.showListPlayers(this.playerList)
    }

    override fun btnAddPlayerClicked() {
        if (playerList.size <= 5) {
            playerList = mRepository.addNameInPlayerList()
            mView.showListPlayers(playerList)
        }
    }

    override fun btnNextClicked(difficultLevel: Int) {
        if (flagStartGame) {
            flagStartGame = false
            listWords = mRepository.createListWords(difficultLevel, mView.contextActivity())
            Log.d("ddd", "listWords = $listWords")

            //start to play...
            val intent = Intent(mView.contextActivity(), ChooseHowPlayActivity::class.java)
            intent.putStringArrayListExtra("listWords", listWords as ArrayList<String>)
            intent.putParcelableArrayListExtra("playerList", playerList as ArrayList<out Parcelable>)
            mView.contextActivity().startActivity(intent)

        } else {
            for (i in playerList.indices) {
                if (playerList[i].name.equals("")) {
                    android.support.v7.app.AlertDialog.Builder(mView.contextActivity())
                            .setMessage(R.string.set_name)
                            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                            .show()
                    return
                }
                for (j in i + 1 until playerList.size) {
                    if (playerList[i].name.equals(playerList[j].name)) {
                        android.support.v7.app.AlertDialog.Builder(mView.contextActivity())
                                .setMessage(R.string.set_different_name)
                                .setPositiveButton(R.string.ok) { dialog, which -> dialog.dismiss() }
                                .show()
                        return
                    }
                }
            }
            mView.changeScreen(true)
            flagStartGame = true
        }
    }

    override fun btnBackClicked() {
        flagStartGame = false
        mView.changeScreen(false)
    }

    override fun btnSettingsClicked() {
        mView.showSettingsDialog(flagStartGame)
    }

    override fun getFlagChangeScreen(): Boolean {
        return flagStartGame
    }

    override fun onDestroy() {

    }

}