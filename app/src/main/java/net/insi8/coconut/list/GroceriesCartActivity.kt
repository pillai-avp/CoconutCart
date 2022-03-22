package net.insi8.coconut.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.insi8.coconut.R
import org.koin.android.ext.android.inject

class GroceriesCartActivity : AppCompatActivity() {

    val viewModel : GroceriesCartViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groceries_cart)
        viewModel.initialize()
    }


}