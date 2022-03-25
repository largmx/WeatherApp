package com.test.myapplication.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.test.myapplication.Constants
import com.test.myapplication.R
import com.test.myapplication.adapter.WeatherAdapter
import com.test.myapplication.fragment.DetailFragmentDialog
import com.test.myapplication.model.DailyModel
import com.test.myapplication.model.WeatherResponse
import com.test.myapplication.viewmodel.ItemListener
import com.test.myapplication.viewmodel.WeatherModelListener
import com.test.myapplication.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity(), WeatherModelListener, ItemListener {
    lateinit var viewModel: WeatherViewModel
    var dialog: AlertDialog? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var lnlConexion: LinearLayoutCompat
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lnlConexion = findViewById(R.id.lnl_conexion) as LinearLayoutCompat

        recyclerView = findViewById(R.id.recyclerview) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        adapter = WeatherAdapter(this)
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.listener = this

        val btnRetry = findViewById(R.id.btn_retry) as MaterialButton
        run()
        btnRetry.setOnClickListener {
            run()
        }
    }

    fun run(){
        if (isNetworkAvailable(this)){
            lnlConexion.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            viewModel.getWeather()
        }else{
            lnlConexion.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            Toast.makeText(this, R.string.no_hay_internet, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStarted() {
        showAlertDialog()
    }

    override fun onSuccess(weatherResponse: WeatherResponse?) {
        if (weatherResponse != null) {
            val data = weatherResponse.dailyModel as ArrayList
            adapter.updateData(data)
        }
        dismissDialog()
    }

    override fun onError(message: String) {
        dismissDialog()
    }

    fun dismissDialog() {
        if (dialog != null)
            dialog!!.dismiss()
    }

    fun showAlertDialog() {

        if (dialog == null) {
            val builder = AlertDialog.Builder(this)
            val layoutInflater = layoutInflater.inflate(R.layout.progressdialogcustom, null)
            builder.setView(layoutInflater)
            builder.setCancelable(false)

            dialog = builder.create()
            dialog!!.show()
        } else {
            dialog!!.show()
        }
    }

    override fun onItemClick(dailyModel: DailyModel) {

        val detailFragmentDialog = DetailFragmentDialog(dailyModel)
        detailFragmentDialog.show(supportFragmentManager,Constants.TAG_DETAIL )


    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }


}