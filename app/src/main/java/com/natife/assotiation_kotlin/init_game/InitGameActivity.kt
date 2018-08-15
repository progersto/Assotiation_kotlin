package com.natife.assotiation_kotlin.init_game

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.SoundEffectConstants
import android.view.View
import android.view.Window
import android.widget.*
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.utils.audio.AudioUtil
import java.util.*

class InitGameActivity : AppCompatActivity(), InitGameContract.View {

    private lateinit var recyclerPlayers: RecyclerView
    private lateinit var back: ImageView
    private lateinit var settings: ImageView
    private lateinit var btnAddPlayer: RelativeLayout
    private lateinit var btnNext: RelativeLayout
    private lateinit var textBtnNext: TextView
    private lateinit var adapterPlayers: PlayersAdapter
    private lateinit var viewRadioButton: View
    private lateinit var textSelection: TextView
    private lateinit var mPresenter: InitGameContract.Presenter
    private lateinit var onItemVoiceIconListener: OnItemVoiceIconListener
    private lateinit var nameForVoiceTemp: EditText
    private val VOICE_RECOGNIZER: Int = 1000
    private lateinit var radioEasy: RadioButton
    private lateinit var radioNormal: RadioButton
    private lateinit var radioHard: RadioButton
    private val LEVEL_EASE = 1
    private val LEVEL_NORMAL = 2
    private val LEVEL_HARD = 3
    private lateinit var audio: AudioUtil
    private lateinit var audioManager: AudioManager

    companion object {

        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, InitGameActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initgame)

        audioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
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

        val playerList = intent.getParcelableArrayListExtra<Player>("playerList")
        (mPresenter as InitGamePresenter).initPlayerList(playerList)
        audio = AudioUtil.getInstance()
        volumeControlStream = AudioManager.STREAM_MUSIC//volume on the volumeButton
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
        radioEasy = findViewById(R.id.radio_easy)
        radioEasy.setOnClickListener{ audio.soundClick(this)}
        radioNormal = findViewById(R.id.radio_normal)
        radioNormal.setOnClickListener{ audio.soundClick(this) }
        radioHard = findViewById(R.id.radio_hard)
        radioHard.setOnClickListener{ audio.soundClick(this)}
        btnAddPlayer.setOnClickListener {
            audio.soundClick(this)
            mPresenter.btnAddPlayerClicked() }
        btnNext.setOnClickListener {
            audio.soundClick(this)
            mPresenter.btnNextClicked(checkDifficultLevel()) }
        back.setOnClickListener {
            audio.soundClick(this)
            mPresenter.btnBackClicked() }
        settings.setOnClickListener {
            audio.soundClick(this)
            mPresenter.btnSettingsClicked() }
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
        if (radioEasy.isChecked) levelDifficult = LEVEL_EASE
        if (radioNormal.isChecked) levelDifficult = LEVEL_NORMAL
        if (radioHard.isChecked) levelDifficult = LEVEL_HARD
        return levelDifficult
    }

    override fun showListPlayers(listPlayer: MutableList<Player>) {
        adapterPlayers.setData(listPlayer)
    }


    override fun changeScreen(flagChange: Boolean) {
        recyclerPlayers.visibility = if (flagChange) View.GONE else View.VISIBLE
        viewRadioButton.visibility = if (flagChange) View.VISIBLE else View.GONE
        back.visibility = if (flagChange) View.VISIBLE else View.GONE
        settings.setImageResource(if (flagChange) R.drawable.ic_settings_black_24dp else R.drawable.ic_error_outline_black_24dp)
        btnAddPlayer.visibility = if (flagChange) View.GONE else View.VISIBLE
        textBtnNext.setText(if (flagChange) R.string.text_play else R.string.text_next)
        textSelection.setText(if (flagChange) R.string.text_selection_difficulty_level else R.string.text_selection_name)
    }

    override fun contextActivity(): Context {
        return this
    }

    override fun showSettingsDialog(flagStartGame: Boolean) {
        if (flagStartGame) {
            val dialogSettings = DialogSettings()
            dialogSettings.show(supportFragmentManager, "dialogSettings")
        } else {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.dialog_inform)
            dialog.show()
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

    override fun onBackPressed() {
        if (mPresenter.getFlagChangeScreen()) {
            return
        }else
        super.onBackPressed()
    }
}