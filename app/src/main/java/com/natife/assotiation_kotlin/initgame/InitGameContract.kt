package com.natife.assotiation_kotlin.initgame

interface InitGameContract {

    interface View {
        fun showListPlayers(listName: MutableList<String>, listColor: MutableList<Int>)

        fun changeScreen(flagChange: Boolean)
    }

    interface Presenter {
        fun initPlayerList()

        fun btnAddPlayerClicked()

        fun btnNextClicked()

        fun btnBackClicked()

        fun btnSettingsClicked()

        fun onDestroy()
    }

    interface Repository {
        fun createListNamePlayers(): MutableList<String>

        fun createListColor(): MutableList<Int>

        fun addNamePlayerInList(): MutableList<String>
    }
}