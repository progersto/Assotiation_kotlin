package com.natife.assotiation_kotlin.initgame

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.natife.assotiation_kotlin.R
import com.natife.assotiation_kotlin.utils.*

class DialogSettings : DialogFragment() {
    internal var timeMoveTV: TextView? = null
    internal var timeGameTV: TextView? = null
    internal var numberCirclesTV: TextView? = null
    internal var timeMove: Int = 0
    internal var timeGame: Int = 0
    internal var numberCircles: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_settings_game, null)

        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        timeMoveTV = v.findViewById(R.id.text_time_move)
        timeGameTV = v.findViewById(R.id.text_time_game)
        numberCirclesTV = v.findViewById(R.id.text_number_of_circles)

        //get info from preferences
        timeMove = restoreTimeMove(v.getContext())
        timeGame = restoreTimeGame(v.getContext())
        numberCircles = restoreNumberCircles(v.getContext())

        //for first init
        if (timeMove == 0 || timeGame == 0 || numberCircles == 0) {
            timeMove = Integer.parseInt(timeMoveTV!!.text.toString())
            timeGame = Integer.parseInt(timeGameTV!!.text.toString())
            numberCircles = Integer.parseInt(numberCirclesTV!!.text.toString())
            saveTimeMove(v.getContext(), timeMove)
            saveTimeGame(v.getContext(), timeGame)
            saveNumberCircles(v.getContext(), numberCircles)
        } else {
            timeMoveTV!!.text = timeMove.toString()
            timeGameTV!!.text = timeGame.toString()
            numberCirclesTV!!.text = numberCircles.toString()
        }

        v.findViewById<View>(R.id.number_of_circles_minus).setOnClickListener {
            if (timeMove != 15) {
                timeMove -= 15
                timeMoveTV!!.text = timeMove.toString()
            }
        }
        v.findViewById<View>(R.id.time_move_plus).setOnClickListener {
            if (timeMove != 300) {
                timeMove += 15
                timeMoveTV!!.text = timeMove.toString()
            }
        }
        v.findViewById<View>(R.id.time_game_minus).setOnClickListener {
            if (timeGame != 15) {
                timeGame -= 1
                timeGameTV!!.text = timeGame.toString()
            }
        }
        v.findViewById<View>(R.id.time_game_plus).setOnClickListener {
            if (timeGame != 90) {
                timeGame += 1
                timeGameTV!!.text = timeGame.toString()
            }
        }
        v.findViewById<View>(R.id.number_of_circles_plus).setOnClickListener {
            if (numberCircles != 50) {
                numberCircles += 1
                numberCirclesTV!!.text = numberCircles.toString()
            }
        }
        v.findViewById<View>(R.id.number_of_circles_minus).setOnClickListener {
            if (numberCircles != 1) {
                numberCircles -= 1
                numberCirclesTV!!.text = numberCircles.toString()
            }
        }
        v.findViewById<View>(R.id.buttonSave).setOnClickListener {
            saveTimeMove(v.getContext(), timeMove)
            saveTimeGame(v.getContext(), timeGame)
            saveNumberCircles(v.getContext(), numberCircles)
            dismiss()
        }

        return v
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}
