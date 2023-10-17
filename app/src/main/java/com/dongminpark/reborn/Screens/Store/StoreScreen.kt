package com.dongminpark.reborn.Screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Buttons.FavoriteListButton
import com.dongminpark.reborn.Buttons.ReBorn
import com.dongminpark.reborn.Buttons.ShoppingCart
import com.dongminpark.reborn.Frames.productFrame
import com.dongminpark.reborn.Model.Product
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Utils.*
import com.dongminpark.reborn.ui.theme.Point

val ItemList = SnapshotStateList<Product>()
val ItemList1 = SnapshotStateList<Product>()
val ItemList2 = SnapshotStateList<Product>()
val ItemList3 = SnapshotStateList<Product>()
val ItemList4 = SnapshotStateList<Product>()

@Composable
fun StoreScreen(navController: NavController) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    val buttons = listOf("전체", "상의", "하의", "잡화")
    var searchState by rememberSaveable { mutableStateOf(true) }
    var selectedButtonIndex by rememberSaveable { mutableStateOf(0) }

    BackOnPressed()

    if (isLoading) {
        LoadingCircle()
        RetrofitManager.instance.productListUnsold(
            completion = { responseState, productList ->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        ItemList.clear()
                        ItemList.addAll(productList!!)
                        ItemList1.clear()
                        ItemList1.addAll(productList)
                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        RetrofitManager.instance.productCategory(
            "상의",
            completion = { responseState, productList ->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        ItemList2.clear()
                        ItemList2.addAll(productList!!)
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        RetrofitManager.instance.productCategory(
            "하의",
            completion = { responseState, productList ->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        ItemList3.clear()
                        ItemList3.addAll(productList!!)

                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        RetrofitManager.instance.productCategory(
            "잡화",
            completion = { responseState, productList ->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        ItemList4.clear()
                        ItemList4.addAll(productList!!)

                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    } else {
        Column() {
            TopAppBar(
                backgroundColor = Color.White,
                contentPadding = PaddingValues(8.dp)
            ) {
                ReBorn()
                Spacer(modifier = Modifier.weight(1f))
                SearchBar { text ->
                    searchState = text.isEmpty()
                    if (searchState) {
                        ItemList.clear()

                        when (selectedButtonIndex) {
                            0 -> {
                                ItemList.addAll(ItemList1)
                            }
                            1 -> {
                                ItemList.addAll(ItemList2)
                            }
                            2 -> {
                                ItemList.addAll(ItemList3)
                            }
                            3 -> {
                                ItemList.addAll(ItemList4)
                            }
                        }
                    } else {
                        RetrofitManager.instance.productSearch(
                            text,
                            completion = { responseState, productList ->
                                when (responseState) {
                                    RESPONSE_STATE.OKAY -> {
                                        ItemList.clear()
                                        ItemList.addAll(productList!!)
                                    }
                                    RESPONSE_STATE.FAIL -> {
                                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            })
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                FavoriteListButton() { navController.navigate("storeLikeList") }
                Spacer(modifier = Modifier.weight(1f))
                ShoppingCart() { navController.navigate("storeShoppingCart") }
            }
            Divider(color = Color.Black, thickness = 1.dp)

            if (searchState) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 14.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    buttons.forEachIndexed { index, label ->
                        OutlinedButton(
                            modifier = Modifier.size(
                                (label.length * 14 + 55).dp,
                                height = 35.dp
                            ),
                            onClick = {
                                selectedButtonIndex = index
                                ItemList.clear()
                                when (selectedButtonIndex) {
                                    0 -> {
                                        ItemList.addAll(ItemList1)
                                    }
                                    1 -> {
                                        ItemList.addAll(ItemList2)
                                    }
                                    2 -> {
                                        ItemList.addAll(ItemList3)
                                    }
                                    3 -> {
                                        ItemList.addAll(ItemList4)
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedButtonIndex == index) Point else Color.White),
                            shape = RoundedCornerShape(30),
                            content = {
                                Text(
                                    text = label,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = if (selectedButtonIndex == index) Color.White else Color.Black
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .padding(top = 5.dp),
                columns = GridCells.Fixed(3),
            ) {
                items(ItemList) { product ->
                    Box(modifier = Modifier.padding(4.dp)) {
                        productFrame(product, navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .padding(8.dp)
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(12.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )

        if (searchText.length == 0){
            Box(modifier = Modifier.fillMaxWidth(0.4f)){
                Text(text = "반팔티")
            }
        }
        BasicTextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(searchText)
                keyboardController?.hide()
                search(searchText, onSearch, focusManager, keyboardController)
            })
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun search(
    searchText: String,
    onSearch: (String) -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {
    if (searchText.isNotEmpty()) {
        onSearch(searchText)
    }
    focusManager.clearFocus()
    keyboardController?.hide()
}


@Preview
@Composable
fun StoreScreenPreview() {
    StoreScreen(navController = rememberNavController())
}

@Preview
@Composable
fun SearchBarPreview() {
    val text = "안녕하세요"

    BasicTextField(
        modifier = Modifier
            .size(width = 120.dp, height = 40.dp)
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(12.dp)
            ),
        value = text,
        onValueChange = {

        },
    )
}