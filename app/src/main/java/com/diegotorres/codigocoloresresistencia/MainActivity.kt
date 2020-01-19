package com.diegotorres.codigocoloresresistencia

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerBand1:Spinner = findViewById(R.id.spinnerband1)
        val spinnerBand2:Spinner = findViewById(R.id.spinnerband2)
        val spinnerMulti:Spinner = findViewById(R.id.spinnermulti)
        val spinnerTol:Spinner = findViewById(R.id.spinnertol)
        val colorNombre = arrayOf("0","1","2","3","4","5","6","7","8","9")
        val colorMulti = arrayOf("x1 Ω","x10 Ω","x100 Ω","x1 kΩ","x10 kΩ","x100 kΩ","x1 MΩ","x10 MΩ","x100 MΩ","x1 GΩ","x0.1 Ω","x0.01 Ω")
        val colorTol =  arrayOf("±1%","±2%","±5%","±10%")
        val colorImagen = intArrayOf(R.drawable.negro,R.drawable.marron,R.drawable.rojo,R.drawable.naranja,R.drawable.amarillo,R.drawable.verde,R.drawable.azul,R.drawable.violeta,R.drawable.gris,R.drawable.blanco)
        val colorImaMulti = intArrayOf(R.drawable.negro,R.drawable.marron,R.drawable.rojo,R.drawable.naranja,R.drawable.amarillo,R.drawable.verde,R.drawable.azul,R.drawable.violeta,R.drawable.gris,R.drawable.blanco,R.drawable.dorado,R.drawable.plata)
        val colorImaTol = intArrayOf(R.drawable.marron,R.drawable.rojo,R.drawable.dorado,R.drawable.plata)
        val spinBand1 = SpinnerCustomAdapter(applicationContext,colorImagen,colorNombre)
        val spinBand2 = SpinnerCustomAdapter(applicationContext,colorImagen,colorNombre)
        val spinMulti = SpinnerCustomAdapter(applicationContext,colorImaMulti,colorMulti)
        val spinTol = SpinnerCustomAdapter(applicationContext,colorImaTol,colorTol)
        spinnerBand1.adapter = spinBand1
        spinnerBand2.adapter = spinBand2
        spinnerMulti.adapter = spinMulti
        spinnerTol.adapter = spinTol

        calculo.setOnClickListener {
            calculoResistencia(spinnerBand1.selectedItemPosition,spinnerBand2.selectedItemPosition,spinnerMulti.selectedItemPosition,spinnerTol.selectedItemPosition)
        }

    }

    class SpinnerCustomAdapter(private var context: Context, private var flags: IntArray, private var Network: Array<String>) : BaseAdapter() { private var inflter: LayoutInflater
        init {
            inflter = LayoutInflater.from(context)
        }
        override fun getCount(): Int {
            return flags.size
        }
        override fun getItem(i: Int): Any? {
            return null
        }
        override fun getItemId(i: Int): Long {
            return 0
        }
        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var view = view
            view = inflter.inflate(R.layout.colores, null)
            val icon = view.findViewById(R.id.spinner_imageView) as ImageView
            val names = view.findViewById(R.id.spinner_textView) as TextView
            icon.setImageResource(flags[i])
            names.text = Network[i]
            return view
        }
    }

    fun calculoResistencia(val1:Int,val2:Int,val3:Int,val4:Int){
        val concatenate:String = val1.toString()+val2.toString()
        val concatenateInt:Int = concatenate.toInt()
        val aux = 10.0
        val valf:Double
        val mostrarPantalla:String
        val auxTol = arrayOf("±1%","±2%","±5%","±10%")
        val calculate:Double

        if (val3 < 10){
            calculate = concatenateInt.toDouble()*aux.pow(val3)

            if(calculate >= 1000 && calculate < 10.0.pow(6)){
                valf = calculate/1000
                mostrarPantalla = "Resistencia de:\n" + valf.toString() + " kΩ" + " " + auxTol.get(val4)
                pantallaresultado.text = mostrarPantalla
            }

            else if(calculate >= 10.0.pow(6)&& calculate < 10.0.pow(9)){
                valf = calculate/(10.0.pow(6))
                mostrarPantalla = "Resistencia de:\n" + valf.toString() + " MΩ" + " " + auxTol.get(val4)
                pantallaresultado.text = mostrarPantalla
            }

            else if(calculate >= 10.0.pow(9) && calculate < 10.0.pow(12)){
                valf = calculate/(10.0.pow(9))
                mostrarPantalla = "Resistencia de:\n" + valf.toString() + " GΩ" + " " + auxTol.get(val4)
                pantallaresultado.text = mostrarPantalla
            }

            else{
                valf = calculate
                mostrarPantalla = "Resistencia de:\n" + valf.toString() + " Ω" + " " + auxTol.get(val4)
                pantallaresultado.text = mostrarPantalla
            }
        }

        else{
            if (val3 == 10){
                calculate = concatenateInt.toDouble()*0.1
                valf = calculate
                mostrarPantalla = "Resistencia de:\n" + valf.toString() + " Ω" + " " + auxTol.get(val4)
                pantallaresultado.text = mostrarPantalla
            }
            else{
                calculate = concatenateInt.toDouble()*0.01
                valf = calculate
                mostrarPantalla = "Resistencia de:\n" + valf.toString() + " Ω" + " " + auxTol.get(val4)
                pantallaresultado.text = mostrarPantalla
            }

        }

    }
}
