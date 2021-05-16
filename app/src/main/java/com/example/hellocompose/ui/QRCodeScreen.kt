package com.example.hellocompose.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hellocompose.R
import com.example.hellocompose.ui.theme.itemsColor
//import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun QRCodeScreen(vm: MainViewModel) {
    val name: String by vm.state.observeAsState("")
    Log.d("Nurs","qr ${name}")
    OutlinedButton(
        onClick = {
            vm.makeSuspendCall()
        },
//        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),  //avoid the oval shape
        border = BorderStroke(1.dp, itemsColor),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = itemsColor)
    ) {
        val style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            letterSpacing = 0.15.sp
        )
        Text(stringResource(R.string.update_settings), style = style)
    }
}