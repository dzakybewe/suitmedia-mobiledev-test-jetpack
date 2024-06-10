package com.bewe.suitmediamobiledev.ui.screens.third

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bewe.suitmediamobiledev.ViewModelFactory
import com.bewe.suitmediamobiledev.common.UiState
import com.bewe.suitmediamobiledev.data.remote.model.DataItem
import com.bewe.suitmediamobiledev.di.Injection
import com.bewe.suitmediamobiledev.ui.theme.ButtonBgColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreen(
    modifier: Modifier = Modifier,
    viewModel: ThirdScreenViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Third Screen",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->

        val uiState by viewModel.result.collectAsState()
        val listItems by viewModel.list.collectAsState()
        val isMoreDataAvailable by viewModel.isMoreDataAvailable.collectAsState()

        fun loadNextPage() {
            viewModel.viewModelScope.launch {
                viewModel.loadNextPage()
            }
        }

        when (uiState) {
            is UiState.Loading -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = ButtonBgColor)
                    Text("Loading...")
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Please check your internet connection")
                }
            }

            is UiState.Success -> {
                ThirdScreenContent(
                    modifier = Modifier.padding(innerPadding),
                    users = listItems,
                    onItemClick = onItemClick,
                    isMoreDataAvailable = isMoreDataAvailable,
                    loadNextPage = { loadNextPage() }
                )
            }
        }
    }
}

@Composable
fun ThirdScreenContent(
    modifier: Modifier = Modifier,
    users: List<DataItem>,
    onItemClick: (String) -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
    isMoreDataAvailable: Boolean,
    loadNextPage: () -> Unit,
) {

    /* Check if reach the bottom or not */
    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.last()
                val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset

                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount &&
                        lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
            }
        }
    }

    /* If there is still data that can be fetch, load more data */
    LaunchedEffect(key1 = isAtBottom, key2 = isMoreDataAvailable) {
        if (isAtBottom && isMoreDataAvailable) {
            loadNextPage()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(users) { user ->
            ItemRow(
                modifier = Modifier.clickable {
                    onItemClick("${user.firstName} ${user.lastName}")
                },
                dataItem = user
            )
        }
        item {
            if (isMoreDataAvailable) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ButtonBgColor)
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No more data available")
                }
            }
        }
    }
}

@Composable
fun ItemRow(
    modifier: Modifier = Modifier,
    dataItem: DataItem,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = dataItem.avatar,
            contentDescription = "User's Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier,
        ) {
            Text(
                text = "${dataItem.firstName} ${dataItem.lastName}",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = dataItem.email,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = Color.Gray

            )
        }
    }
}
