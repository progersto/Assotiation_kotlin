package com.natife.assotiation_kotlin.choose_how_play

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.*
import com.natife.assotiation_kotlin.R
import java.util.ArrayList
import com.natife.assotiation_kotlin.init_game.Player
import com.natife.assotiation_kotlin.game.GameActivity
import com.natife.assotiation_kotlin.resultgame.ResultGame
import com.natife.assotiation_kotlin.utils.*

class ChooseHowPlayActivity : AppCompatActivity(), ChooseHowPlayContract.View {
    private lateinit var mPresenter: ChooseHowPlayContract.Presenter
    private lateinit var listWords: MutableList<String>
    private lateinit var whoseTurn: TextView
    private lateinit var results: ImageView
    private lateinit var textSelection: TextView
    private lateinit var frameWord1: FrameLayout
    private lateinit var frameWord2: FrameLayout
    private lateinit var frameShowWords: FrameLayout
    private lateinit var word1: TextView
    private lateinit var word2: TextView
    private lateinit var layoutShow: FrameLayout
    private lateinit var layoutTell: FrameLayout
    private lateinit var layoutDraw: FrameLayout
    private lateinit var iconShow: ImageView
    private lateinit var iconTell: ImageView
    private lateinit var iconDraw: ImageView
    private lateinit var textDraw: TextView
    private lateinit var textShow: TextView
    private lateinit var textTell: TextView
    private lateinit var buttonGo: RelativeLayout
    private var colorPlayer = 0
    private var flagWord = false
    private var flagAction = false
    private lateinit var playerList: MutableList<Player>
    private lateinit var howExplain: String
    private lateinit var word: String
    private var timeMove: Int = 0
    private var timeGame: Int = 0
    private var numberCircles: Int = 0
    private var timeGameFlag = true
    private var positionPlayer: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_how_play)

        //get info from preferences
        timeMove = restoreTimeMove(this)
        timeGame = restoreTimeGame(this)
        numberCircles = restoreNumberCircles(this)

        if (timeMove == 0 || timeGame == 0 || numberCircles == 0) {
            initPreference(this)
            timeMove = restoreTimeMove(this)
            timeGame = restoreTimeGame(this)
            numberCircles = restoreNumberCircles(this)
        }

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс Contract.View
        mPresenter = ChooseHowPlayPresenter(this)

        listWords = intent.getStringArrayListExtra("listWords") as MutableList<String>
        playerList = mPresenter.getPlayerList()

        initViews()

        mPresenter.findDataForFillFields(playerList, listWords, timeGame)
    }

    private fun initViews() {
        whoseTurn = findViewById(R.id.whose_turn)
        results = findViewById(R.id.results)
        textSelection = findViewById(R.id.textSelection)
        frameWord1 = findViewById(R.id.frame_word1)
        frameWord2 = findViewById(R.id.frame_word2)
        word1 = findViewById(R.id.word1)
        word2 = findViewById(R.id.word2)
        layoutShow = findViewById(R.id.layout_show)
        layoutTell = findViewById(R.id.layout_tell)
        layoutDraw = findViewById(R.id.layout_draw)
        frameShowWords = findViewById(R.id.frame_show_words)
        iconShow = findViewById(R.id.iconShow)
        iconTell = findViewById(R.id.iconTell)
        iconDraw = findViewById(R.id.icon_draw)
        textDraw = findViewById(R.id.text_draw)
        textShow = findViewById(R.id.text_show)
        textTell = findViewById(R.id.text_tell)
        buttonGo = findViewById(R.id.buttonGo)

        results.setOnClickListener { showResultDialog(); }
        frameShowWords.setOnClickListener {
            frameShowWords.visibility = (View.GONE)
            frameWord1.visibility = (View.VISIBLE)
            frameWord2.visibility = View.VISIBLE
        }
        word1.setOnClickListener {
            flagWord = true

            word = word1.text.toString()
            word1.setTextColor(ContextCompat.getColor(this, colorPlayer))
            word2.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            frameWord1.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            frameWord2.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = frameWord1.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
        }
        word2.setOnClickListener {
            flagWord = true

            word = word2.text.toString()
            word2.setTextColor(ContextCompat.getColor(this, colorPlayer))
            word1.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            frameWord2.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            frameWord1.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = frameWord2.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
        }
        layoutShow.setOnClickListener {
            if (playerList[positionPlayer].show) {
                flagAction = true

                howExplain = "show"
                textShow.setTextColor(ContextCompat.getColor(this, colorPlayer))
                iconShow.setColorFilter(ContextCompat.getColor(this, colorPlayer))
                layoutShow.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
                val gd = layoutShow.foreground as GradientDrawable
                gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
                iconShow.alpha = 1F
                textShow.alpha = 1F

                if (playerList[positionPlayer].draw) {
                    textDraw.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
                    iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
                    layoutDraw.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
                }

                if (playerList[positionPlayer].tell) {
                    textTell.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
                    iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
                    layoutTell.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
                }
            }
        }
        layoutTell.setOnClickListener {
            if (playerList[positionPlayer].tell) {
                flagAction = true

                howExplain = "tell"
                textTell.setTextColor(ContextCompat.getColor(this, colorPlayer))
                iconTell.setColorFilter(ContextCompat.getColor(this, colorPlayer))
                layoutTell.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
                val gd = layoutTell.foreground as GradientDrawable
                gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
                iconTell.alpha = 1F
                textTell.alpha = 1F

                if (playerList[positionPlayer].show) {
                    textShow.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
                    iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
                    layoutShow.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
                }

                if (playerList[positionPlayer].draw) {
                    textDraw.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
                    iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
                    layoutDraw.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
                }
            }
        }
        layoutDraw.setOnClickListener {
            if (playerList[positionPlayer].draw) {
                flagAction = true

                howExplain = "draw"
                textDraw.setTextColor(ContextCompat.getColor(this, colorPlayer))
                iconDraw.setColorFilter(ContextCompat.getColor(this, colorPlayer))
                layoutDraw.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
                //change color frame
                val gd = layoutDraw.foreground as GradientDrawable
                gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
                iconDraw.alpha = 1F
                textDraw.alpha = 1F

                if (playerList[positionPlayer].show) {
                    textShow.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection)) //color text
                    iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection)) //color icon
                    layoutShow.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)  //background
                }

                if (playerList[positionPlayer].tell) {
                    textTell.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
                    iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
                    layoutTell.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
                }
            }
        }
        buttonGo.setOnClickListener {
            if (flagWord && flagAction) {
                mPresenter.buttonGoPressed()
                flagWord = false
                flagAction = false
            } else if (!flagWord && flagAction || !flagWord && !flagAction) {
                Toast.makeText(this, "Выберите слово", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, "Выберите действие", Toast.LENGTH_SHORT).show()
        }
    }

//    private fun checkFillingField(){
//        if (flagWord && flagAction) {
//            buttonGo.background.alpha = 0
//        }
//    }

    override fun startGameActivity(posPlayer: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("positionPlayer", posPlayer)
        intent.putParcelableArrayListExtra("playerList", playerList as ArrayList<out Parcelable>)
        intent.putExtra("word", word)
        intent.putExtra("how_explain", howExplain)
        startActivity(intent)
    }

    override fun showResultDialog() {
        val intent = Intent(this, ResultGame::class.java)
        intent.putExtra("timeGameFlag", timeGameFlag)
        intent.putParcelableArrayListExtra("playerList", playerList as ArrayList<out Parcelable>)
        startActivity(intent)
    }

    override fun showData(name: String, color: Int, word1: String, word2: String, positionPlayer: Int) {
        whoseTurn.text = String.format("%s %s", resources.getString(R.string.turn), name)
        whoseTurn.setTextColor(ContextCompat.getColor(this, color))
        this.word1.text = word1
        this.word2.text = word2

        colorPlayer = color
        this.positionPlayer = positionPlayer
        if (!playerList[positionPlayer].show) {
            textShow.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))//text
            iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))//icon
            layoutShow.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)//background
            val gd = layoutShow.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, R.color.colorNotActive))
            gd.alpha = 50
            iconShow.alpha = 0.3F
            textShow.alpha = 0.3F
        }
        if (!playerList[positionPlayer].tell) {
            textTell.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))//text
            iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))//icon
            layoutTell.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)//background
            val gd = layoutTell.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, R.color.colorNotActive))
            gd.alpha = 50
            iconTell.alpha = 0.3F
            textTell.alpha = 0.3F
        }
        if (!playerList[positionPlayer].draw) {
            textDraw.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))//text
            iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))//icon
            layoutDraw.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)//background
            val gd = layoutDraw.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, R.color.colorNotActive))
            gd.alpha = 50
            iconDraw.alpha = 0.3F
            textDraw.alpha = 0.3F
        }
    }

    override fun getContextActivity(): Context {
        return this
    }


    override fun gameOver() {
        timeGameFlag = false
    }


    override fun onRestart() {
        super.onRestart()
        frameShowWords.visibility = View.VISIBLE
        frameWord1.visibility = View.GONE
        frameWord2.visibility = View.GONE
        word2.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
        word1.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
        frameWord2.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
        frameWord1.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
        mPresenter.findDataForFillFields(playerList, listWords, timeGame)

        if (playerList[positionPlayer].show) {
            layoutShow.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            iconShow.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
            textShow.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            val gd = layoutShow.foreground as GradientDrawable

            iconShow.alpha = 1F
            textShow.alpha = 1F
        }

        if (playerList[positionPlayer].tell) {
            layoutTell.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            iconTell.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
            textTell.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            val gd = layoutTell.foreground as GradientDrawable

            iconTell.alpha = 1F
            textTell.alpha = 1F
        }

        if (playerList[positionPlayer].draw) {
            layoutDraw.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            iconDraw.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
            textDraw.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            val gd = layoutDraw.foreground as GradientDrawable

            iconDraw.alpha = 1F
            textDraw.alpha = 1F
        }

        if (!timeGameFlag) {
            showResultDialog()
        }
    }

    override fun onStop() {
        super.onStop()
        mPresenter.stopTimerGame()
    }

    override fun onBackPressed() {}

}
