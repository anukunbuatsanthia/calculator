package com.example.calculater

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var current = ""
    private var operator = ""
    private var firstName = 0.0
    private var secondNumber = 0.0
    private var result = 0.0
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //เชื่อม display เข้ากับ layout
        display = findViewById(R.id.txtDisplay)

        //ตัวแปรเก็บตัวเลข
        val numberButton = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9,
        )
        for (id in numberButton) {
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }



        //ปุ่มเครื่องหมายพื้นฐาน
        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { operatorClicked("/") }

        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener { deleteClicked() }
        findViewById<Button>(R.id.btnMod).setOnClickListener { modClicked() }
        findViewById<Button>(R.id.btnDot).setOnClickListener { addDot() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { toggleSign() }


    }
    private fun numberClicked(value: String) {
        if (isNewOperation) {
            display.text = ""
            isNewOperation = false


        }
        current += value
        display.text = current


    }
    private fun operatorClicked(op: String) {
        if (current.isNotEmpty()) {
            firstName = current.toDouble()
            operator = op
            current = ""
        }
    }
    private fun calculateResult() {
        if (current.isNotEmpty()){
            secondNumber = current.toDouble()
            result = when (operator) {
                "+" -> firstName + secondNumber
                "-" -> firstName - secondNumber
                "*" -> firstName * secondNumber
                "/" -> if (secondNumber != 0.0) firstName / secondNumber else Double.NaN
                "%" -> firstName % secondNumber
                else -> 0.0


            }
            display.text = if (result.isNaN()) "Can not divide by zero" else result.toString()
            current = result.toString()
            isNewOperation = true

        }
    }
    private fun clearAll() {
        current = ""
        operator = ""
        firstName = 0.0
        secondNumber = 0.0
        result = 0.0
        isNewOperation = true
        display.text = "0"
    }
    private fun addDot() {
        if (!current.contains(".")) {
            if (current.isEmpty()) {
                current = "0"
            }
            current += "."
            display.text = current
        }
    }
    private fun toggleSign() {
        if (current.isNotEmpty() && current != "0") {
            current = if (current.startsWith("-")) {
                current.substring(1)
            } else {
                "-$current"

            }
            display.text = current

        }
    }
    private fun deleteClicked() {
        if (isNewOperation) return
        if (current.isNotEmpty()){
            current = current.dropLast(1)
        }
        if (current == "-" || current.isEmpty()) {
            current = ""
            display.text = "0"
        } else {
            display.text = current
        }
    }

    private fun modClicked() {
        operatorClicked("%")
    }




}
