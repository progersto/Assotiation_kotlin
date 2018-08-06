package com.natife.assotiation_kotlin.choose_how_play

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.*
import com.natife.assotiation_kotlin.R
import java.util.ArrayList
import com.natife.assotiation_kotlin.initgame.Player

class ChooseHowPlayActivity : AppCompatActivity(), ChooseHowPlayContract.View {

    private var mPresenter: ChooseHowPlayContract.Presenter? = null
    private var listName: MutableList<String>? = null
    private var listColor: MutableList<Int>? = null
    private var listWords: MutableList<String>? = null
    private var playerList: MutableList<Player>? = null
    private var whoseTurn: TextView? = null
    private var results: ImageView? = null
    private var textSelection: TextView? = null
    private var frameWord1: FrameLayout? = null
    private var frameWord2: FrameLayout? = null
    private var frameShowWords: FrameLayout? = null
    private var word1: TextView? = null
    private var word2: TextView? = null
    private var layoutShow: FrameLayout? = null
    private var layoutTell: FrameLayout? = null
    private var layoutDraw: FrameLayout? = null
    private var iconShow: ImageView? = null
    private var iconTell: ImageView? = null
    private var iconDraw: ImageView? = null
    private var textDraw: TextView? = null
    private var textShow: TextView? = null
    private var textTell: TextView? = null
    private var buttonGo: RelativeLayout? = null
    private var colorPlayer = 0
    private var flagWord = false
    private var flagAction = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_how_play)

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс Contract.View
        mPresenter = ChooseHowPlayPresenter(this)

        listWords = intent.getStringArrayListExtra("listWords") as MutableList<String>
        playerList = intent.getParcelableArrayListExtra<Player>("playerList")

        initViews()

        mPresenter!!.findDataForFillFields(playerList!!, listWords!!)
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

        results!!.setOnClickListener { mPresenter!!.resultPressed() }
        frameShowWords!!.setOnClickListener {
            frameShowWords!!.visibility = (View.GONE)
            frameWord1!!.visibility = (View.VISIBLE)
            frameWord2!!.visibility = View.VISIBLE
        }
        word1!!.setOnClickListener {
            flagWord = true
            mPresenter!!.word1Pressed(word1!!.text.toString())
            word1!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            word2!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            frameWord1!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            frameWord2!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = frameWord1!!.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
        }
        word2!!.setOnClickListener {
            flagWord = true
            mPresenter!!.word1Pressed(word2!!.text.toString())
            word2!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            word1!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            frameWord2!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            frameWord1!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = frameWord2!!.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
        }
        layoutShow!!.setOnClickListener {
            flagAction = true
            mPresenter!!.layoutShow_Pressed()
            textShow!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            textTell!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            textDraw!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            iconDraw!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
            iconTell!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
            iconShow!!.setColorFilter(ContextCompat.getColor(this, colorPlayer))
            layoutShow!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            layoutTell!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            layoutDraw!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = layoutShow!!.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
        }
        layoutTell!!.setOnClickListener {
            flagAction = true
            mPresenter!!.layoutTell_Pressed()
            textShow!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            textTell!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            textDraw!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            iconDraw!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
            iconTell!!.setColorFilter(ContextCompat.getColor(this, colorPlayer))
            iconShow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
            layoutShow!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            layoutTell!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            layoutDraw!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = layoutTell!!.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
        }
        layoutDraw!!.setOnClickListener {
            flagAction = true
            mPresenter!!.layoutDraw_Pressed()
            //color text
            textShow!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            textTell!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
            textDraw!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            //color icon
            iconDraw!!.setColorFilter(ContextCompat.getColor(this, colorPlayer))
            iconTell!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
            iconShow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
            //background
            layoutShow!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            layoutTell!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            layoutDraw!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            //change color frame
            val gd = layoutDraw!!.foreground as GradientDrawable
            gd.setStroke(3, ContextCompat.getColor(this, colorPlayer))
        }
        buttonGo!!.setOnClickListener {
            if (flagWord && flagAction) {
                mPresenter!!.buttonGo()
                flagWord = false
                flagAction = false
            } else if (!flagWord && flagAction || !flagWord && !flagAction) {
                Toast.makeText(this, "Выберите слово", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, "Выберите действие", Toast.LENGTH_SHORT).show()
        }
    }

    override fun contextActivity(): Context {
        return this
    }

    override fun showResultDialog() {
        val dialogResult = DialogResult()
        val args = Bundle()
        args.putParcelableArrayList("playerList", playerList as ArrayList<out Parcelable>)
        dialogResult.arguments = args
        dialogResult.show(supportFragmentManager, "dialogResult")
    }

    override fun showData(name: String, color: Int, word1: String, word2: String) {
        whoseTurn!!.text = String.format("%s %s", resources.getString(R.string.turn), name)
        whoseTurn!!.setTextColor(ContextCompat.getColor(this, color))
        this.word1!!.text = word1
        this.word2!!.text = word2

        colorPlayer = color
    }

    override fun onRestart() {
        super.onRestart()
        val ddd = playerList
        frameShowWords!!.visibility = View.VISIBLE
        frameWord1!!.visibility = View.GONE
        frameWord2!!.visibility = View.GONE
        word2!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
        word1!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
        frameWord2!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
        frameWord1!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
        layoutShow!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
        layoutTell!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
        layoutDraw!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
        iconDraw!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
        iconTell!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
        iconShow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelection))
        textShow!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
        textTell!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
        textDraw!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelection))
        mPresenter!!.findDataForFillFields(playerList!!, listWords!!)
    }

}
