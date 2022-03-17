package uz.context.examenapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.examenapplication.R
import uz.context.examenapplication.activities.adapter.MyAdapter
import uz.context.examenapplication.activities.adapter.MyAdapter2
import uz.context.examenapplication.activities.database.CardEntity
import uz.context.examenapplication.activities.database.MyDatabase
import uz.context.examenapplication.activities.model.Cards
import uz.context.examenapplication.activities.networking.RetrofitHttp
import java.util.*
import kotlin.collections.ArrayList

class CardListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var myDatabase: MyDatabase
    private lateinit var myAdapter: MyAdapter
    private lateinit var myAdapter2: MyAdapter2
    private lateinit var linearLayout: LinearLayout
    private val cardList = ArrayList<Cards>()
    private val cardList2 = ArrayList<CardEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        initViews()

    }

    private fun initViews() {
        progressBar = findViewById(R.id.progressBar)
        if (checkInternet()) {
            progressBar.isVisible = true
            apiRequest()
        } else {
            getDataBase()
        }
        myDatabase = MyDatabase.getDatabase(this)!!
        val btnAdd: AppCompatImageView = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        linearLayout = findViewById(R.id.linearlayout)
        recyclerView.layoutManager = LinearLayoutManager(this)
        myAdapter = MyAdapter(this, cardList)
        myAdapter2 = MyAdapter2(this, cardList2)
        recyclerView.adapter = myAdapter

        btnAdd.setOnClickListener {
            intent()
        }
    }

    private fun apiRequest() {
        RetrofitHttp.postService.getAllCards().enqueue(object : Callback<ArrayList<Cards>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<Cards>>,
                response: Response<ArrayList<Cards>>
            ) {
                if (response.isSuccessful) {
                    progressBar.isVisible = false
                    cardList.addAll(response.body()!!)
                    myAdapter.notifyDataSetChanged()
                    cardList2.addAll(myDatabase.getDao().getAllData())
                }
            }

            override fun onFailure(call: Call<ArrayList<Cards>>, t: Throwable) {
                Log.d("@@@", t.message.toString())
                t.printStackTrace()
            }
        })
    }

    private fun getDataBase() {

    }

    private fun intent() {
        val intent = Intent(this, AddNewCardActivity::class.java)
        startActivity(intent)
    }
    private fun checkInternet(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }
}

//1- Create card list page
//2- Add credit card page
//3- Mock Api as a backend
//4- In case of Offline, use database