package sourceset564.samples.roomrx

import sourceset564.samples.roomrx.data.CartEntity

/**
 * Created by Anwar on 11/20/2019.
 */
interface MainView{

    fun setPresenter(presenter : MainPresenter)

    fun showCartItem(list : List<CartEntity>)

    fun showEmptyMessage()

    fun showEmptyMessage(message : String)

    fun hideEmptyMessage()

    fun showToastMessage(message: String)
}