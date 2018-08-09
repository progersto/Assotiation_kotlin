package com.natife.assotiation_kotlin.utils

import android.content.Context
import android.preference.PreferenceManager

var TIME_MOVE_KEY = "timeMove"
var TIME_GAME_KEY = "timeGame"
var NUMBER_CIRCLE_KEY = "numberCircles"
var COLOR_DRAW_KEY = "numberCircles"

fun saveTimeMove(context: Context, value: Int) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = prefs.edit()
    editor.putInt(TIME_MOVE_KEY, value)
    editor.apply()
}

fun restoreTimeMove(context: Context): Int {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    return prefs.getInt(TIME_MOVE_KEY, 0)
}

fun saveTimeGame(context: Context, value: Int) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = prefs.edit()
    editor.putInt(TIME_GAME_KEY, value)
    editor.apply()
}

fun restoreTimeGame(context: Context): Int {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    return prefs.getInt(TIME_GAME_KEY, 0)
}

fun saveNumberCircles(context: Context, value: Int) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = prefs.edit()
    editor.putInt(NUMBER_CIRCLE_KEY, value)
    editor.apply()
}

fun restoreNumberCircles(context: Context): Int {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    return prefs.getInt(NUMBER_CIRCLE_KEY, 0)
}

fun saveColorDraw(context: Context, value: Int) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = prefs.edit()
    editor.putInt(COLOR_DRAW_KEY, value)
    editor.apply()
}

fun restoreColorDraw(context: Context): Int {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    return prefs.getInt(COLOR_DRAW_KEY, 0)
}