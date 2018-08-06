package com.natife.assotiation_kotlin.initgame

import android.os.Parcel
import android.os.Parcelable

class Player : Parcelable {
    var name: String? = null
    var color: Int = 0
    var countWords: Int = 0
    var countScore: Int = 0

    constructor(name: String, color: Int, countWords: Int, countScore: Int) {
        this.name = name
        this.color = color
        this.countWords = countWords
        this.countScore = countScore
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        color = `in`.readInt()
        countWords = `in`.readInt()
        countScore = `in`.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(name)
        parcel.writeInt(color)
        parcel.writeInt(countWords)
        parcel.writeInt(countScore)
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(p0: Parcel?): Player {
            return Player(p0!!)
        }

        override fun newArray(p0: Int): Array<Player?> {
            return arrayOfNulls(p0)
        }
    }
}