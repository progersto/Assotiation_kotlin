package com.natife.assotiation_kotlin.initgame

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.natife.assotiation_kotlin.R

import java.util.ArrayList

class PlayersAdapter(private val context: Context) : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private var list: MutableList<String> = ArrayList()
    private var listColor: List<Int> = ArrayList()


    init {
        this.inflater = LayoutInflater.from(context)
    }//AdapterProductList

    override fun getItemCount(): Int {
        return list.size
    }//getItemCount

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }//getItemId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_player, parent, false)
        val holder = ViewHolder(view)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                list[holder.adapterPosition] = charSequence.toString()
                Log.d("ddd", "onTextChanged list = $list")
            }

            override fun afterTextChanged(editable: Editable) {}
        }

        holder.editTextPlayerName.addTextChangedListener(textWatcher)

        return holder
    } // onCreateViewHolder

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        internal var constraint_item_player: ConstraintLayout
        internal var imageColor: ImageView
        internal var editTextPlayerName: TextView
        internal var imageVoice: RelativeLayout

        init {
            constraint_item_player = view.findViewById(R.id.item_player_constraint)
            imageColor = view.findViewById(R.id.imageColor)
            imageVoice = view.findViewById(R.id.imageVoice)
            editTextPlayerName = view.findViewById(R.id.editTextPlayerName)
        }//ViewHolder
    }//class ViewHolder


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("ddd", "onBindViewHolder list = $list")

        holder.editTextPlayerName.hint = context.resources.getString(R.string.name_player) + " " + (position + 1)
        holder.editTextPlayerName.text = list[position]

        holder.imageColor.setColorFilter(ContextCompat.getColor(context, listColor[position]))

        val listener = View.OnClickListener { }
        holder.imageVoice.setOnClickListener(listener)
    }//onBindViewHolder

    override fun onViewRecycled(holder: ViewHolder) {

    }

    fun setData(list: MutableList<String>, listColor: List<Int>) {
        Log.d("ddd", "setData this.list = " + this.list)
        Log.d("ddd", "setData list = $list")
        this.list = list
        this.listColor = listColor
        notifyDataSetChanged()
    }


    fun deleteFromListAdapter(position: Int) {
        list.removeAt(position)

        Log.d("ddd", "deleteFromListAdapter list = $list")
        notifyItemRemoved(position)//updates after removing Item at position
        notifyDataSetChanged()
    }//deleteFromListAdapter

    companion object {
        private val PENDING_REMOVAL_TIMEOUT = 3000 // 3sec
    }

}//class AdapterProductList