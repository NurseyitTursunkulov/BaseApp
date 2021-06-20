package com.example.hellocompose.ui.util

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.hellocompose.R
import com.example.hellocompose.ui.theme.*
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.buttons
import com.vanpra.composematerialdialogs.datetime.datepicker.datepicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

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
        Text(errorText, color = color, maxLines = 1, overflow = TextOverflow.Ellipsis)

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
fun authorizeText() {
//    Spacer(Modifier.height(16.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.already_signed_in))
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            stringResource(R.string.autorize),
            color = Color.Blue,
            modifier = Modifier.clickable {

            })
    }
    Spacer(Modifier.height(96.dp))
}

@Composable
fun registerButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Spacer(Modifier.height(32.dp))
    OutlinedButton(
        onClick = {
            onClick()
        },
        modifier = modifier
            .navigationBarsWithImePadding(),
//            .align(alignment = Alignment.CenterHorizontally),  //avoid the oval shape
        border = BorderStroke(1.dp, itemsColor),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = itemsColor)
    ) {
        val style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            letterSpacing = 0.15.sp
        )
        Text(stringResource(R.string.register), style = style)
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

@Composable
fun countTimerWithDisabledButton(
    text2: String
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (button, text) = createRefs()
        Text(text = stringResource(R.string.after_n_seconds, text2),
            color = Color.Black.copy(alpha = 0.2f),
            modifier = Modifier
                .padding(start = 8.dp)
                .constrainAs(text) {
                    start.linkTo(parent.start, margin = 0.dp)
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                    )
                }
        )

        Button(
            onClick = {}, modifier = Modifier
                .padding(4.dp)
                .constrainAs(button) {
                    end.linkTo(parent.end, margin = 0.dp)

                }, enabled = false
        ) {
            Text(
                text = stringResource(R.string.send_new_code),
                style = Typography.body2.copy(color = Color.White),
                modifier = Modifier
                    .clickable(onClick = {})
            )
        }
    }
}

@Composable
fun sendNewSmsText(modifier: Modifier) {
    Text(
        text = stringResource(R.string.when_sms_not_received),
        style = Typography.body2.copy(),
        modifier = modifier
    )
}

@Composable
fun sendNewSmsButton(modifier: Modifier, onClick: () -> Unit) {
    val horizontalGradient = Brush.horizontalGradient(
        colors = listOf(
            buttonGradientStart,
            buttonGradientMiddle,
            buttonGradientEnd,
        ),
        0f,
        250f
    )
    Text(
        text = stringResource(R.string.send_new_code),
        style = Typography.body2.copy(color = Color.White),
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(4.dp))
            .background(brush = horizontalGradient)
            .padding(8.dp)
            .then(modifier)
    )
}

@Composable
fun sendSMSCodeView(onSendButtonClick: (telefon: String) -> Unit) {
    Spacer(modifier = Modifier.padding(8.dp))
    var telefon by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
    ) {
        MyOutlinedTextField(value = telefon,
            onValueChange = { telefon = it },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black.copy(alpha = 0.1f),
                backgroundColor = Color.Black.copy(alpha = 0.6f),
                unfocusedBorderColor = Color.Black.copy(alpha = 0.1f)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp))
                .navigationBarsWithImePadding(),
            trailingIcon = {
                Text(
                    "продолжить", color = Color.White, modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    itemsColor3,
                                    MaterialTheme.colors.primary,
                                    itemsColor1
                                )
                            )
                        )
                        .height(TextFieldDefaults.MinHeight - 2.dp)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .clickable {
                            onSendButtonClick(telefon)
                        }
                )
            })

    }
    Spacer(modifier = Modifier.padding(156.dp))
}


@Composable
fun showErrorSnackbar(
    modifier: Modifier,
    text: String,
    onOkClick: () -> Unit,
) {
    Snackbar(
        modifier = Modifier
            .padding(4.dp, bottom = 50.dp).then(modifier),
        actionOnNewLine = true,
        action = {
            TextButton(
                onClick = onOkClick
            ) {
                Text(text = "Remove")
            }
        }
    ) {
        Text(text = text)
    }
}


suspend fun countRemainedSeconds(
    context: Context,
    START_COUNT_TIME: Preferences.Key<Int>,
    updateTextCount: (count: String) -> Unit,
    countingFinished: () -> Unit
) {
    val lastSavedTime =
        context.dataStore.data.first()[START_COUNT_TIME]
    lastSavedTime?.let {
        val sec = TimeUnit.MILLISECONDS.toSeconds(Date().time)
        val remainedTimeInSecond = (sec - it)
        when {
            remainedTimeInSecond.toInt() < ONE_MINUTE -> {
                for (i in (ONE_MINUTE - remainedTimeInSecond.toInt()).downTo(
                    0
                )) {
                    delay(1000)
                    updateTextCount(i.toString())
                }
                countingFinished()
            }
            it == 0 -> {
                for (i in ONE_MINUTE.downTo(0)) {
                    delay(1000)
                    updateTextCount(i.toString())
                }
                countingFinished()
            }
            else -> {
                countingFinished()
            }
        }
    } ?: run {
        for (i in ONE_MINUTE.downTo(0)) {
            delay(1000)
            updateTextCount(i.toString())
        }
        countingFinished()
    }
}

@Composable
fun headerView() {
    Spacer(modifier = Modifier.padding(16.dp))
    Text(
        stringResource(R.string.authorization),
        style = MaterialTheme.typography.h6
    )
    Spacer(modifier = Modifier.padding(16.dp))
    Image(
        painter = painterResource(id = R.drawable.ic_group_5),
        contentDescription = "null" // decorative element
    )
    Spacer(modifier = Modifier.padding(16.dp))
}


const val ONE_MINUTE = 60
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")