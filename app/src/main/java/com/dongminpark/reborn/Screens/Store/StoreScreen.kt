package com.dongminpark.reborn.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Buttons.FavoriteListButton
import com.dongminpark.reborn.Buttons.ReBorn
import com.dongminpark.reborn.Buttons.ShoppingCart
import com.dongminpark.reborn.Frames.productFrame

@Composable
fun StoreScreen(navController: NavController) {
    val ItemList by remember { mutableStateOf(mutableListOf(1, 2, 3,4,5,6,7,8,9,10)) }
    var buttons by remember { mutableStateOf(mutableListOf("전체", "상의", "하의", "잡화")) }
    var selectedButtonIndex by rememberSaveable { mutableStateOf(0) }


    Column() {
        TopAppBar(
            backgroundColor = Color.White,
            contentPadding = PaddingValues(8.dp)
        ) {
            ReBorn()
            Spacer(modifier = Modifier.weight(1f))
            SearchBar{}
            Spacer(modifier = Modifier.weight(1f))
            FavoriteListButton(){navController.navigate("storeLikeList")}
            Spacer(modifier = Modifier.weight(1f))
            ShoppingCart(){navController.navigate("storeShoppingCart")}
        }
        Divider(color = Color.Black, thickness = 1.dp)

        LazyRow(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            item {
                buttons.forEachIndexed { index, label ->
                    OutlinedButton(
                        modifier = Modifier.size(
                            (label.length * 13 + 50).dp,
                            height = 35.dp
                        ),
                        onClick = {
                            selectedButtonIndex = index
                            /*
                            loadPost = false
                            postList.clear()
                            start = 0
                            display = 20
                            selectedButtonIndex = index
                            selectedButtonIndex2 = index
                            if (label == buttons[0]) {
                                postpopular(start, display, postList)
                            } else postfilter(label, start, display, postList)
                            start += 20
                            display += 20

                             */
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedButtonIndex == index) MaterialTheme.colors.primaryVariant else Color.White),
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
            columns = GridCells.Fixed(2),
        ) {
            items(ItemList) { post ->
                productFrame(post, navController, "store", true, true, true)
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

    TextFieldFormat(
        text = searchText,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController?.hide()
            search(searchText, onSearch, focusManager, keyboardController)
            })
    ){
        searchText = it
    }
}

@Composable
fun TextFieldFormat(modifier: Modifier = Modifier, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, keyboardActions: KeyboardActions = KeyboardActions { }, text: String, isSingle: Boolean = true, onValueChange: (String) -> Unit){
    val textFieldColors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    TextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth(0.8f),
        maxLines = if (isSingle) 1 else 100,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = textFieldColors,
        singleLine = isSingle
    )
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