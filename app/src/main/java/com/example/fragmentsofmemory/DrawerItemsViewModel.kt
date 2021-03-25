package com.example.fragmentsofmemory

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.fragmentsofmemory.Database.DrawerItems
import com.example.fragmentsofmemory.Database.UserCard
import kotlinx.coroutines.*

class DrawerItemsViewModel(application: Application): AndroidViewModel(application){

    /*
    val dbs = Room.databaseBuilder(
        application.applicationContext,
        DrawerItemsDataBase::class.java, "database-DrawerItems"
    ).build()*/

    private val db by lazy{DrawerItemsDataBase.getDatabase(application, CoroutineScope(SupervisorJob()))}

    var allDrawerItems:LiveData<List<DrawerItems>> = db.getDao().getAll()

    /*
    init {
        GlobalScope.launch {
            val items = dbs.getItems().getAll()

            viewModelScope.launch {
                allDrawerItems = items
            }
        }
    }*/

    init {
        viewModelScope.launch(Dispatchers.IO){
            //initDrawerItems()
        }
    }


    fun initDrawerItems() {
        viewModelScope.launch(Dispatchers.IO){
            Log.d(TAG, "Hello")
            if(allDrawerItems.value?.isEmpty() == true) {
                db.getDao().insert(DrawerItems(0, "憨批创建的分类"))
            }
        }
    }

    fun addDrawerItemsDatabase(itemsName: String) {
        viewModelScope.launch(Dispatchers.IO){
            db.getDao().insert(DrawerItems(0, itemsName))
        }
    }
}