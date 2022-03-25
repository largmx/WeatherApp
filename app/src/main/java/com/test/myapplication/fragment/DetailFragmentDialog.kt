package com.test.myapplication.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Insets
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.test.myapplication.Constants
import com.test.myapplication.R
import com.test.myapplication.model.DailyModel
import java.util.*

class DetailFragmentDialog (dailyModel: DailyModel) : DialogFragment() {

    lateinit var activity : Activity;
    lateinit var txtDate: TextView
    lateinit var txtWeather: TextView
    lateinit var txtPressure: TextView
    lateinit var txtHumidity: TextView

    val  model = dailyModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity  =  context as Activity
    }


    fun setInfo(){
        val weather = model.weather.get(0)
        val date = Date(model.dt).toString()

        txtDate.text = getString(R.string.dia, date )
        txtWeather.text = getString(R.string.clima, weather.description)
        txtPressure.text = getString(R.string.presion, model.pressure)
        txtHumidity.text = getString(R.string.humedad, model.humidity)
    }

    override fun onResume() {
        super.onResume()
        val window = dialog!!.window
        val width: Int = getScreenWidth(activity)
        window?.setLayout((width * Constants.WIDTH_PERCENT).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
    }

    fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.detail_layout, container, false )
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }

        txtDate = view.findViewById(R.id.txt_date)
        txtWeather = view.findViewById(R.id.txt_weather)
        txtPressure = view.findViewById(R.id.txt_pressure)
        txtHumidity = view.findViewById(R.id.txt_humidity)

        setInfo()

        val accept = view.findViewById(R.id.btn_aceptar) as MaterialButton
        accept.setOnClickListener{
            dismiss()
        }

        return view
    }


}