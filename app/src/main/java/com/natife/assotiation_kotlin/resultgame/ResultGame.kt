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
import android.support.v4.view.MenuItemCompat
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
import android.support.v4.view.MenuItemCompat.getActionProvider
import android.support.v7.widget.ShareActionProvider
import android.support.v7.widget.Toolbar
import android.view.Menu


class ResultGame : AppCompatActivity() {
    internal var timeMoveTV: TextView? = null
    internal var timeGameTV: TextView? = null
    internal var numberCirclesTV: TextView? = null
    internal var timeMove: Int = 0
    internal var timeGame: Int = 0
    internal var numberCircles: Int = 0
    private var playerList: List<Player>? = null
    private var localPayerList: MutableList<Player>? = null
    private lateinit var mShareActionProvider: android.support.v7.widget.ShareActionProvider
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewResult = layoutInflater.inflate(R.layout.activity_result, null)
        toolbar = viewResult.findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        val timeGameFlag = intent.getBooleanExtra("timeGameFlag", false)
        playerList = intent.getParcelableArrayListExtra("playerList")
        localPayerList = ArrayList(playerList as List<Player>)

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
            when {
                player.countScore == t1.countScore -> 0
                player.countScore < t1.countScore -> 1
                else -> -1
            }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_result, menu)

        val item = menu.findItem(R.id.menu_share)
        mShareActionProvider = MenuItemCompat.getActionProvider(item) as android.support.v7.widget.ShareActionProvider

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_text))
        shareIntent.putExtra(Intent.EXTRA_STREAM, resources.getString(R.string.share_URI))

        shareIntent.type = "text/plain"
        mShareActionProvider.setShareIntent(shareIntent)

        return true
    }

}
