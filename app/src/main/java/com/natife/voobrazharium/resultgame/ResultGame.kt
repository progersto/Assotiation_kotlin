package com.natife.voobrazharium.resultgame

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.media.SoundPool
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.StrictMode
import android.view.View
import android.view.View.inflate
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.natife.voobrazharium.R
import com.natife.voobrazharium.init_game.InitGameActivity
import com.natife.voobrazharium.init_game.Player
import java.util.ArrayList
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.natife.voobrazharium.utils.audio.AudioUtil
import java.io.File
import java.io.FileOutputStream

class ResultGame : AppCompatActivity() {
    private lateinit var playerList: List<Player>
    private lateinit var localPayerList: MutableList<Player>
    private lateinit var toolbar: Toolbar
    private var timeGameFlag: Boolean = true
    private lateinit var layoutResult: LinearLayout
    private var dialog: Dialog? = null
    private lateinit var sp: SoundPool
    private var soundIdShot: Int = 1
    private lateinit var audio: AudioUtil
    private lateinit var mAdView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewResult = layoutInflater.inflate(R.layout.activity_result, null)
        layoutResult = viewResult.findViewById(R.id.layoutResult)//контейнер для вставки item
        toolbar = viewResult.findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        audio = AudioUtil.getInstance()
        volumeControlStream = AudioManager.STREAM_MUSIC//volume on the volumeButton

        timeGameFlag = intent.getBooleanExtra("timeGameFlag", false)
        playerList = intent.getParcelableArrayListExtra("playerList")
        localPayerList = ArrayList(playerList)

        val btnBack: ImageView = viewResult.findViewById(R.id.back)
        btnBack.isSoundEffectsEnabled= false
        btnBack.setOnClickListener {
            audio.soundClickPlayer(this)
           // audio.soundClick(this)
            setResult(RESULT_OK, intent.putExtra("flagNextPlayer", true))
            this.finish()
        }
        btnBack.visibility = if (timeGameFlag) View.VISIBLE else View.INVISIBLE

        val buttonAgain: RelativeLayout = viewResult.findViewById(R.id.buttonAgain)
        buttonAgain.isSoundEffectsEnabled= false
        buttonAgain.setOnClickListener {
           // audio.soundClick(this)
            audio.soundClickPlayer(this)
            android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage(R.string.you_are_sure)
                    .setNegativeButton(R.string.no) { dialog, _ ->
                        audio.soundClickPlayer(this)
                      //  audio.soundClick(this)
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.ok) { dialog, _ ->
                        audio.soundClickPlayer(this)
                       // audio.soundClick(this)
                        dialog.dismiss()
                        finishAffinity()
                        val intent = Intent(this, InitGameActivity::class.java)
                        intent.putParcelableArrayListExtra("playerList", playerList as ArrayList<out Parcelable>)
                        startActivity(intent)
                    }
                    .show()
        }

        (localPayerList as ArrayList<Player>).sortWith(Comparator { player, t1 ->
            when {
                player.countScore == t1.countScore -> 0
                player.countScore < t1.countScore -> 1
                else -> -1
            }
        })

        val layoutResult: LinearLayout = viewResult.findViewById(R.id.layoutResult)//контейнер для вставки item

        val isWin = checkWin()
        for (i in localPayerList.indices) {
            val newItem = inflate(this, R.layout.item_result, null)//добавляемый item
            val image = newItem.findViewById<ImageView>(R.id.image_result)
            val nameResult = newItem.findViewById<TextView>(R.id.name_result)//inserted name
            val totalPointsResult = newItem.findViewById<TextView>(R.id.total_points)
            val guessedWordsResult = newItem.findViewById<TextView>(R.id.guessed_words)

            val name = localPayerList[i].name!!.substring(0, 1).toUpperCase() + localPayerList[i].name!!.substring(1)
            nameResult.text = name
            val guessedWords = String.format("%s %s %s",
                    resources.getString(R.string.guessed),
                    localPayerList[i].countWords,
                    resources.getString(R.string.words))
            guessedWordsResult.text = guessedWords
            totalPointsResult.text = localPayerList[i].countScore.toString()
            if (isWin && i == 0) {
                image.visibility = View.VISIBLE
                if (!timeGameFlag){
                    audio.soundApplausePlayer(this)
                    //audio.soundApplause(this)
                    dialog = Dialog(this)
                    dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog!!.setContentView(R.layout.dialog_win)
                    val nameWiner: TextView = dialog!!.findViewById(R.id.name_win)
                    nameWiner.text = name
                    dialog!!.show()
                }
            } else
                image.visibility = View.INVISIBLE
            layoutResult.addView(newItem)

            if (!isWin && !timeGameFlag && i == 0){
                dialog = Dialog(this)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.setContentView(R.layout.dialog_draw)
                dialog!!.show()
            }
        }
        setContentView(viewResult)

        starAdvertising()
    }

    private fun starAdvertising() {
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                mAdView.visibility = View.VISIBLE
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

    override fun onBackPressed() {
        if (timeGameFlag) {
            super.onBackPressed()
        }
    }

    private fun checkWin(): Boolean {
        var flag = true
        if (localPayerList.get(index = 0).countScore == localPayerList.get(index = 1).countScore) {
            flag = false
        }
        return flag
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> {
                audio.soundClickPlayer(this)
//                audio.soundClick(this)
                val bitmap = getBitmapFromView(layoutResult)
                val builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())
                startShare(bitmap)
            }
        }
        return true
    }

    private fun startShare(bitmap: Bitmap) {
        try {
            val file = File(this.externalCacheDir, "logicchip.png")
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)

            val intent = Intent(android.content.Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.share_text))
            intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_URI))
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "Share image via"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_result, menu)
        return true
    }


    private fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }

}
