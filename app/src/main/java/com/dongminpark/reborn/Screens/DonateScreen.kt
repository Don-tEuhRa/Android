package com.dongminpark.reborn.Screens


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//구현안된기능
//입력안됐을때 경고띄우는 기능, 팝업창 잘 안보여서 잘 보이게 하고싶은데 색을 못정함, TextField누를때마다 거기로 화면 자동 스크롤되게하고싶은데 뭔지모르겠움

@Composable
fun rebornAppBarDonate(){
    TopAppBar(elevation = 10.dp,
        backgroundColor = Color(0xff78C1F3),
        modifier = Modifier.height(100.dp)
    ) {
        Column(modifier = Modifier
            .padding(8.dp)) {
            Text(text = stringResource(id = R.string.app_name),
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Text(text ="옷 기부 독려멘트",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp)

        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DonateScreen(navController: NavController) {
    val placeInput = remember { mutableStateOf(TextFieldValue()) }
    val dateInput = remember { mutableStateOf(TextFieldValue()) }
    val houseNumInput = remember { mutableStateOf(TextFieldValue()) }
    val userInput = remember { mutableStateOf(TextFieldValue()) }
    val phoneInput = remember { mutableStateOf(TextFieldValue()) }

    val showDialog = remember { mutableStateOf(false) }

    Surface(color = Color.White) {
        Scaffold(
            backgroundColor = Color.White,
            content = {
                Column {
                    rebornAppBarDonate()
                    Spacer(modifier = Modifier.height(20.dp))
                    donateInput(
                        placeInput = placeInput,
                        dateInput = dateInput,
                        houseNumInput = houseNumInput,
                        userInput = userInput,
                        phoneInput = phoneInput,
                        showDialog = showDialog,
                        onDonateClicked = {
                            showDialog.value = true

                            placeInput.value = TextFieldValue()
                            dateInput.value= TextFieldValue()
                            houseNumInput.value = TextFieldValue()
                            userInput.value = TextFieldValue()
                            phoneInput.value = TextFieldValue()
                        }
                    )
                }
            }
        )
    }
}

@SuppressLint("NewApi")
@Composable
fun donateInput(
    placeInput: MutableState<TextFieldValue>,
    dateInput: MutableState<TextFieldValue>,
    houseNumInput: MutableState<TextFieldValue>,
    userInput: MutableState<TextFieldValue>,
    phoneInput: MutableState<TextFieldValue>,
    showDialog: MutableState<Boolean>,
    onDonateClicked: () -> Unit
) {
    val shouldShowHouseNum = remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }


    val houseNumResource: (Boolean) -> Int = {
        if (it) {
            R.drawable.baseline_visibility_24
        } else {
            R.drawable.baseline_visibility_off_24
        }
    }

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "방문수거 장소")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = placeInput.value,
            onValueChange = { newValue -> placeInput.value = newValue },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color(0xff78C1F3)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))


        Text(text = "수거날짜")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp,Color.LightGray, RoundedCornerShape(6.dp))
        ) {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
            ) {
                Text(
                    text = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                )
                Text(text = "▼")
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            val startDate = LocalDate.now()
            val endDate = LocalDate.now().plusMonths(6).minusDays(1) // 6개월 이후까지의 날짜 표시

            var currentDate = startDate
            while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
                val date = currentDate
                DropdownMenuItem(
                    onClick = {
                        selectedDate = date
                        expanded = false
                    }
                ) {
                    Text(
                        text = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        color = if (date == selectedDate) Color(0xff78C1F3) else Color.Black
                    )
                }
                currentDate = currentDate.plusDays(1)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "현관비밀번호")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = houseNumInput.value,
            trailingIcon = {
                IconButton(onClick = {
                    Log.d("TAG", "Housenum:클릭")
                    shouldShowHouseNum.value = !shouldShowHouseNum.value
                }) {
                    Icon(
                        painter = painterResource(id = houseNumResource(shouldShowHouseNum.value)),
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (shouldShowHouseNum.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Next
            ),
            onValueChange = { newValue -> houseNumInput.value = newValue },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color(0xff78C1F3)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "이름")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInput.value,
            onValueChange = { newValue -> userInput.value = newValue },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next // Enter 누르면 다음으로 이동
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color(0xff78C1F3)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "연락처")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneInput.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            onValueChange = { newValue -> phoneInput.value = newValue },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                cursorColor = Color(0xff78C1F3)
            )
        )
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            donateButton(onDonateClicked = {
                onDonateClicked()
            })
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text("감사합니다!")
            },
            text = {
                Text("기부해주셔서 감사합니다.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff78C1F3)
                    )
                ) {
                    Text("확인")
                }
            }
        )
    }
}

@Composable
fun donateButton(onDonateClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors= ButtonDefaults.buttonColors(
            backgroundColor = Color(0xff78C1F3),
            contentColor = Color.White
        ),
        onClick = {
            Log.d("Tag","DonateButton 눌림")
            onDonateClicked()
        }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "기부하기",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}


@Preview
@Composable
fun PreviewDonateScreen() {
    val navController = rememberNavController()
    DonateScreen(navController = navController)
}
