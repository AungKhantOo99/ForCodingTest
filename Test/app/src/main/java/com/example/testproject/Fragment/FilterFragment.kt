package com.example.testproject.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.Adapter.AllPhoneAdapter
import com.example.testproject.FilterResultActivity
import com.example.testproject.R
import com.example.testproject.model.Tag
import com.example.testproject.model.products
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider

class FilterFragment : Fragment() {
    var brandName=""
    var priceRang=0..1000000
    var min=0
    var max=0
    var AllProduct=ArrayList<products>()

    lateinit var search:SearchView
    lateinit var allPhoneRecycler:RecyclerView
    lateinit var filter:FloatingActionButton
    lateinit var radioButton: RadioButton
    lateinit var oneToTen:LinearLayout
    lateinit var tentotwenty:LinearLayout
    lateinit var twentytothirty:LinearLayout
    lateinit var thirtytofifty:LinearLayout
    lateinit var morethanfifty:LinearLayout
    lateinit var warranty:CheckBox
    lateinit var finger:CheckBox
    lateinit var camera:CheckBox
    var StockRange=0..100
    var SearchProducts=ArrayList<products>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AddLocaldata()
        search=view.findViewById(R.id.searchphone)
        allPhoneRecycler=view.findViewById(R.id.recyclerproduct)
        filter=view.findViewById(R.id.floatingActionButton)
        SearchProducts.addAll(AllProduct)
        allPhoneRecycler.layoutManager = GridLayoutManager(context, 2)
        val adapterlist = context?.let { AllPhoneAdapter(it, AllProduct) }
        allPhoneRecycler.adapter = adapterlist
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(p0: String?): Boolean {
                adapterlist?.filter?.filter(p0)
                return true
            }
        })
        filter.setOnClickListener {

            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            dialog.setContentView(view)
            dialog.show()

            val brandnameradio=view.findViewById<RadioGroup>(R.id.radio_group)
            val filterdata=view.findViewById<Button>(R.id.filter)
            val rangeSlider=view.findViewById<RangeSlider>(R.id.rangeSlider)

            oneToTen=view.findViewById(R.id.onetoten)
            tentotwenty=view.findViewById(R.id.tentotwenty)
            twentytothirty=view.findViewById(R.id.twentytothirty)
            thirtytofifty=view.findViewById(R.id.thirtytofifty)
            morethanfifty=view.findViewById(R.id.morethanfifty)

            warranty=view.findViewById(R.id.warranty)
            finger=view.findViewById(R.id.finger)
            camera=view.findViewById(R.id.camera)

            stockRange()

            rangeSlider.addOnChangeListener { rangeSlider, _, _ ->
                min=rangeSlider.values[0].toInt()
                max=rangeSlider.values[1].toInt()
            }
            filterdata.setOnClickListener {
                    val intSelectButton: Int = brandnameradio!!.checkedRadioButtonId
                    radioButton = view.findViewById(intSelectButton)
                    brandName=radioButton.text.toString()
                val result: List<products> = AllProduct.filter {
                    it.brand.equals(brandName) &&
                            it.stock in StockRange &&
                            it.price in priceRang
                }
                var finalresult=ArrayList<products>()
                finalresult = CheckSpecification(result) as ArrayList<products>
                if(finalresult.isEmpty()){
                    Toast.makeText(context, "found 0 item", Toast.LENGTH_SHORT).show()
                }
                else{
                    val intent = Intent(context, FilterResultActivity::class.java)
                    intent.putExtra("Keys",finalresult)
                    startActivity(intent)
                }
            }
        }
    }
    fun CheckSpecification( beforecheck:List<products>):List<products>{
        var aftercheck:List<products>?=null
        if(warranty.isChecked && finger.isChecked && camera.isChecked){
            aftercheck= beforecheck.filter {
                it.tag.TripleCamera && it.tag.warranty && it.tag.InScreenFinger
            }
        } else
        if (warranty.isChecked && finger.isChecked){
            aftercheck=  beforecheck.filter {
                it.tag.warranty && it.tag.InScreenFinger
            }
        }else
        if (warranty.isChecked && camera.isChecked){
            aftercheck=  beforecheck.filter {
                it.tag.warranty && it.tag.TripleCamera
            }
        }else
        if (finger.isChecked && camera.isChecked){
            aftercheck=beforecheck.filter {
                it.tag.InScreenFinger && it.tag.TripleCamera
            }
        }else
        if (warranty.isChecked){
            aftercheck=beforecheck.filter {
                it.tag.warranty
            }
        }else
        if (finger.isChecked){
            aftercheck=beforecheck.filter {
                it.tag.InScreenFinger
            }
        }else
        if (camera.isChecked){
            aftercheck=beforecheck.filter {
                it.tag.TripleCamera
            }
        }else aftercheck=beforecheck
        return aftercheck
    }
    fun stockRange(){
        oneToTen.setOnClickListener {
            oneToTen.setBackgroundResource(R.drawable.selectbg)
            twentytothirty.setBackgroundResource(R.drawable.defaultbg)
            thirtytofifty.setBackgroundResource(R.drawable.defaultbg)
            morethanfifty.setBackgroundResource(R.drawable.defaultbg)
            tentotwenty.setBackgroundResource(R.drawable.defaultbg)
            StockRange = 1..10
        }
        tentotwenty.setOnClickListener {
            tentotwenty.setBackgroundResource(R.drawable.selectbg)
            twentytothirty.setBackgroundResource(R.drawable.defaultbg)
            thirtytofifty.setBackgroundResource(R.drawable.defaultbg)
            morethanfifty.setBackgroundResource(R.drawable.defaultbg)
            oneToTen.setBackgroundResource(R.drawable.defaultbg)
            StockRange = 11..20
        }
        twentytothirty.setOnClickListener {
            twentytothirty.setBackgroundResource(R.drawable.selectbg)
            tentotwenty.setBackgroundResource(R.drawable.defaultbg)
            thirtytofifty.setBackgroundResource(R.drawable.defaultbg)
            morethanfifty.setBackgroundResource(R.drawable.defaultbg)
            oneToTen.setBackgroundResource(R.drawable.defaultbg)
            StockRange = 21..30
        }
        thirtytofifty.setOnClickListener {
            thirtytofifty.setBackgroundResource(R.drawable.selectbg)
            twentytothirty.setBackgroundResource(R.drawable.defaultbg)
            tentotwenty.setBackgroundResource(R.drawable.defaultbg)
            morethanfifty.setBackgroundResource(R.drawable.defaultbg)
            oneToTen.setBackgroundResource(R.drawable.defaultbg)
            StockRange = 31..50
        }
        morethanfifty.setOnClickListener {
            morethanfifty.setBackgroundResource(R.drawable.selectbg)
            twentytothirty.setBackgroundResource(R.drawable.defaultbg)
            thirtytofifty.setBackgroundResource(R.drawable.defaultbg)
            tentotwenty.setBackgroundResource(R.drawable.defaultbg)
            oneToTen.setBackgroundResource(R.drawable.defaultbg)
            StockRange = 50..300
        }
    }
    fun AddLocaldata(){
        AllProduct.add(
            products(
                "SAMSUNG Z FLIP", R.drawable.sszflip, 990000, 3, "SAMSUNG",
                Tag(true, true, false)
            )
        )
        AllProduct.add(
            products(
                "HUAWEI ENJOY 7", R.drawable.henjoy, 250000, 32, "HUAWEI",
                Tag(true, false, false)
            )
        )
        AllProduct.add(
            products(
                "SAMAUNG S10", R.drawable.sssten, 790000, 20, "SAMSUNG",
                Tag(false, true, false)
            )
        )
        AllProduct.add(
            products(
                "MI K20", R.drawable.mitwenty, 500000, 3, "MI",
                Tag(false, true, true)
            )
        )
        AllProduct.add(
            products(
                "HUAWEI GOLD", R.drawable.hgold, 700000, 9, "HUAWEI",
                Tag(true, true, true)
            )
        )
        AllProduct.add(
            products(
                "SAMAUNG M53", R.drawable.ssmfivethree, 370000, 3, "SAMSUNG",
                Tag(true, true, true)
            )
        )
        AllProduct.add(
            products(
                "MI MAX TWO", R.drawable.mimaxtwo, 200000, 30, "MI",
                Tag(true, false, false)
            )
        )
        AllProduct.add(
            products(
                "OPPO A76", R.drawable.oppoasevensix, 300000, 25, "OPPO",
                Tag(true, true, false)
            )
        )
        AllProduct.add(
            products(
                "SAMAUNG NOTE 3", R.drawable.ssnotethree, 150000, 53, "SAMSUNG",
                Tag(false, true, false)
            )
        )
        AllProduct.add(
            products(
                "HUAWEI NOVA", R.drawable.hnova, 400000, 12, "HUAWEI",
                Tag(true, true, true)
            )
        )
        AllProduct.add(
            products(
                "OPPO F21", R.drawable.oppoftwoone, 900000, 5, "OPPO",
                Tag(true, true, true)
            )
        )
        AllProduct.add(
            products(
                "SAMAUNG A40", R.drawable.ssafour, 690000, 21, "SAMSUNG",
                Tag(true, true, true)
            )
        )
        AllProduct.add(
            products(
                "MI NOTE 11", R.drawable.minoteeleven, 600000, 13, "MI",
                Tag(true, true, true)
            )
        )
        AllProduct.add(
            products(
                "HUAWEI p7", R.drawable.hpseven, 400000, 5, "HUAWEI",
                Tag(true, true, true)
            )
        )
        AllProduct.add(
            products(
                "OPPO NEON 7", R.drawable.opponeonseven, 150000, 40, "OPPO",
                Tag(false, false, false)
            )
        )
        AllProduct.add(
            products(
                "MI NINE", R.drawable.minine, 150000, 35, "MI",
                Tag(false, true, false)
            )
        )
        AllProduct.add(
            products(
                "HUAWEI Y7", R.drawable.hyseven, 500000, 20, "HUAWEI",
                Tag(true, true, true)
            )
        )
        AllProduct.add(
            products(
                "MI NOTE 8", R.drawable.minoteeight, 350000, 23, "MI",
                Tag(false, true, false)
            )
        )
        AllProduct.add(
            products(
                "OPPO FIND N", R.drawable.oppofindn, 600000, 17, "OPPO",
                Tag(true, true, true)
            )
        )
    }
}