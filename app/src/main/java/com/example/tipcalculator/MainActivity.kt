package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalcLayout()

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipCalcLayout(){
    var amountInput by remember { mutableStateOf("") }
    var percentInput by remember { mutableStateOf("") }
    val amount =amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = percentInput.toDoubleOrNull() ?: 0.0
    val tip = tipcalculator(amount,tipPercent)
    Column(modifier = Modifier
        .statusBarsPadding()
        .padding(horizontal = 40.dp)
        .verticalScroll(rememberScrollState())
        .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.calculate_tip),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(Alignment.Start)
        )
        EditNumberField(amountInput,{amountInput=it},"bill amount",modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth())

      //  Spacer(modifier = Modifier.height(4.dp))

        EditNumberField(percentInput,{percentInput=it},"tip percentage",modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth())


        Text(text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall)

    }

}

@Composable
fun EditNumberField(value: String,
                    onValueChange: (String) -> Unit = {},
                    placeholder: String ,
    modifier: Modifier = Modifier){

    TextField(
        value = value,
        onValueChange = onValueChange,
        //label = { Text("bill amount") },
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
      //  leadingIcon = {Icon(Icons.Filled.Person, contentDescription = null)},
        modifier = modifier
    )
}

private fun tipcalculator(BillAmount: Double, tipPercent: Double):Double{
    val tip = tipPercent/100 * BillAmount
    return tip
}


