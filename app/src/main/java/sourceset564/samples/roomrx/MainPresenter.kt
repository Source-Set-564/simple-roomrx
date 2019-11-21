package sourceset564.samples.roomrx

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import sourceset564.samples.roomrx.data.CartDatabase
import sourceset564.samples.roomrx.data.CartEntity
import sourceset564.samples.roomrx.data.FoodCartDao

/**
 * Created by Anwar on 11/20/2019.
 */
class MainPresenter constructor(val view : MainView, context : Context) {

    private val cartDao : FoodCartDao
    private var mDisposable = CompositeDisposable()

    init {
        view.setPresenter(this)
        cartDao = CartDatabase.getInstance(context).foodCartDao()
    }

    fun start(){
        cartDao.getAllCart()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.isNotEmpty()){
                    view.hideEmptyMessage()
                    view.showCartItem(it)
                }else{
                    view.showEmptyMessage()
                }
            },{
                it.message?.also {
                    view.showEmptyMessage(it)
                } ?: view.showEmptyMessage()
            }).also {
                mDisposable.add(it)
            }
    }

    fun addToCart(cart : CartEntity){
        cartDao.insert(cart)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showToastMessage("Add cart successfully")
            },{
                it.message?.also {
                    view.showToastMessage(it)
                } ?: view.showToastMessage("Error add ${cart.name}")
            }).also {
                mDisposable.add(it)
            }
    }

    fun updateCart(cart: CartEntity){
        cartDao.update(cart)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showToastMessage("$it row updated. Cart with id ${cart.id} is updated")
            },{
                it.message?.also {
                    view.showToastMessage(it)
                } ?: view.showToastMessage("Error update ${cart.name}")
            }).also {
                mDisposable.add(it)
            }
    }

    fun removeCart(cart: CartEntity){
        cartDao.delete(cart)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showToastMessage("$it row deleted. Cart with id ${cart.id} is deleted")
            },{
                it.message?.also {
                    view.showToastMessage(it)
                } ?: view.showToastMessage("Error remove ${cart.name}")
            }).also {
                mDisposable.add(it)
            }
    }

    fun removeAll(){
        cartDao.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showToastMessage("All item is deleted")
            },{
                it.message?.also {
                    view.showToastMessage(it)
                } ?: view.showToastMessage("Error remove all")
            }).also {
                mDisposable.add(it)
            }
    }

    fun stop(){
        mDisposable.clear()
    }
}