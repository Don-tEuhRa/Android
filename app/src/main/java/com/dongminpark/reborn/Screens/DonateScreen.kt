package com.dongminpark.reborn.Screens


import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.App
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Utils.*
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun rebornAppBarDonate() {
    TopAppBar(
        elevation = 10.dp,
        backgroundColor = Color(0xff78C1F3),
        modifier = Modifier.height(100.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Text(
                text = "옷 기부 독려멘트",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DonateScreen(navController: NavController) {
    BackOnPressed()
    val userInput = remember { mutableStateOf(TextFieldValue()) }
    val phoneInput = remember { mutableStateOf(TextFieldValue()) }
    val placeInput = remember { mutableStateOf(TextFieldValue()) }
    val placeDetailInput = remember { mutableStateOf(TextFieldValue()) }
    val postInput = remember { mutableStateOf(TextFieldValue()) }
    val houseNumInput = remember { mutableStateOf(TextFieldValue()) }

    val showDialog = remember { mutableStateOf(false) }
    val showDialogError = remember { mutableStateOf(false) }

    Surface(color = Color.White) {
        Scaffold(
            backgroundColor = Color.White,
            content = {
                Column {
                    rebornAppBarDonate()
                    donateInput(
                        navController = navController,
                        userInput = userInput,
                        phoneInput = phoneInput,
                        placeInput = placeInput,
                        placeDetailInput = placeDetailInput,
                        postInput = postInput,
                        houseNumInput = houseNumInput,
                        showDialog = showDialog,
                        showDialogError = showDialogError,
                        onDonateClicked = {
                            showDialogError.value = false
                            showDialog.value = true
                            userInput.value = TextFieldValue()
                            phoneInput.value = TextFieldValue()
                            placeInput.value = TextFieldValue()
                            placeDetailInput.value = TextFieldValue()
                            postInput.value = TextFieldValue()
                            houseNumInput.value = TextFieldValue()
                        }
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("NewApi")
@Composable
fun donateInput(
    navController: NavController,
    userInput: MutableState<TextFieldValue>,
    phoneInput: MutableState<TextFieldValue>,
    placeInput: MutableState<TextFieldValue>,
    placeDetailInput: MutableState<TextFieldValue>,
    postInput: MutableState<TextFieldValue>,
    houseNumInput: MutableState<TextFieldValue>,
    showDialog: MutableState<Boolean>,
    showDialogError: MutableState<Boolean>,
    onDonateClicked: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val shouldShowHouseNum = remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var isHouseNumEnabled by remember { mutableStateOf(true) }
    var sendApi by remember { mutableStateOf(false) }


    val houseNumResource: (Boolean) -> Int = {
        if (it) {
            R.drawable.baseline_visibility_24
        } else {
            R.drawable.baseline_visibility_off_24
        }
    }

    // 수거날짜, 이름, 연락처, 주소, 상세주소, 우편번호, 현관비밀번호
    LazyColumn( // LazyColumn으로 수정 -> 방문수거 장소 => 마이페이지에 있는것 처럶 주소, 상세주소, 우편번호
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "수거날짜")
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
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.8f)
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
        }//수거날짜
        item {
            Row() {
                Text(text = "이름")
                if (userInput.value.text.isEmpty()) {
                    Text(
                        text = "(이름을 입력해주세요.)",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userInput.value,
                onValueChange = { newValue -> userInput.value = newValue },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = if (showDialog.value) Color.Transparent else Color(0xff78C1F3)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

        }//이름
        item {
            Row() {
                Text(text = "연락처")
                if (phoneInput.value.text.isEmpty()) {
                    Text(
                        text = "( '-' 빼고 입력해주세요. )",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = phoneInput.value,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { newValue ->
                    if (newValue.text.all { it.isDigit() } && newValue.text.length <= 11) {
                        phoneInput.value = newValue
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = if (showDialog.value) Color.Transparent else Color(0xff78C1F3)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
        }//연락처
        item {
            Row() {
                Text(text = "방문수거 장소")
                if (placeInput.value.text.isEmpty()) {
                    Text(
                        text = "(장소를 입력해주세요.)",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
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
                    cursorColor = if (showDialog.value) Color.Transparent else Color(0xff78C1F3)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
        }//방문수거
        item {
            Row() {
                Text(text = "상세 주소")
                if (placeDetailInput.value.text.isEmpty()) {
                    Text(
                        text = "(상세주소를 입력해주세요.)",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = placeDetailInput.value,
                onValueChange = { newValue -> placeDetailInput.value = newValue },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = if (showDialog.value) Color.Transparent else Color(0xff78C1F3)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

        }//상세주소
        item {
            Row() {
                Text(text = "우편번호")
                if (postInput.value.text.isEmpty()) {
                    Text(
                        text = "(우편번호를 입력해주세요)",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = postInput.value,
                onValueChange = { newValue ->
                    if (newValue.text.all { it.isDigit() } && newValue.text.length <= 5) {
                        postInput.value = newValue
                    } },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = if (showDialog.value) Color.Transparent else Color(0xff78C1F3)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
        }//우편변호

        item {

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "현관비밀번호")
                if (!isHouseNumEnabled && houseNumInput.value.text.isEmpty()) {
                    Text(
                        text = "(비밀번호를 입력해주세요.)",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Checkbox(
                    checked = isHouseNumEnabled,
                    onCheckedChange = {
                        isHouseNumEnabled = it
                    },
                    modifier = Modifier.align(Alignment.Bottom)
                )
                Text(text = "없음")
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = houseNumInput.value,
                enabled = !isHouseNumEnabled,
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
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                onValueChange = { newValue -> houseNumInput.value = newValue },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = if (showDialog.value) Color.Transparent else Color(0xff78C1F3)
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
        }//현관비밀번호
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                donateButton(
                    enabled =!(placeInput.value.text.isEmpty() || userInput.value.text.isEmpty() || placeDetailInput.value.text.isEmpty() || postInput.value.text.length != 5
                            || phoneInput.value.text.length != 11 || (!isHouseNumEnabled && houseNumInput.value.text.isEmpty())
                            )
                    , onDonateClicked = {
                    if (
                        placeInput.value.text.isEmpty() || userInput.value.text.isEmpty() || placeDetailInput.value.text.isEmpty() || postInput.value.text.length != 5
                                || phoneInput.value.text.length != 11 || (!isHouseNumEnabled && houseNumInput.value.text.isEmpty())
                    ) {
                        showDialogError.value = true
                    } else {
                        sendApi = true
                        RetrofitManager.instance.receiptCreate(
                            name = userInput.value.text,
                            phoneNumber = phoneInput.value.text,
                            date = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            address = placeInput.value.text,
                            addressDetail = placeDetailInput.value.text,
                            zipCode = postInput.value.text.toInt(),
                            gatePassword = if (isHouseNumEnabled) "" else houseNumInput.value.text,
                            completion = { responseState->
                                when (responseState) {
                                    RESPONSE_STATE.OKAY -> {
                                        Log.d(Constants.TAG, "api 호출 성공")
                                        onDonateClicked()
                                    }
                                    RESPONSE_STATE.FAIL -> {
                                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                        Log.d(Constants.TAG, "api 호출 에러")
                                    }
                                }
                                sendApi = false
                            })
                    }
                })
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
    if (sendApi){
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("잠시만 기다려 주세요")
            },
            text = {
                Text("기부를 진행중입니다.")
            },
            confirmButton = {
            },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                2.dp,
                Color.LightGray.copy(alpha = 0.7f),
                RoundedCornerShape(8.dp)
            )
        )
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
                        navController.navigate("donate")
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff78C1F3)
                    )
                ) {
                    Text("확인")
                }
            },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                2.dp,
                Color.LightGray.copy(alpha = 0.7f),
                RoundedCornerShape(8.dp)
            )
        )
    }
    if (showDialogError.value) {
        AlertDialog(
            onDismissRequest = {
                showDialogError.value = false
            },
            title = {
                Text("기부하기 실패")
            },
            text = {
                Text("입력되지않은 칸이 있습니다")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialogError.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray
                    )
                ) {
                    Text("확인")
                }
            },
            modifier = Modifier.border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
        )
    }
}


@Composable
fun donateButton(enabled: Boolean, onDonateClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xff78C1F3),
            contentColor = Color.White
        ),
        onClick = {
            Log.d("Tag", "DonateButton 눌림")
            onDonateClicked()
        }) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
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
