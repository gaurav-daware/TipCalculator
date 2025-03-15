package com.example.tipcalculator

import junit.framework.TestCase.assertEquals
import org.junit.Test

class TipCalculatorTests {

    @Test
    fun calculateTip_20PercentNoRoundup(){
        val amount = 100.00
        val tipPercent = 20.00
        val expectedTip = 20.00
        val actualTip = tipcalculator(BillAmount = amount, tipPercent = tipPercent, false)
        assertEquals(expectedTip, actualTip)

    }
}