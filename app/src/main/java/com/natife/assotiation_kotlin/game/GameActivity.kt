package com.natife.assotiation_kotlin.game

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.game.UtilForDraw.PaintView
import java.util.*
import java.util.concurrent.TimeUnit

class GameActivity : AppCompatActivity(), GameContract.View {
    private val mPresenter: GameContract.Presenter? = null
    private var name: String? = null
    private var colorPlayer: Int = 0
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        howExplain = intent.getStringExtra("how_explain")
        name = intent.getStringExtra("name")
        colorPlayer = intent.getIntExtra("colorPlayer", 0)
        listWords = intent.getStringArrayListExtra("listWords")
        word = intent.getStringExtra("word")
        initView()
    }

    override fun onResume() {
        super.onResume()
        showView(howExplain!!)
    }

    private fun showView(howExplain: String) {

        whoseTurn!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
        when (howExplain) {
            "tell" -> {
                whoseTurn!!.text = String.format("%s %s", resources.getString(R.string.describes), name)
                selectedTellOrShow()
            }
            "show" -> {
                whoseTurn!!.text = String.format("%s %s", resources.getString(R.string.shows), name)
                selectedTellOrShow()
            }
            "draw" -> {
                whoseTurn!!.text = String.format("%s %s", resources.getString(R.string.draws), name)
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
        initTimer(textTimerDraw)

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
        initTimer(textTimer)
    }

    private fun initTimer(textTimer: TextView?) {
        mCountDownTimer = object : CountDownTimer((61 * 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                Log.v("Log_tag", "Tick of Progress$millisUntilFinished")

                val iii = 60 - millisUntilFinished.toInt() / 1000
                circularProgressbar!!.progress = iii

                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))

                textTimer!!.text = String.format(Locale.getDefault(), "%01d:%02d", minutes, seconds)
            }

            override fun onFinish() {

            }
        }
        mCountDownTimer!!.start()
    }

    private fun initView() {
        textTimerDraw = findViewById(R.id.text_timer_draw)
        whoseTurn = findViewById(R.id.whose_turn)
        drawClear = findViewById(R.id.draw_clear)
        drawClear!!.setOnClickListener { view -> paintView!!.clear() }
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
        buttonAction!!.setOnClickListener { view -> layoutBtnFromTellAndShow!!.visibility = View.VISIBLE }
        remindWord!!.setOnClickListener { view ->
            val toast = Toast.makeText(this, word, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            if (!flagShowBtn) {
                layoutBtnFromTellAndShow!!.visibility = View.GONE
            }
        }

    }

    override fun contextActivity(): Context {
        return this
    }

    override fun showResultDialog() {

    }
}

