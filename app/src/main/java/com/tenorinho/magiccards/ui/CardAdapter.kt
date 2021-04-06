package com.tenorinho.magiccards.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tenorinho.magiccards.R
import com.tenorinho.magiccards.models.domain.Card

class CardAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var cards:ArrayList<Card> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as CardViewHolder
        h.bind(cards[position])
    }
    override fun getItemCount(): Int = cards.size
    fun updateList(listCards:ArrayList<Card>){
        cards = listCards
        notifyDataSetChanged()
    }
    private class CardViewHolder:RecyclerView.ViewHolder{
        val imgCard:ImageView
        val txtName:TextView
        val txtTypeline:TextView
        constructor(v: View):super(v){
            imgCard = v.findViewById(R.id.item_card_img)
            txtName = v.findViewById(R.id.item_card_txt_name)
            txtTypeline = v.findViewById(R.id.item_card_txt_type_line)
        }
        fun bind(card:Card?){
            if(card != null) {
                if (card.isTwoFaced) {
                    val cardFace = card.card_faces?.get(0)
                    if(cardFace != null && cardFace.image_uris != null){
                        Log.v("TAG", "bind: CardFace = NOT NULL")
                        Glide.with(imgCard.context).load(cardFace.image_uris.normal).into(imgCard)
                    }
                    else{
                        Log.v("TAG", "bind: CardFace = NULL")
                        imgCard.setImageResource(R.drawable.ic_no_card_image)
                        imgCard.setBackgroundColor(imgCard.context.resources.getColor(R.color.lightGray))
                    }
                }
                else {
                    if(card.image_uris != null && !card.image_uris.normal.isNullOrBlank()){
                        Log.v("TAG", "bind: ImageUris = NOT NULL")
                        Glide.with(imgCard.context).load(card.image_uris.normal).into(imgCard)
                    }
                    else{
                        Log.v("TAG", "bind: ImageUris = NULL")
                        imgCard.setImageResource(R.drawable.ic_no_card_image)
                        imgCard.setBackgroundColor(imgCard.context.resources.getColor(R.color.lightGray))
                    }
                }
                txtName.text = card.name ?: "UNKNOWN"
                txtTypeline.text = card.type_line ?: "UNKNOWN"
            }
            else{
                imgCard.setImageResource(R.drawable.ic_no_card_image)
                txtName.text = "UNKNOWN"
                txtTypeline.text = "UNKNOWN"
            }
        }
    }
}