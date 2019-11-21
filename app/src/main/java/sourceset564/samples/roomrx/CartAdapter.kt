package sourceset564.samples.roomrx

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_cart.view.*
import sourceset564.samples.roomrx.data.CartEntity
import java.text.DecimalFormat

/**
 * Created by Anwar on 11/20/2019.
 */
class CartAdapter : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    private val mList = mutableListOf<CartEntity>()
    private val decimalFormat = DecimalFormat("#,###")
    var itemActionListener : OnItemClickListener? = null

    fun updateAdapter(list : List<CartEntity>){
        val callback = DiffCartCallback(mList,list)
        val result = DiffUtil.calculateDiff(callback)
        mList.clear()
        mList.addAll(list)
        result.dispatchUpdatesTo(this)
    }

    fun clear(){
        mList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart,parent,false)

        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val cart = mList[position]
        holder.itemView.run {
            textName.setText(cart.name)

            val itemQty = context.resources.getString(R.string.item_qty,cart.qty)
            textQty.setText(itemQty)

            textPrice.setText(decimalFormat.format(cart.price))
            textTotal.setText(decimalFormat.format(cart.getTotalPrice()))
            setOnClickListener { itemActionListener?.onClick(cart) }
            cartRemove.setOnClickListener {
                itemActionListener?.onRemove(cart)}
        }
    }

    interface OnItemClickListener{
        fun onClick(cart : CartEntity)
        fun onRemove(cart : CartEntity)
    }

    inner class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    inner class DiffCartCallback(
        val oldList : List<CartEntity>,
        val newList : List<CartEntity>) : DiffUtil.Callback(){

        override fun areItemsTheSame(
            oldPosition: Int,
            newPosition: Int): Boolean = oldList[oldPosition].id== newList[newPosition].id

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(
            oldPosition: Int,
            newPosition: Int): Boolean = oldList[oldPosition] == newList[newPosition]
    }
}