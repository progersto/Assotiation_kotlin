package com.natife.assotiation_kotlin.initgame

import android.content.Context
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.utils.ListGenerator
import java.util.*
import kotlin.collections.ArrayList

class InitGameRepository : InitGameContract.Repository {

    private var listName = mutableListOf<String>()

    override fun addNamePlayerInList(): MutableList<String> {
        listName.add("")
        return listName
    }

    override fun createListNamePlayers(listWithName: MutableList<String>?): MutableList<String> {
        listName = java.util.ArrayList()
        if (listWithName == null) {
            for (i in 0..2) {
                listName.add("")
            }
        } else {
            listName.addAll(listWithName)
        }
        return listName
    }

    override fun createListColor(): MutableList<Int> {
        return java.util.ArrayList(Arrays.asList(R.color.colorPlayer1, R.color.colorPlayer2,
                R.color.colorPlayer3, R.color.colorPlayer4, R.color.colorPlayer5, R.color.colorPlayer6))
    }


    override fun createListWords(difficultLevel: Int, context: Context): MutableList<String> {
        return ListGenerator.createListSelectedLevel(context, difficultLevel)
    }

}
