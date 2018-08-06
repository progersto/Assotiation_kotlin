package com.natife.assotiation_kotlin.choose_how_play

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.DialogFragment
import android.text.TextUtils.substring
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.initgame.InitGameActivity
import com.natife.assotiation_kotlin.initgame.Player
import java.util.ArrayList

class DialogResult : DialogFragment() {
    internal var timeMoveTV: TextView? = null
    internal var timeGameTV: TextView? = null
    internal var numberCirclesTV: TextView? = null
    internal var timeMove: Int = 0
    internal var timeGame: Int = 0
    internal var numberCircles: Int = 0
    private var playerList: List<Player>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ddd", "fff")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val v = inflater.inflate(R.layout.dialog_result, null)
        val buttonAgain: RelativeLayout = v.findViewById(R.id.buttonAgain)
        buttonAgain.setOnClickListener {
            activity!!.finishAffinity()
            dismiss()
            val intent = Intent(context, InitGameActivity::class.java)
            intent.putParcelableArrayListExtra("playerList", playerList as ArrayList<out Parcelable>)
            startActivity(intent)
        }

        playerList = arguments!!.getParcelableArrayList("playerList")

        val layoutResult: LinearLayout = v.findViewById(R.id.layoutResult)//контейнер для вставки item
        for (i in playerList!!.indices) {
            val newItem: View = inflater.inflate(R.layout.item_result, null)//добавляемый item
            val image: ImageView = newItem.findViewById(R.id.image_result)
            val nameResult: TextView = newItem.findViewById(R.id.name_result)//inserted name
            val totalPointsResult: TextView = newItem.findViewById(R.id.total_points)
            val guessedWordsResult: TextView = newItem.findViewById(R.id.guessed_words)
            val name = playerList!![i].name!!.substring(0, 1).toUpperCase() + playerList!![i].name!!.substring(1)
            nameResult.text = name
            layoutResult.addView(newItem)
        }
        return v
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}
