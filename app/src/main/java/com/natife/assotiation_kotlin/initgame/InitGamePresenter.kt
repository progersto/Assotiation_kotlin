package com.natife.assotiation_kotlin.initgame


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
            mView.changeScreen(true)
            flagStartGame = true
        }
    }

    override fun btnBackClicked() {
        flagStartGame = false
        mView.changeScreen(false)
    }

    override fun btnSettingsClicked() {

    }

    override fun onDestroy() {

    }
}