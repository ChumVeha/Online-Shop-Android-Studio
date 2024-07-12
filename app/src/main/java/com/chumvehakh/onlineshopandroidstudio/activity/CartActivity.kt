package com.chumvehakh.onlineshopandroidstudio.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chumvehakh.onlineshopandroidstudio.Adapter.CartAdapter
import com.chumvehakh.onlineshopandroidstudio.Helper.ChangeNumberItemsListener
import com.chumvehakh.onlineshopandroidstudio.Helper.ManagmentCart
import com.chumvehakh.onlineshopandroidstudio.R
import com.chumvehakh.onlineshopandroidstudio.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {
    private lateinit var binding:ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart= ManagmentCart(this)

        setVariable()
        initCartList()
        calculateCart()
    }

    private fun initCartList() {
        binding.viewCart.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        binding.viewCart.adapter=CartAdapter(managmentCart.getListCart(), this, object :ChangeNumberItemsListener{
            override fun onChanged() {
                calculateCart()
            }

        })
        with(binding){
            emptyTxt.visibility=
                if (managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView2.visibility=
                if (managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }
    private fun calculateCart(){
        val percentTxt=0.02
        val delivery=10.0
        tax = Math.round((managmentCart.getTotalFee()*percentTxt)*100)/100.0
        val total = Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100
        val itemTotal = Math.round(managmentCart.getTotalFee()*100)/100

        with(binding){
            totalFeeTxt.text="$$itemTotal"
            taxTxt.text="$$tax"
            deliveryTxt.text="$$delivery"
            totalTxt.text="$$total"
        }
    }
    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }
}