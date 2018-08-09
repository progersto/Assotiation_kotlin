package com.natife.assotiation_kotlin.resultgame

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.View.inflate
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.initgame.InitGameActivity
import com.natife.assotiation_kotlin.initgame.Player
import java.util.ArrayList

class ResultGame : AppCompatActivity() {
    internal var timeMoveTV: TextView? = null
    internal var timeGameTV: TextView? = null
    internal var numberCirclesTV: TextView? = null
    internal var timeMove: Int = 0
    internal var timeGame: Int = 0
    internal var numberCircles: Int = 0
    private var playerList: List<Player>? = null
    private var localPayerList: MutableList<Player>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewResult = layoutInflater.inflate(R.layout.activity_result, null)

        val timeGameFlag = intent.getBooleanExtra("timeGameFlag", false)
        playerList = intent.getParcelableArrayListExtra("playerList")
        localPayerList = ArrayList(playerList)

        val buttonAgain: RelativeLayout = viewResult.findViewById(R.id.buttonAgain)
        buttonAgain.setOnClickListener {
            finishAffinity()
            val intent = Intent(this, InitGameActivity::class.java)
            intent.putParcelableArrayListExtra("playerList", playerList as ArrayList<out Parcelable>)
            startActivity(intent)
        }
        val gd = buttonAgain.background as GradientDrawable
        gd.setColor(ContextCompat.getColor(this, R.color.colorButton))

        (localPayerList as ArrayList<Player>).sortWith(Comparator { player, t1 ->
            if (player.countScore == t1.countScore)
                0
            else if (player.countScore < t1.countScore) 1
            else -1
        })

        val layoutResult: LinearLayout = viewResult.findViewById(R.id.layoutResult)//контейнер для вставки item

        val isWin = checkWin()
        for (i in playerList!!.indices) {
            val newItem = inflate(this, R.layout.item_result, null)//добавляемый item
            val image = newItem.findViewById<ImageView>(R.id.image_result)
            val nameResult = newItem.findViewById<TextView>(R.id.name_result)//inserted name
            val totalPointsResult = newItem.findViewById<TextView>(R.id.total_points)
            val guessedWordsResult = newItem.findViewById<TextView>(R.id.guessed_words)

            val name = playerList!![i].name!!.substring(0, 1).toUpperCase() + playerList!![i].name!!.substring(1)
            nameResult.text = name
            val guessedWords = String.format("%s %s %s",
                    resources.getString(R.string.guessed),
                    localPayerList!![i].countWords,
                    resources.getString(R.string.words))
            guessedWordsResult.text = guessedWords
            totalPointsResult.text = localPayerList!![i].countScore.toString()
            if (isWin && i == 0) {
                image.visibility = View.VISIBLE
            } else
                image.visibility = View.INVISIBLE
            layoutResult.addView(newItem)
        }

        setContentView(viewResult)
    }

    private fun checkWin(): Boolean {
        var flag = true
        if (localPayerList!!.get(index = 0).countScore == localPayerList!!.get(index = 1).countScore) {
            flag = false
        }
        return flag
    }
}
