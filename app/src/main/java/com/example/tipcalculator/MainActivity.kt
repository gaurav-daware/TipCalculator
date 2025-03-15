package com.example.tipcalculator

import android.bluetooth.le.BluetoothLeAdvertiser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import kotlin.math.ceil


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
fun TipCalcLayout() {
    var amountInput by rememberSaveable { mutableStateOf("") }
    var percentInput by rememberSaveable { mutableStateOf("") }
    var roundup by rememberSaveable { mutableStateOf(false) }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = percentInput.toDoubleOrNull() ?: 0.0
    val tip = tipcalculator(amount, tipPercent, roundup)
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(Alignment.Start)
        )
        EditBillAmount(amountInput, { amountInput = it },Modifier.padding(bottom = 32.dp).fillMaxWidth())
        EditTipPercent(percentInput, { percentInput = it },Modifier.padding(bottom = 32.dp).fillMaxWidth())


        //  Spacer(modifier = Modifier.height(4.dp))


        RoundtheTipRow(roundup, { roundup = it })

        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(thickness = 1.dp,
            modifier = Modifier.padding(4.dp))

        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(10.dp)
        )

    }

}

@Composable
fun EditBillAmount(
    value :String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(stringResource(R.string.bill_amount))},
        leadingIcon = { Icon(painter = painterResource(id=R.drawable.money),null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
        singleLine = true,
        modifier = modifier

    )
}
@Composable
fun EditTipPercent(
    value :String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(stringResource(R.string.tip_percentage))},
        leadingIcon = { Icon(painter = painterResource(id=R.drawable.percent),null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        singleLine = true,
        modifier = modifier

    )
}



@Composable
fun RoundtheTipRow(
    roundup: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = stringResource(R.string.round_up_tip),
            fontSize = 18.sp
        )

        Switch(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundup,
            onCheckedChange = onRoundUpChanged
        )


    }
}

@VisibleForTesting
internal fun tipcalculator(BillAmount: Double, tipPercent: Double, roundUp: Boolean): Double {
    val tip = tipPercent / 100 * BillAmount
    if (roundUp) {
        return ceil(tip)
    }
    return tip
}


