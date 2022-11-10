package com.ianpedraza.dogedex.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.ui.composables.ErrorDialog
import com.ianpedraza.dogedex.ui.composables.LoadingWheel
import com.ianpedraza.dogedex.utils.DataState

private const val GRID_SPAN_COUNT = 3

@ExperimentalMaterialApi
@Composable
fun DogListScreen(
    onAction: (Action) -> Unit,
    // I'm not a big fan of this, because the own composable edits the states
    viewModel: DogsListViewModel = hiltViewModel()
) {
    val dataState = viewModel.dogsList.value

    if (dataState is DataState.Success) {
        /*LazyColumn {
            items(dataState.data) {
                DogItem(dog = it, onAction = onAction)
            }
        }*/

        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            content = {
                items(dataState.data) {
                    DogGridItem(dog = it, onAction = onAction)
                }
            }
        )
    }

    if (dataState is DataState.Loading) {
        LoadingWheel()
    }

    if (dataState is DataState.Error) {
        ErrorDialog(dataState.error) {
            viewModel.reset()
        }
    }

    /*Scaffold(
        topBar = { DogListScreenTopBar(onNavigationIconClick) }
    ) { paddingValues ->
        if (dataState is DataState.Success) {
            LazyVerticalGrid(
                modifier = Modifier.padding(paddingValues),
                columns = GridCells.Fixed(GRID_SPAN_COUNT),
                content = {
                    items(dataState.data) {
                        DogGridItem(dog = it, onAction = onAction)
                    }
                }
            )
        }
    }*/
}

@Composable
private fun DogListScreenTopBar(onClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "My Dog Collection") },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = { BackNavigationIcon(onClick) }
    )
}

@Composable
private fun BackNavigationIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = rememberVectorPainter(image = Icons.Sharp.ArrowBack),
            contentDescription = stringResource(R.string.back_button)
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun DogGridItem(dog: Dog, onAction: (Action) -> Unit) {
    if (dog.inCollection) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            onClick = { onAction(Action.OnClick(dog)) },
            shape = RoundedCornerShape(4.dp)
        ) {
            AsyncImage(
                modifier = Modifier.background(Color.White),
                model = dog.imageUrl,
                contentDescription = dog.name
            )
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .aspectRatio(1f),
            color = colorResource(id = R.color.secondary_background),
            shape = RoundedCornerShape(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dog.index.toString(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}

@Composable
private fun DogItem(dog: Dog, onAction: (Action) -> Unit) {
    if (dog.inCollection) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onAction(Action.OnClick(dog)) },
            text = dog.name
        )
    } else {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.Red),
            text = stringResource(id = R.string.format_index, dog.index)
        )
    }
}

sealed class Action {
    data class OnClick(val dog: Dog) : Action()
}
