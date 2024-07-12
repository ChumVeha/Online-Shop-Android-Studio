package com.chumvehakh.onlineshopandroidstudio.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.chumvehakh.onlineshopandroidstudio.Model.BrandModel
import com.chumvehakh.onlineshopandroidstudio.Model.ItemsModel
import com.chumvehakh.onlineshopandroidstudio.Model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel():ViewModel(){
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _banner = MediatorLiveData<List<SliderModel>>()
    private val _brand = MediatorLiveData<MutableList<BrandModel>>()
    private val _popular= MediatorLiveData<MutableList<ItemsModel>>()

    val brands: LiveData<MutableList<BrandModel>> = _brand
    val popular: LiveData<MutableList<ItemsModel>> = _popular
    val banners: LiveData<List<SliderModel>> = _banner

    fun loadBanners(){
        val Ref=firebaseDatabase.getReference("Banner")
        Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children){
                    val list=childSnapshot.getValue(SliderModel::class.java)
                    if (list!=null){
                        lists.add(list)
                    }
                }
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun loadBrand(){
        val Ref=firebaseDatabase.getReference("Category")
        Ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<BrandModel>()
                for (childSnapshot in snapshot.children){
                    val list=childSnapshot.getValue(BrandModel::class.java)
                    if (list!=null){
                        lists.add(list)
                    }
                }
                _brand.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    fun loadPupolar(){
        val Ref=firebaseDatabase.getReference("Items")
        Ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children){
                    val list=childSnapshot.getValue(ItemsModel::class.java)
                    if (list!=null){
                        lists.add(list)
                    }
                }
                _popular.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}