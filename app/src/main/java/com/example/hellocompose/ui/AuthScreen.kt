package com.example.hellocompose.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.hellocompose.R
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.example.hellocompose.ui.theme.itemsColor
import com.example.hellocompose.ui.theme.itemsColor1
import com.example.hellocompose.ui.theme.itemsColor3
import com.example.hellocompose.ui.util.MyOutlinedTextField
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@ExperimentalAnimatedInsets
@Composable
fun authScreen(
    onNumberSendClicked: (telefonNumber: String) -> Unit,
) {
    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            Text(stringResource(R.string.authorization), style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.padding(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_group_5),
                contentDescription = "null" // decorative element
            )
            Spacer(modifier = Modifier.padding(16.dp))
            var telefon by remember { mutableStateOf("") }
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(4.dp))

            ) {

                MyOutlinedTextField(value = telefon,
                    onValueChange = { telefon = it },
                    visualTransformation = NumberTransformation(),
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
                                    onNumberSendClicked(telefon)
                                }
                        )
                    })

            }
        }
    }
}

@ExperimentalAnimatedInsets
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloComposeTheme {
//        authScreen()
    }
}

