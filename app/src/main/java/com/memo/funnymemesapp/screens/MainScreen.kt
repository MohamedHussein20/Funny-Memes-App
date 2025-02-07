package com.memo.funnymemesapp.screens

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.memo.funnymemesapp.models.Meme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    memesList: List<Meme>,
    navController: NavHostController
) {
    Column(modifier = modifier.fillMaxSize()) {
        val textState = remember {
            mutableStateOf(TextFieldValue())
        }
        SearchView(
            state = textState,
            placeholder = "Search here ...",
            modifier = modifier
        )
        val searchText = textState.value.text
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(items = memesList.filter {
                it.name.contains(searchText, ignoreCase = true)
            }, key = { it.id }){ item ->
                MemeItem(
                    itemName = item.name,
                    itemUrl = item.url,
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun MemeItem(
    itemName: String,
    itemUrl: String,
    navController: NavHostController
) {
    Card (
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .clickable {
                navController.navigate("Detail Screen?name=$itemName&url=$itemUrl")
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffffc107)
        )
    ){
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(model = itemUrl, contentDescription = itemName,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = itemName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .basicMarquee()
                )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}
@Composable
fun SearchView(
    state: MutableState<TextFieldValue>,
    placeholder: String,
    modifier: Modifier
) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(10.dp)),
        placeholder = {
            Text(text = placeholder)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xffffc107),
            focusedContainerColor = Color(0xffffc107)
        ),
        singleLine = true,
        maxLines = 1,
        textStyle = TextStyle(color = Color.Black)
    )
}