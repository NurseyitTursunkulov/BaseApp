package com.example.hellocompose.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hellocompose.R
import com.example.hellocompose.ui.theme.itemsColor

@Composable
fun SettingsScreen(vm: MainViewModel ) {

    val loading: Boolean by vm.showMainScreen.observeAsState(false)

    Column(modifier = Modifier.padding(28.dp)) {
        Text(stringResource(R.string.settings_label), color = Color.Black, style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(16.dp))
        if(loading){
            CircularProgressIndicator(
                modifier = Modifier
                    .size(60.dp)

            )
        }
        var name by remember { mutableStateOf("") }
        Text(stringResource(R.string.name))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            value = name,
            onValueChange = { name = it },
//                        label = { Text("имя") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = itemsColor,
                cursorColor = itemsColor,
            )
        )
        Spacer(Modifier.height(16.dp))
        var telefon by remember { mutableStateOf("") }
        Text(stringResource(R.string.telefon))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            value = telefon,
            onValueChange = { telefon = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        label = { Text("телефон") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = itemsColor,
                cursorColor = itemsColor,
            )
        )
        Spacer(Modifier.height(16.dp))
        var email by remember { mutableStateOf("") }
        Text("e-Mail")
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        label = { Text("телефон") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = itemsColor,
                cursorColor = itemsColor,
            )
        )

        Spacer(Modifier.height(16.dp))
        var birthDate by remember { mutableStateOf("") }
        Text(stringResource(R.string.date_of_birth))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            value = birthDate,
            onValueChange = { birthDate = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        label = { Text("телефон") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = itemsColor,
                cursorColor = itemsColor,
                focusedLabelColor = Color.Cyan
            )
        )
        Spacer(Modifier.height(16.dp))
        Spacer(Modifier.height(16.dp))

        Row() {
            val style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp,
                letterSpacing = 0.15.sp
            )
            Text(
                stringResource(R.string.points), style = style,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                "100", style = style, modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                    .wrapContentWidth(Alignment.End)
            )
        }
        Spacer(Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                      vm.makeSuspendCall()
            },
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),  //avoid the oval shape
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
}
