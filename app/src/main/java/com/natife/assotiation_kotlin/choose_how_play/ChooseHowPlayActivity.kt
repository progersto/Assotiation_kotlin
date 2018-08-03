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

class ChooseHowPlayActivity() : AppCompatActivity(), ChooseHowPlayContract.View {

    private var mPresenter: ChooseHowPlayContract.Presenter? = null
    private var listName: MutableList<String>? = null
    private var listColor: MutableList<Int>? = null
    private var listWords: MutableList<String>? = null
    private var whoseTurn: TextView? = null
    private var results: ImageView? = null
    private var textSelection: TextView? = null
    private var frame_word1: FrameLayout? = null
    private var frame_word2: FrameLayout? = null
    private var word1: TextView? = null
    private var word2: TextView? = null
    private var layout_show: FrameLayout? = null
    private var layout_tell: FrameLayout? = null
    private var layout_draw: FrameLayout? = null
    private var iconShow: ImageView? = null
    private var iconTell: ImageView? = null
    private var iconDraw: ImageView? = null
    private var text_draw: TextView? = null
    private var text_show: TextView? = null
    private var text_tell: TextView? = null
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

        initViews();

        mPresenter!!.findDataForFillFields(listName!!, listColor!!, listWords!!)
    }

    private fun initViews() {
        whoseTurn = findViewById<TextView>(R.id.whose_turn)
        results = findViewById<ImageView>(R.id.results)
        textSelection = findViewById<TextView>(R.id.textSelection)
        frame_word1 = findViewById<FrameLayout>(R.id.frame_word1)
        frame_word2 = findViewById<FrameLayout>(R.id.frame_word2)
        word1 = findViewById<TextView>(R.id.word1)
        word2 = findViewById<TextView>(R.id.word2)
        layout_show = findViewById<FrameLayout>(R.id.layout_show)
        layout_tell = findViewById<FrameLayout>(R.id.layout_tell)
        layout_draw = findViewById<FrameLayout>(R.id.layout_draw)
        iconShow = findViewById<ImageView>(R.id.iconShow)
        iconTell = findViewById<ImageView>(R.id.iconTell)
        iconDraw = findViewById<ImageView>(R.id.icon_draw)
        text_draw = findViewById<TextView>(R.id.text_draw)
        text_show = findViewById<TextView>(R.id.text_show)
        text_tell = findViewById<TextView>(R.id.text_tell)
        buttonGo = findViewById<RelativeLayout>(R.id.buttonGo)
        word1!!.setOnClickListener {
            mPresenter!!.word1Pressed(word1!!.getText().toString())
            word1!!.setTextColor(resources.getColor(colorPlayer))
            word2!!.setTextColor(resources.getColor(R.color.colorTextSelextion))
            frame_word1!!.setForeground(resources.getDrawable(R.drawable.selected_action_and_word))
            frame_word2!!.setForeground(resources.getDrawable(R.drawable.recycler_backgroind))
            val gd = frame_word1!!.getForeground() as GradientDrawable
            gd.setStroke(1, resources.getColor(colorPlayer))
        }
        word2!!.setOnClickListener {
            mPresenter!!.word1Pressed(word1!!.getText().toString())
            word2!!.setTextColor(resources.getColor(colorPlayer))
            word1!!.setTextColor(resources.getColor(R.color.colorTextSelextion))
            frame_word2!!.setForeground(resources.getDrawable(R.drawable.selected_action_and_word))
            frame_word1!!.setForeground(resources.getDrawable(R.drawable.recycler_backgroind))
            val gd = frame_word2!!.getForeground() as GradientDrawable
            gd.setStroke(1, resources.getColor(colorPlayer))
        }
        layout_show!!.setOnClickListener {
            mPresenter!!.layoutShow_Pressed()
            text_show!!.setTextColor(resources.getColor(colorPlayer))
            text_tell!!.setTextColor(resources.getColor(R.color.colorTextSelextion))
            text_draw!!.setTextColor(resources.getColor(R.color.colorTextSelextion))
            iconDraw!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconTell!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconShow!!.setColorFilter(ContextCompat.getColor(this, colorPlayer))
            layout_show!!.setForeground(resources.getDrawable(R.drawable.selected_action_and_word))
            layout_tell!!.setForeground(resources.getDrawable(R.drawable.recycler_backgroind))
            layout_draw!!.setForeground(resources.getDrawable(R.drawable.recycler_backgroind))
            val gd = layout_show!!.getForeground() as GradientDrawable
            gd.setStroke(1, resources.getColor(colorPlayer))
        }
        layout_tell!!.setOnClickListener {
            mPresenter!!.layoutTell_Pressed()
            text_show!!.setTextColor(resources.getColor(R.color.colorTextSelextion))
            text_tell!!.setTextColor(resources.getColor(colorPlayer))
            text_draw!!.setTextColor(resources.getColor(R.color.colorTextSelextion))
            iconDraw!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconTell!!.setColorFilter(ContextCompat.getColor(this, colorPlayer))
            iconShow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            layout_show!!.setForeground(resources.getDrawable(R.drawable.recycler_backgroind))
            layout_tell!!.setForeground(resources.getDrawable(R.drawable.selected_action_and_word))
            layout_draw!!.setForeground(resources.getDrawable(R.drawable.recycler_backgroind))
            val gd = layout_tell!!.getForeground() as GradientDrawable
            gd.setStroke(1, resources.getColor(colorPlayer))
        }
        layout_draw!!.setOnClickListener {
            mPresenter!!.layoutDraw_Pressed()
            //color text
            text_show!!.setTextColor(resources.getColor(R.color.colorTextSelextion))
            text_tell!!.setTextColor(resources.getColor(R.color.colorTextSelextion))
            text_draw!!.setTextColor(resources.getColor(colorPlayer))
            //color icon
            iconDraw!!.setColorFilter(ContextCompat.getColor(this, colorPlayer))
            iconTell!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            iconShow!!.setColorFilter(ContextCompat.getColor(this, R.color.colorTextSelextion))
            //background
            layout_show!!.setForeground(resources.getDrawable(R.drawable.recycler_backgroind))
            layout_tell!!.setForeground(resources.getDrawable(R.drawable.recycler_backgroind))
            layout_draw!!.setForeground(resources.getDrawable(R.drawable.selected_action_and_word))
            //change color frame
            val gd = layout_draw!!.getForeground() as GradientDrawable
            gd.setStroke(1, resources.getColor(colorPlayer))
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
        whoseTurn!!.setTextColor(resources.getColor(color))
        word1!!.text = wordOne
        word2!!.text = wordTwo

        colorPlayer = color
    }

}
