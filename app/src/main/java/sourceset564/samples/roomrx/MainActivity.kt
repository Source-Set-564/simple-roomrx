package sourceset564.samples.roomrx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import sourceset564.samples.roomrx.data.CartEntity
import kotlin.random.Random

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var mPresenter : MainPresenter
    private lateinit var mCartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCartAdapter = CartAdapter()
        recyclerItem.setHasFixedSize(true)
        recyclerItem.isNestedScrollingEnabled = true
        recyclerItem.layoutManager = LinearLayoutManager(this)
        recyclerItem.adapter = mCartAdapter
        mCartAdapter.itemActionListener = object : CartAdapter.OnItemClickListener{
            override fun onClick(cart: CartEntity) {
                //Generate dummy data to update
                val random = Random(System.currentTimeMillis())
                val foodNames = arrayOf("Fish Burger","Spaghetti","Pizza","Fried Chicken")
                val name = foodNames[random.nextInt(foodNames.size)]
                val qty = random.nextInt(16,30)
                val price = random.nextInt(35000,135000)

                cart.name = name
                cart.qty = qty
                cart.price = price

                mPresenter.updateCart(cart)
            }

            override fun onRemove(cart: CartEntity) {
                mPresenter.removeCart(cart)
            }
        }

        MainPresenter(this,this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        mPresenter.start()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.stop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionRemoveAll -> mPresenter.removeAll()
        }
        return true
    }

    override fun setPresenter(presenter: MainPresenter) {
        mPresenter = presenter
    }

    override fun showCartItem(list: List<CartEntity>) {
        mCartAdapter.updateAdapter(list)
    }

    override fun showEmptyMessage() {
        showEmptyMessage(resources.getString(R.string.empty_message))
    }

    override fun showEmptyMessage(message: String) {
        if(!emptyIcon.isShown || !emptyMessage.isShown) {
            mCartAdapter.clear()
            emptyMessage.setText(message)
            emptyIcon.visible()
            emptyMessage.visible()
        }
    }

    override fun hideEmptyMessage() {
        if(emptyIcon.isShown || emptyMessage.isShown) {
            emptyIcon.gone()
            emptyMessage.gone()
        }
    }

    override fun showToastMessage(message: String) {
        toast(message)
    }

    fun addCart(view: View) {
        //Generate dummy data to insert
        val random = Random(System.currentTimeMillis())
        val foodNames = arrayOf("Nasi Goreng","Nasi Pecel","Ayam Penyet","Sate Ayam")
        val name = foodNames[random.nextInt(foodNames.size)]
        val qty = random.nextInt(1,15)
        val price = random.nextInt(12500,45300)

        CartEntity(name,qty,price).also {
            mPresenter.addToCart(it)
        }
    }
}
