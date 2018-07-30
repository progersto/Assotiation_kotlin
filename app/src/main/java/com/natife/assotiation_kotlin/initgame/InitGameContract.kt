package com.natife.assotiation_kotlin.initgame

import android.content.res.Resources

interface InitGameContract {

    interface View {

        fun resourceForListName(): Resources

        fun showListPlayers(listName: List<String>, listColor: List<Int>)

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
        fun createListNamePlayers(resources: Resources): MutableList<String>

        fun createListColor(): MutableList<Int>

        fun addNamePlayerInList(resources: Resources): MutableList<String>
    }

}