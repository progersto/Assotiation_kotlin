package com.natife.assotiation_kotlin.game

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.widget.*
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.game.UtilForDraw.PaintView
import com.natife.assotiation_kotlin.init_game.Player
import android.graphics.drawable.GradientDrawable
import android.media.AudioManager
import android.view.*
import android.widget.TextView
import android.widget.RelativeLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.natife.assotiation_kotlin.utils.audio.AudioUtil
import com.natife.assotiation_kotlin.utils.restoreColorDraw
import com.natife.assotiation_kotlin.utils.restoreTimeMove
import com.natife.assotiation_kotlin.utils.saveColorDraw

class GameActivity : AppCompatActivity(), GameContract.View, ColorPickerDialogListener {
    private lateinit var mPresenter: GameContract.Presenter
    private lateinit var howExplain: String
    private lateinit var textTimerDraw: TextView
    private lateinit var whoseTurn: TextView
    private lateinit var drawClear: ImageView
    private lateinit var timer: RelativeLayout
    private lateinit var circularProgressbar: ProgressBar
    private lateinit var textTimer: TextView
    private lateinit var layoutBtnFromTellAndShow: View
    private lateinit var theyGuessed: RelativeLayout
    private lateinit var theyNotGuessed: RelativeLayout
    private lateinit var remindWord: RelativeLayout
    private lateinit var layoutBtnPlayer: LinearLayout
    private lateinit var word: String
    private lateinit var paintView: PaintView
    private lateinit var buttonAction: RelativeLayout
    private lateinit var buttonPointBrush: RelativeLayout
    private var flagShowBtn: Boolean = false
    private lateinit var layoutForDraw: RelativeLayout
    private var positionPlayer: Int = 0
    private lateinit var playerList: MutableList<Player>
    private var timerBig: Boolean = false
    private var gd: GradientDrawable? = null
    private var timeMove: Int = 0
    private val DIALOG_ID_COLOR = 0
    private lateinit var colorDialog: ColorPickerDialog.Builder
    private var colorForStartDialog: Int = 0
    private lateinit var backImage: ImageView
    private lateinit var audio: AudioUtil
    private lateinit var mAdView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс GameContract.View
        mPresenter = GamePresenter(this)

        howExplain = intent.getStringExtra("how_explain")
        positionPlayer = intent.getIntExtra("positionPlayer", 0)
        word = intent.getStringExtra("word")
        timeMove = restoreTimeMove(this)//get info from preferences
        colorForStartDialog = restoreColorDraw(this)

        initView()

        audio = AudioUtil.getInstance()
        volumeControlStream = AudioManager.STREAM_MUSIC//volume on the volumeButton
        playerList = mPresenter.getPlayerList()

        starAdvertising()
    }

    private fun starAdvertising() {
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                if (layoutForDraw.visibility != View.VISIBLE){
                    mAdView.visibility = View.VISIBLE
                }
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                mAdView.visibility = View.INVISIBLE
            }

            override fun onAdOpened() {
            }

            override fun onAdLeftApplication() {
            }

            override fun onAdClosed() {
            }
        }
    }


    override fun onResume() {
        super.onResume()
        showView(howExplain)
    }

    private fun showView(howExplain: String) {
        whoseTurn.setTextColor(ContextCompat.getColor(this, playerList[positionPlayer].color))
        when (howExplain) {
            "tell" -> {
                whoseTurn.text = String.format("%s %s", resources.getString(R.string.describes), playerList[positionPlayer].name)
                selectedTellOrShow()
                playerList[positionPlayer].tell = false
            }
            "show" -> {
                whoseTurn.text = String.format("%s %s", resources.getString(R.string.shows), playerList[positionPlayer].name)
                selectedTellOrShow()
                playerList[positionPlayer].show = false
            }
            "draw" -> {
                whoseTurn.text = String.format("%s %s", resources.getString(R.string.draws), playerList[positionPlayer].name)
                selectedDraw()
                playerList[positionPlayer].draw = false
            }
        }
    }

    private fun selectedDraw() {
        flagShowBtn = false
        layoutForDraw.visibility = View.VISIBLE
        layoutBtnFromTellAndShow.visibility = View.GONE
        textTimerDraw.visibility = View.VISIBLE
        drawClear.visibility = View.VISIBLE
        backImage.visibility = View.VISIBLE
        timerBig = false
        mPresenter.initTimer(false, timeMove)

        paintView = (findViewById<View>(R.id.paintView) as PaintView?)!!
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        paintView.init(metrics)
        paintView.normal()
        //        paintView.emboss();//чеканка
        //        paintView.blur();//пятно
    }

    private fun selectedTellOrShow() {
        flagShowBtn = true
        timer.visibility = View.VISIBLE
        layoutBtnFromTellAndShow.visibility = View.VISIBLE
        timerBig = true
        mPresenter.initTimer(true, timeMove)
    }

    private fun initView() {
        backImage = findViewById(R.id.back_image)
        backImage.setOnClickListener { _ ->
            audio.soundClick(this)
            paintView.backPaths() }
        textTimerDraw = findViewById(R.id.text_timer_draw)
        whoseTurn = findViewById(R.id.whose_turn)
        drawClear = findViewById(R.id.draw_clear)
        drawClear.setOnClickListener { _ ->
            audio.soundClick(this)
            paintView.clear() }
        timer = findViewById(R.id.timer)
        circularProgressbar = findViewById(R.id.circularProgressbar)
        circularProgressbar.max = timeMove
        textTimer = findViewById(R.id.text_timer)
        layoutBtnFromTellAndShow = findViewById(R.id.layout_btn_from_tell_and_show)
        theyGuessed = findViewById(R.id.they_guessed)
        theyNotGuessed = findViewById(R.id.they_not_guessed)
        remindWord = findViewById(R.id.remind_word)
        layoutBtnPlayer = findViewById(R.id.layout_btn_player)
        buttonAction = findViewById(R.id.buttonAction)
        layoutForDraw = findViewById(R.id.layout_for_draw)
        buttonPointBrush = findViewById(R.id.buttonPointBrush)
        buttonPointBrush.setOnClickListener { _ ->
            audio.soundClick(this)
            colorDialog = ColorPickerDialog.newBuilder()
            colorDialog.setDialogType(ColorPickerDialog.TYPE_PRESETS)
                    .setAllowPresets(false)
                    .setDialogId(DIALOG_ID_COLOR)
                    .setColor(colorForStartDialog)
                    .setDialogTitle(R.string.select_color)
                    .setSelectedButtonText(R.string.select)
                    .setShowAlphaSlider(false)
                    .setPresetsButtonText(R.string.presets)
                    .setCustomButtonText(R.string.custom)
                    .setShowAlphaSlider(false)
                    .show(this)
        }
        buttonAction.setOnClickListener { _ ->
            audio.soundClick(this)
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.btn_block)
            val theyGuessed = dialog.findViewById<RelativeLayout>(R.id.they_guessed)
            val theyNotGuessed = dialog.findViewById<RelativeLayout>(R.id.they_not_guessed)
            val remindWord = dialog.findViewById<RelativeLayout>(R.id.remind_word)
            theyGuessed.setOnClickListener { _ ->
                dialog.dismiss()
                btnTheyGuessed()
            }
            theyNotGuessed.setOnClickListener { _ ->
                dialog.dismiss()
                btnTheyNotGuessed()
            }
            remindWord.setOnClickListener { _ ->
                dialog.dismiss()
                btnRemindWord()
            }
            dialog.show()
        }
        remindWord.setOnClickListener { _ -> btnRemindWord() }
        theyGuessed.setOnClickListener { btnTheyGuessed() }
        theyNotGuessed.setOnClickListener { btnTheyNotGuessed() }
    }


    private fun btnRemindWord() {
        audio.soundClick(this)
        val toast = Toast.makeText(this, word, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    override fun dialogTimeMoveGone(flag: Boolean) {
        if (flag) {
            btnTheyGuessed()
        } else {
            btnTheyNotGuessed()
        }
    }

    private fun btnTheyNotGuessed() {
        audio.soundClick(this)
        mPresenter.stopCountDownTimer()
        mPresenter.notWin()
    }


    private fun btnTheyGuessed() {
        audio.soundClick(this)
        mPresenter.stopCountDownTimer()
        whoseTurn.text = resources.getString(R.string.who_guessed)
        whoseTurn.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))

        if (flagShowBtn) {
            timer.visibility = View.GONE
        } else {
            textTimerDraw.visibility = View.GONE
            drawClear.visibility = View.GONE
            backImage.visibility = View.GONE
            layoutForDraw.visibility = View.GONE
        }
        layoutBtnFromTellAndShow.visibility = View.GONE
        layoutBtnPlayer.visibility = View.VISIBLE

        for (i in playerList.indices) {
            if (positionPlayer != i) {
                val newItem = LayoutInflater.from(this).inflate(R.layout.item_player_button, null)//добавляемый item
                val btn = newItem.findViewById<RelativeLayout>(R.id.btnPlayer)
                val textBtnPlayer = newItem.findViewById<TextView>(R.id.textBtnPlayer)
                val name = playerList[i].name!!.substring(0, 1).toUpperCase() + playerList[i].name!!.substring(1)
                textBtnPlayer.text = name
                gd = btn.background as GradientDrawable
                gd!!.setColor(ContextCompat.getColor(this, playerList[i].color))
                btn.setOnClickListener { _ ->
                    audio.soundClick(this)
                    mPresenter.playerWin(playerList, i, positionPlayer) }
                layoutBtnPlayer.addView(newItem)
            }
        }
    }

    override fun contextActivity(): Context {
        return this
    }


    override fun finishCurrentGame() {
        mPresenter.gameActivityDestroyed()
        this.finish()
    }


    override fun setCircularProgressbar(progress: Int) {
        circularProgressbar.progress = progress
    }


    override fun setTextTimer(time: String) {
        if (timerBig) {
            textTimer.text = time
        } else
            textTimerDraw.text = time
    }


    override fun onStop() {
        super.onStop()
        if (gd != null) {
            gd!!.setColor(ContextCompat.getColor(this, R.color.colorButton))
        }
        mPresenter.stopCountDownTimer();
    }


    override fun onColorSelected(dialogId: Int, selectedColor: Int) {
        when (dialogId) {
            DIALOG_ID_COLOR -> {
                paintView.setColorPaint(selectedColor)
                colorDialog.setColor(selectedColor)
                colorForStartDialog = selectedColor
                saveColorDraw(this, selectedColor)
            }
        }
    }

    override fun onDialogDismissed(dialogId: Int) {
        //auto generate
    }


    override fun onBackPressed() {}
}

