package sourceset564.samples.roomrx

import android.app.Activity
import android.view.View
import android.widget.Toast

/**
 * Created by Anwar on 11/20/2019.
 */

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun Activity.toast(message : String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}