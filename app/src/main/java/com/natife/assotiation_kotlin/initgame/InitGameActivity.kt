package com.natife.assotiation_kotlin.initgame

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.*
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.R.id.*
import java.util.*

class InitGameActivity : AppCompatActivity(), InitGameContract.View {

    private lateinit var recyclerPlayers: RecyclerView
    private var back: ImageView? = null
    private var settings: ImageView? = null
    private var btnAddPlayer: RelativeLayout? = null
    private var btnNext: RelativeLayout? = null
    private var textBtnNext: TextView? = null
    private lateinit var adapterPlayers: PlayersAdapter
    private var viewRadioButton: View? = null
    private var textSelection: TextView? = null
    private var mPresenter: InitGameContract.Presenter? = null
    private lateinit var onItemVoiceIconListener: OnItemVoiceIconListener
    private lateinit var nameForVoiceTemp: EditText
    private val VOICE_RECOGNIZER: Int = 1000
    private var radio_easy: RadioButton? = null
    private var radio_normal: RadioButton? = null
    private var radio_hard: RadioButton? = null
    private val LEVEL_EASE = 1
    private val LEVEL_NORMAL = 2
    private val LEVEL_HARD = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс InitGameContract.View
        mPresenter = InitGamePresenter(this)

        initView()

        recyclerPlayers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapterPlayers = PlayersAdapter(this@InitGameActivity, onItemVoiceIconListener)
        recyclerPlayers.adapter = adapterPlayers

        val callback = object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val swipedPosition = viewHolder.adapterPosition

                if (adapterPlayers.itemCount > 3) {
                    adapterPlayers.deleteFromListAdapter(swipedPosition)
                }
            }

            override fun getSwipeDirs(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder): Int {
                return if (adapterPlayers.itemCount > 3) super.getSwipeDirs(recyclerView, holder) else 0
            }
        }
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerPlayers)

        mPresenter!!.initPlayerList()
    }//onCreate


    private fun initView() {
        recyclerPlayers = findViewById(R.id.recyclerViewListPlayer)
        textSelection = findViewById(R.id.textSelection)
        back = findViewById(R.id.back)
        settings = findViewById(R.id.settings)
        btnAddPlayer = findViewById(R.id.buttonAddPlayer)
        btnNext = findViewById(R.id.buttonNext)
        textBtnNext = findViewById(R.id.textBtnNext)
        viewRadioButton = findViewById(R.id.viewRadioButton)
        radio_easy = findViewById(R.id.radio_easy)
        radio_normal = findViewById(R.id.radio_normal)
        radio_hard = findViewById(R.id.radio_hard)

        btnAddPlayer!!.setOnClickListener { mPresenter!!.btnAddPlayerClicked() }
        btnNext!!.setOnClickListener { mPresenter!!.btnNextClicked(checkDifficultLevel()) }
        back!!.setOnClickListener { mPresenter!!.btnBackClicked() }
        settings!!.setOnClickListener { mPresenter!!.btnSettingsClicked() }
        onItemVoiceIconListener = object : OnItemVoiceIconListener {
            override fun onItemVoiceIconClick(position: Int, editText: EditText) {
                // call the voice dialing activity
                nameForVoiceTemp = editText
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault())
                try {
                    startActivityForResult(intent, VOICE_RECOGNIZER)
                } catch (a: ActivityNotFoundException) {
                    Toast.makeText(this@InitGameActivity,
                            R.string.error_voice_not_support, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }//initView

    private fun checkDifficultLevel(): Int {
        var levelDifficult = LEVEL_EASE
        if (radio_easy!!.isChecked()) levelDifficult = LEVEL_EASE
        if (radio_normal!!.isChecked()) levelDifficult = LEVEL_NORMAL
        if (radio_hard!!.isChecked()) levelDifficult = LEVEL_HARD
        return levelDifficult
    }

    override fun showListPlayers(listName: MutableList<String>, listColor: MutableList<Int>) {
        adapterPlayers.setData(listName, listColor)
    }


    override fun changeScreen(flagChange: Boolean) {
        recyclerPlayers.visibility = if (flagChange) View.GONE else View.VISIBLE
        viewRadioButton!!.visibility = if (flagChange) View.VISIBLE else View.GONE
        back!!.visibility = if (flagChange) View.VISIBLE else View.GONE
        settings!!.setImageResource(if (flagChange) R.drawable.ic_settings_black_24dp else R.drawable.ic_error_outline_black_24dp)
        btnAddPlayer!!.visibility = if (flagChange) View.GONE else View.VISIBLE
        textBtnNext!!.setText(if (flagChange) R.string.text_play else R.string.text_next)
        textSelection!!.setText(if (flagChange) R.string.text_selection_difficulty_level else R.string.text_selection_name)
    }

    override fun contextActivity(): Context {
        return this
    }

    companion object {

        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, InitGameActivity::class.java))
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            VOICE_RECOGNIZER -> {
                // result of voice dialing
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val yourResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)[0]
                    nameForVoiceTemp.setText(yourResult)
                }
            }
        }
    }
}