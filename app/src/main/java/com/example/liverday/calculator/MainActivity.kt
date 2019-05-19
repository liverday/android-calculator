package com.example.liverday.calculator

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.liverday.calculator.databinding.ActivityMainBinding
import java.lang.Double.isNaN
import java.lang.Double.parseDouble
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var valueOne = Double.NaN
    private var valueTwo: Double? = null
    private val decimalFormat = DecimalFormat("#.##########")

    private var CURRENT: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    @SuppressLint("SetTextI18n")
    fun onNumberClick(view: View) {
        view as Button
        binding.editText.setText(binding.editText.text.toString() + view.text.toString())
    }

    @SuppressLint("SetTextI18n")
    fun onOperatorClick(view: View) {
        computeCalculation()

        view as Button
        val char = view.text.first()
        when (char) {
            Action.ADDITION, Action.SUBTRACTION, Action.MULTIPLICATION, Action.DIVISION -> {
                CURRENT = char
                binding.infoTextView.text = binding.editText.text.toString() + " $char "
                binding.editText.text = null
            }
            Action.CLEAR -> {
                binding.editText.text = null
                binding.infoTextView.text = null
                valueOne = Double.NaN
                valueTwo = null
                CURRENT = null
            }
            else -> {
                val text = binding.infoTextView.text.toString()
                val valueOneFormatted = decimalFormat.format(valueOne)
                val valueTwoFormatted = decimalFormat.format(valueTwo)
                binding.infoTextView.text = "$text$valueTwoFormatted = $valueOneFormatted"
                valueOne = Double.NaN
                CURRENT = '0'
            }
        }
    }

    private fun computeCalculation() {
        if (!isNaN(valueOne)) {
            try {
                valueTwo = parseDouble(binding.editText.text.toString())
            } catch (e: Exception) {
                return
            }

            binding.editText.text = null

            when (CURRENT) {
                Action.ADDITION -> valueOne += valueTwo!!
                Action.DIVISION -> valueOne /= valueTwo!!
                Action.MULTIPLICATION -> valueOne *= valueTwo!!
                Action.SUBTRACTION -> valueOne -= valueTwo!!
            }
        } else {
            try {
                valueOne = parseDouble(binding.editText.text.toString())
            } catch (e: Exception) { }
        }
    }
}
