package com.ianpedraza.dogedex.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.framework.dummy.dogs.DummyData
import com.ianpedraza.dogedex.ui.composables.ErrorDialog
import com.ianpedraza.dogedex.ui.composables.LoadingWheel
import com.ianpedraza.dogedex.ui.composables.VerticalDivider
import com.ianpedraza.dogedex.utils.DataState

@Composable
fun DogDetailScreen(
    dog: Dog,
    dataState: DataState<Boolean>? = null,
    onButtonClicked: () -> Unit,
    onErrorDialogDismiss: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(id = R.color.secondary_background))
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        DogInformation(dog = dog)

        AsyncImage(
            modifier = Modifier.width(270.dp)
                .padding(top = 80.dp),
            model = dog.imageUrl,
            contentDescription = dog.name
        )

        FloatingActionButton(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            onClick = onButtonClicked
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = stringResource(id = R.string.check_button)
            )
        }

        if (dataState is DataState.Loading) {
            LoadingWheel()
        }

        if (dataState is DataState.Error) {
            ErrorDialog(dataState.error, onErrorDialogDismiss)
        }
    }
}

@Composable
private fun DogInformation(dog: Dog) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 180.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = colorResource(id = android.R.color.white)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.format_index, dog.index),
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.End
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                    text = dog.name,
                    color = colorResource(id = R.color.text_black),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium
                )

                LifeIcon()

                Text(
                    text = stringResource(id = R.string.format_life_expectancy, dog.lifeExpectancy),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black)
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = dog.temperament,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.text_black),
                    fontWeight = FontWeight.Medium
                )

                Divider(
                    modifier = Modifier.padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 16.dp
                    ),
                    color = colorResource(id = R.color.divider),
                    thickness = 1.dp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DogDataColumn(
                        modifier = Modifier.weight(1f),
                        genre = stringResource(id = R.string.female),
                        weight = dog.weightFemale,
                        height = dog.heightFemale
                    )

                    VerticalDivider()

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = dog.type,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.text_black),
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = stringResource(id = R.string.group),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.dark_gray)
                        )
                    }

                    VerticalDivider()

                    DogDataColumn(
                        modifier = Modifier.weight(1f),
                        genre = stringResource(id = R.string.male),
                        weight = dog.weightMale,
                        height = dog.heightMale
                    )
                }
            }
        }
    }
}

@Composable
private fun DogDataColumn(
    modifier: Modifier = Modifier,
    genre: String,
    weight: String,
    height: Double
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = genre,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.text_black)
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = weight,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.text_black),
            fontWeight = FontWeight.Medium
        )

        Text(
            text = stringResource(id = R.string.weight),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.dark_gray)
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),

            text = stringResource(id = R.string.format_height, height),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.text_black),
            fontWeight = FontWeight.Medium
        )

        Text(
            text = stringResource(id = R.string.height),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = colorResource(id = R.color.dark_gray)
        )
    }
}

@Composable
private fun LifeIcon() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(start = 80.dp, end = 80.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = colorResource(id = R.color.color_primary)
        ) {
            Icon(
                modifier = Modifier.width(24.dp).height(24.dp).padding(4.dp),
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = stringResource(id = R.string.icon_of_a_heart),
                tint = colorResource(id = R.color.white)
            )
        }

        Surface(
            shape = RoundedCornerShape(bottomEnd = 2.dp, topEnd = 2.dp),
            modifier = Modifier.width(200.dp).height(6.dp),
            color = colorResource(id = R.color.color_primary)
        ) {}
    }
}

@Preview
@Composable
fun DogDetailScreenPreview() {
    val dog = DummyData.getAllDogs().first()

    DogDetailScreen(
        dog,
        onButtonClicked = {},
        onErrorDialogDismiss = {}
    )
}
