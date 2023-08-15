package com.dongminpark.reborn.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import com.dongminpark.reborn.Buttons.FavoriteListButton
import com.dongminpark.reborn.Buttons.ReBorn
import com.dongminpark.reborn.Buttons.ShoppingCart
import com.dongminpark.reborn.Frames.productFrame
import com.dongminpark.reborn.Utils.BackOnPressed
import com.dongminpark.reborn.ui.theme.Point

val ItemList = SnapshotStateList<Int>()

@Composable
fun StoreScreen(navController: NavController) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var buttons by remember { mutableStateOf(mutableListOf("전체", "상의", "하의", "잡화")) }
    var searchState by rememberSaveable { mutableStateOf(true) } // 검색 전후 구분
    var selectedButtonIndex by rememberSaveable { mutableStateOf(0) }

    BackOnPressed()

    if (isLoading){
        ItemList.addAll(arrayListOf(1, 2))
        isLoading = false
    }else{
        Column() {
            TopAppBar(
                backgroundColor = Color.White,
                contentPadding = PaddingValues(8.dp)
            ) {
                ReBorn()
                Spacer(modifier = Modifier.weight(1f))
                SearchBar{text ->
                    searchState = text.isEmpty()
                    if (searchState){
                        // buttons.get(selectedButtonIndex)을 사용해서 api 호출
                        ItemList.clear()
                        ItemList.addAll(Array(4){0})
                    }else{
                        // text로 검색하는 api 호출

                        // test용 내용. 지워야함
                        ItemList.clear()
                        ItemList.addAll(Array(text.length){0})
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                FavoriteListButton() { navController.navigate("storeLikeList") }
                Spacer(modifier = Modifier.weight(1f))
                ShoppingCart() { navController.navigate("storeShoppingCart") }
            }
            Divider(color = Color.Black, thickness = 1.dp)

            if (searchState) {
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
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .padding(top = 5.dp),
                columns = GridCells.Fixed(2),
            ) {
                items(ItemList) { post ->
                    productFrame(post, navController, "store")
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

        BasicTextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
                onSearch(newText)
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
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