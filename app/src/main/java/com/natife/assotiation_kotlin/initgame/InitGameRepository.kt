package com.natife.assotiation_kotlin.initgame

import android.content.res.Resources
import com.natife.assotiation_kotlin.R
import java.util.*

class InitGameRepository : InitGameContract.Repository {
//    internal var listName: MutableList<String>
private var listName = mutableListOf<String>()

    override fun createListNamePlayers(resources: Resources): MutableList<String> {
        listName = ArrayList()
        for (i in 0..2) {
            listName.add(resources.getString(R.string.name_player) + " " + (i + 1))
        }
        return listName
    }

    override fun createListColor(): MutableList<Int> {
        return Arrays.asList(R.color.colorPlayer1, R.color.colorPlayer2,
                R.color.colorPlayer3, R.color.colorPlayer4, R.color.colorPlayer5, R.color.colorPlayer6)
    }

    override fun addNamePlayerInList(resources: Resources): MutableList<String> {
        listName.add(resources.getString(R.string.name_player) + " " + (listName.size + 1))
        return listName
    }
}
