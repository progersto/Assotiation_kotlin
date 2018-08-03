package com.natife.assotiation_kotlin.choose_how_play

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.natife.assotiation_kotlin.R

class ChooseHowPlayActivity : AppCompatActivity(), ChooseHowPlayContract.View {

    private var mPresenter: ChooseHowPlayContract.Presenter? = null
    private var listName: MutableList<String>? = null
    private var listColor: MutableList<Int>? = null
    private var listWords: MutableList<String>? = null
    private var whoseTurn: TextView? = null
    private var results: ImageView? = null
    private var textSelection: TextView? = null
    private var frameWord1: FrameLayout? = null
    private var frameWord2: FrameLayout? = null
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_how_play)

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс Contract.View
        mPresenter = ChooseHowPlayPresenter(this)

        listName = intent.getStringArrayListExtra("listName") as MutableList<String>
        listColor = intent.getIntegerArrayListExtra("listColor") as MutableList<Int>
        listWords = intent.getStringArrayListExtra("listWords") as MutableList<String>

        initViews()

        mPresenter!!.findDataForFillFields(listName!!, listColor!!, listWords!!)
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
        iconShow = findViewById(R.id.iconShow)
        iconTell = findViewById(R.id.iconTell)
        iconDraw = findViewById(R.id.icon_draw)
        textDraw = findViewById(R.id.text_draw)
        textShow = findViewById(R.id.text_show)
        textTell = findViewById(R.id.text_tell)
        buttonGo = findViewById(R.id.buttonGo)
        word1!!.setOnClickListener {
            mPresenter!!.word1Pressed(word1!!.text.toString())
            word1!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            word2!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion))
            frameWord1!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            frameWord2!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = frameWord1!!.foreground as GradientDrawable
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer))
        }
        word2!!.setOnClickListener {
            mPresenter!!.word1Pressed(word1!!.text.toString())
            word2!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            word1!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion))
            frameWord2!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            frameWord1!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = frameWord2!!.foreground as GradientDrawable
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer))
        }
        layoutShow!!.setOnClickListener {
            mPresenter!!.layoutShow_Pressed()
            textShow!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            textTell!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion))
            textDraw!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconDraw!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconTell!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconShow!!.setColorFilter(ContextCompat.getColor(this, colorPlayer))
            layoutShow!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            layoutTell!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            layoutDraw!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = layoutShow!!.foreground as GradientDrawable
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer))
        }
        layoutTell!!.setOnClickListener {
            mPresenter!!.layoutTell_Pressed()
            textShow!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion))
            textTell!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            textDraw!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconDraw!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconTell!!.setColorFilter(ContextCompat.getColor(this, colorPlayer))
            iconShow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            layoutShow!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            layoutTell!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            layoutDraw!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            val gd = layoutTell!!.foreground as GradientDrawable
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer))
        }
        layoutDraw!!.setOnClickListener {
            mPresenter!!.layoutDraw_Pressed()
            //color text
            textShow!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion))
            textTell!!.setTextColor(ContextCompat.getColor(this, R.color.colorTextSelextion))
            textDraw!!.setTextColor(ContextCompat.getColor(this, colorPlayer))
            //color icon
            iconDraw!!.setColorFilter(ContextCompat.getColor(this, colorPlayer))
            iconTell!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconShow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            //background
            layoutShow!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            layoutTell!!.foreground = ContextCompat.getDrawable(this, R.drawable.recycler_backgroind)
            layoutDraw!!.foreground = ContextCompat.getDrawable(this, R.drawable.selected_action_and_word)
            //change color frame
            val gd = layoutDraw!!.foreground as GradientDrawable
            gd.setStroke(1, ContextCompat.getColor(this, colorPlayer))
        }
        buttonGo!!.setOnClickListener { mPresenter!!.buttonGo() }
    }

    override fun contextActivity(): Context {
        return this
    }

    override fun showResultDialog() {

    }

    override fun showData(name: String, color: Int, wordOne: String, wordTwo: String) {
        whoseTurn!!.text = String.format("%s %s", resources.getString(R.string.turn), name)
        whoseTurn!!.setTextColor(ContextCompat.getColor(this, color))
        word1!!.text = wordOne
        word2!!.text = wordTwo

        colorPlayer = color
    }

}
