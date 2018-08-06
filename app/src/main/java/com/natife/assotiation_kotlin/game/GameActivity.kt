package com.natife.assotiation_kotlin.game

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.*
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.game.UtilForDraw.PaintView
import com.natife.assotiation_kotlin.initgame.Player
import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import android.widget.RelativeLayout
import android.view.LayoutInflater

class GameActivity : AppCompatActivity(), GameContract.View {
    private var mPresenter: GameContract.Presenter? = null
    private var listWords: List<String>? = null
    private var howExplain: String? = null
    private var textTimerDraw: TextView? = null
    private var whoseTurn: TextView? = null
    private var drawClear: TextView? = null
    private var timer: RelativeLayout? = null
    private var circularProgressbar: ProgressBar? = null
    private var textTimer: TextView? = null
    private var layoutBtnFromTellAndShow: LinearLayout? = null
    private var theyGuessed: RelativeLayout? = null
    private var theyNotGuessed: RelativeLayout? = null
    private var remindWord: RelativeLayout? = null
    private var layoutBtnPlayer: LinearLayout? = null
    private var mCountDownTimer: CountDownTimer? = null
    private var word: String? = null
    private var paintView: PaintView? = null
    private var buttonAction: RelativeLayout? = null
    private var buttonPointBrush: RelativeLayout? = null
    private var flagShowBtn: Boolean = false
    private var layoutForDraw: RelativeLayout? = null
    private var positionPlayer: Int = 0
    private var playerList: MutableList<Player>? = null
    private var timerBig: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        howExplain = intent.getStringExtra("how_explain")
        positionPlayer = intent.getIntExtra("positionPlayer", 0)
        playerList = intent.getParcelableArrayListExtra("playerList")
        listWords = intent.getStringArrayListExtra("listWords")
        word = intent.getStringExtra("word")
        initView()

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс GameContract.View
        mPresenter = GamePresenter(this)
    }



    override fun onResume() {
        super.onResume()
        showView(howExplain!!)
    }

    private fun showView(howExplain: String) {

        whoseTurn!!.setTextColor(ContextCompat.getColor(this, playerList!![positionPlayer].color))
        when (howExplain) {
            "tell" -> {
                whoseTurn!!.text = String.format("%s %s", resources.getString(R.string.describes),  playerList!![positionPlayer].name)
                selectedTellOrShow()
            }
            "show" -> {
                whoseTurn!!.text = String.format("%s %s", resources.getString(R.string.shows),  playerList!![positionPlayer].name)
                selectedTellOrShow()
            }
            "draw" -> {
                whoseTurn!!.text = String.format("%s %s", resources.getString(R.string.draws),  playerList!![positionPlayer].name)
                selectedDraw()
            }
        }
    }

    private fun selectedDraw() {
        flagShowBtn = false
        layoutForDraw!!.visibility = View.VISIBLE
        layoutBtnFromTellAndShow!!.visibility = View.GONE
        textTimerDraw!!.visibility = View.VISIBLE
        drawClear!!.visibility = View.VISIBLE
        timerBig = false
        mPresenter!!.initTimer(false)

        paintView = findViewById<View>(R.id.paintView) as PaintView?
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView!!.init(metrics)
        paintView!!.normal()
        //        paintView.emboss();//чеканка
        //        paintView.blur();//пятно
    }

    private fun selectedTellOrShow() {
        flagShowBtn = true
        timer!!.visibility = View.VISIBLE
        layoutBtnFromTellAndShow!!.visibility = View.VISIBLE
        timerBig = true
        mPresenter!!.initTimer(true)
    }

    private fun initView() {
        textTimerDraw = findViewById(R.id.text_timer_draw)
        whoseTurn = findViewById(R.id.whose_turn)
        drawClear = findViewById(R.id.draw_clear)
        drawClear!!.setOnClickListener { _ -> paintView!!.clear() }
        timer = findViewById(R.id.timer)
        circularProgressbar = findViewById(R.id.circularProgressbar)
        textTimer = findViewById(R.id.text_timer)
        layoutBtnFromTellAndShow = findViewById(R.id.layout_btn_from_tell_and_show)
        theyGuessed = findViewById(R.id.they_guessed)
        theyNotGuessed = findViewById(R.id.they_not_guessed)
        remindWord = findViewById(R.id.remind_word)
        layoutBtnPlayer = findViewById(R.id.layout_btn_player)
        buttonAction = findViewById(R.id.buttonAction)
        layoutForDraw = findViewById(R.id.layout_for_draw)
        buttonPointBrush = findViewById(R.id.buttonPointBrush)
        buttonAction!!.setOnClickListener { _ -> layoutBtnFromTellAndShow!!.visibility = View.VISIBLE }
        remindWord!!.setOnClickListener { _ ->
            val toast = Toast.makeText(this, word, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            if (!flagShowBtn) {
                layoutBtnFromTellAndShow!!.visibility = View.GONE
            }
        }
        theyGuessed!!.setOnClickListener {
            mPresenter!!.stopCountDownTimer()
            timer!!.visibility = View.GONE
            layoutBtnFromTellAndShow!!.visibility = View.GONE
            layoutBtnPlayer!!.visibility = View.VISIBLE
            for (i in 0 until playerList!!.size) {
                if (positionPlayer != i) {
                    val newItem = LayoutInflater.from(this).inflate(R.layout.item_player_button, null)//добавляемый item
                    val btn = newItem.findViewById<RelativeLayout>(R.id.btnPlayer)
                    val textBtnPlayer = newItem.findViewById<TextView>(R.id.textBtnPlayer)
                    val name = playerList!![i].name!!.substring(0, 1).toUpperCase() + playerList!![i].name!!.substring(1)
                    textBtnPlayer.text = name
                    val gd = btn.background as GradientDrawable
                    gd.setColor(ContextCompat.getColor(this, playerList!![i].color))
                    btn.setOnClickListener { _ -> mPresenter!!.playerWin(playerList!!, i) }
                    layoutBtnPlayer!!.addView(newItem)
                }
            }
        }
        theyNotGuessed!!.setOnClickListener {
            mPresenter!!.stopCountDownTimer()
            mPresenter!!.notWin()
        }

    }

    override fun contextActivity(): Context {
        return this
    }

    override fun startGame() {}

    override fun finishCurrentGame() {
        this.finish()
    }

    override fun setCircularProgressbar(progress: Int) {
        circularProgressbar!!.progress = progress
    }

    override fun setTextTimer(time: String) {
        if (timerBig) {
            textTimer!!.text = time
        } else
            textTimerDraw!!.text = time
    }
}

