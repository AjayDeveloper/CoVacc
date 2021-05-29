package com.example.covacc

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covacc.adapter.CenterAdapter
import com.example.covacc.model.Model
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var btn_search: Button
    private lateinit var edt_search_pin: EditText
    private lateinit var progress_circular: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var centerAdapter: CenterAdapter
    private lateinit var centerList: List<Model>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_search = findViewById(R.id.btn_search)
        edt_search_pin = findViewById(R.id.edt_pincode)
        progress_circular = findViewById(R.id.progress_circular)
        recyclerView = findViewById(R.id.recview)

        centerList = ArrayList<Model>()

        btn_search.setOnClickListener {
            val pinCode = edt_search_pin.text.toString()
            if (pinCode.length != 6) {
                Toast.makeText(applicationContext, "Wrong Pin Code", Toast.LENGTH_LONG).show()
            } else {
                (centerList as java.util.ArrayList<Model>).clear()
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val dpd =
                    DatePickerDialog(
                        this,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            progress_circular.visibility = View.GONE
                            var dateStr: String = """$dayOfMonth-${month + 1}-$year"""
                            getAppoimentDetails(pinCode, dateStr)

                        },
                        year,
                        month,
                        day
                    )

                dpd.show()


            }
        }
    }

    private fun getAppoimentDetails(pinCode: String, dateStr: String) {
        val url =
            "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=" + pinCode + "&date=\"$dateStr"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->

            try {
                val centerArray = response.getJSONArray("centers")
                if (centerArray.length().equals(0)) {
                    Toast.makeText(
                        applicationContext,
                        "There is not vaccination center available",
                        Toast.LENGTH_LONG
                    ).show()
                }
                for (i in 0 until centerArray.length()) {
                    val centerObj = centerArray.getJSONObject(i)
                    val centerName: String = centerObj.getString("name")
                    val centerAddress: String = centerObj.getString("address")
                    val centerFrom: String = centerObj.getString("from")
                    val centerTo: String = centerObj.getString("to")
                    val centerFee_type: String = centerObj.getString("fee_type")
                    val sessionObj = centerObj.getJSONArray("sessions").getJSONObject(0)
                    val available_capacity: Int = sessionObj.getInt("available_capacity")
                    val vaccineName: String = sessionObj.getString("vaccine")
                    val min_age_limit: Int = sessionObj.getInt("min_age_limit")

                    val mode = Model(
                        centerName,
                        centerAddress,
                        centerFrom,
                        centerTo,
                        centerFee_type,
                        min_age_limit,
                        vaccineName,
                        available_capacity,


                        )

                    centerList = centerList + mode

                }

                centerAdapter = CenterAdapter(centerList)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = centerAdapter
                centerAdapter.notifyDataSetChanged()


            } catch (e: Exception) {
                e.printStackTrace()

            }

        },
            { error ->
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        queue.add(request)

    }
}