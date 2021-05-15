package com.example.hellocompose.ui.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hellocompose.R
import com.example.hellocompose.ui.theme.itemsColor
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.buttons
import com.vanpra.composematerialdialogs.datetime.datepicker.datepicker

@Composable
fun infoEnterField(
    color: Color,
    errorText: String,
    value: String,
    valueContainsError: Boolean,
    textHeader: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onvalueChanged: (newValue: String) -> Unit
) {
    Spacer(Modifier.height(16.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(textHeader, color = color)
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Text(errorText, color = color, maxLines = 1,overflow = TextOverflow.Ellipsis)

    }
    androidx.compose.material.OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        value = value,
        isError = valueContainsError,
        keyboardOptions = keyboardOptions,
        onValueChange = {
            onvalueChanged(it)

        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = itemsColor,
            cursorColor = itemsColor,
        )
    )

}

@Composable
fun birthDateButton(
    birthDateColor: Color,
    birthDateErrorText: String,
    color: Color,
    birthDate: String,
    birthDateContainsError: Boolean,
    onDateSelected: (date: String) -> Unit
) {
    Spacer(Modifier.height(16.dp))
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(stringResource(R.string.date_of_birth), color = birthDateColor)
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Text(
            birthDateErrorText,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
    val dialog = remember { MaterialDialog() }
    dialog.build {

        datepicker { date ->
            onDateSelected(date.toString())
        }
        buttons {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    }
    Spacer(Modifier.padding(4.dp))

    OutlinedButton(
        onClick = { dialog.show() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        border = BorderStroke(
            ButtonDefaults.OutlinedBorderSize,
            if (birthDateContainsError)
                Color.Red
            else
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
        )
    ) {
        val style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            letterSpacing = 0.1.sp
        )
        Text(text = birthDate, color = Color.Black, style = style)
    }
}

@Composable
fun headerText(text: String) {
    Spacer(Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text,
            color = Color.Black,
            style = MaterialTheme.typography.h6
        )
    }
}