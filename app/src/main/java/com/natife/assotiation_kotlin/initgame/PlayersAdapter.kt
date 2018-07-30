package com.natife.assotiation_kotlin.initgame

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.natife.assotiation_kotlin.R

import java.util.ArrayList

class PlayersAdapter(private val context: Context, list: List<String>, listColor: List<Int>) : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private val list: MutableList<String>
    private val listColor: List<Int>

    init {
        this.inflater = LayoutInflater.from(context)
        this.list = ArrayList(list)
        this.listColor = ArrayList(listColor)
    }//AdapterProductList

    override fun getItemCount(): Int {
        return list.size
    }//getItemCount

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }//getItemId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_player, parent, false)
        return ViewHolder(view)
    } // onCreateViewHolder

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
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
        holder.editTextPlayerName.hint = list[position]
        holder.imageColor.setColorFilter(ContextCompat.getColor(context, listColor[position]))

        val listener = View.OnClickListener { }
        holder.imageVoice.setOnClickListener(listener)

    }//onBindViewHolder

    fun deleteFromListAdapter(pos: Int) {
        list.removeAt(pos)
        notifyItemRemoved(pos)//updates after removing Item at position
        notifyItemRangeChanged(pos, list.size)//updates the items of the following items
    }//deleteFromListAdapter
}//class AdapterProductList