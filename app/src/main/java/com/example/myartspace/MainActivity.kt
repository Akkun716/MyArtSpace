package com.example.myartspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myartspace.ui.theme.MyArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}


@Composable
fun ArtSpaceApp() {
    val artPieceTotal = 3
    var pieceIndex by remember { mutableStateOf(1) }
    var showDescription by remember{ mutableStateOf(false) }
    var imageResource by remember{ mutableStateOf(R.drawable.dice_3) }
    var contentDescription by remember{ mutableStateOf(R.string.art_1_content_description) }
    var artTitle by remember{ mutableStateOf(R.string.art_1_title) }
    val creator by remember{ mutableStateOf(R.string.art_creator) }
    val dateCreated by remember{ mutableStateOf(R.string.art_date) }
    var imageDescription by remember{ mutableStateOf(R.string.art_1_description) }

    val switchContent: (Int) -> Unit = {index ->
        imageResource = when(index) {
            1 -> R.drawable.dice_3
            2 -> R.drawable.lemon_drink
            else -> R.drawable.lemon_tree
        }
        contentDescription = when(index) {
            1 -> R.string.art_1_content_description
            2 -> R.string.art_2_content_description
            else -> R.string.art_3_content_description
        }
        artTitle = when(index) {
            1 -> R.string.art_1_title
            2 -> R.string.art_2_title
            else -> R.string.art_3_title
        }
        imageDescription = when(index) {
            1 -> R.string.art_1_description
            2 -> R.string.art_2_description
            else -> R.string.art_3_description
        }
    }

    Box {
        Column(
            modifier = Modifier.fillMaxSize(1f),
            verticalArrangement = Arrangement.Center
        ) {
            ImageWithTitleAndInfo(
                showDescription = showDescription,
                onCheckedChange = { showDescription = it },
                imageResource = imageResource,
                contentDescription = contentDescription,
                creator = creator,
                title = artTitle,
                dateCreated = dateCreated,
                imageDescription = imageDescription
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(3.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = {
                    pieceIndex = when(pieceIndex) {
                        1 -> 3
                        else -> pieceIndex - 1
                    }
                    switchContent(pieceIndex)
                }
            ) {
                Text(text = stringResource(R.string.button_prev))
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End),
                onClick = {
                    pieceIndex = when(pieceIndex) {
                        artPieceTotal -> 1
                        else -> pieceIndex + 1
                    }
                    switchContent(pieceIndex)
                }
            ) {
                Text(text = stringResource(R.string.button_next))
            }
        }
    }
}

@Composable
fun ShowDescriptionRow(
    showDescription: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.show_description)
        )
        Switch(
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray,
                uncheckedTrackColor = Color.Gray,
                checkedThumbColor = Color.Green,
                checkedTrackColor = Color.LightGray
            ),
            checked = showDescription,
            onCheckedChange = onCheckedChange,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

@Composable
fun ImageWithTitleAndInfo(
    showDescription: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    imageResource: Int,
    contentDescription: Int,
    creator: Int,
    title: Int,
    dateCreated: Int,
    imageDescription: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = stringResource(id = contentDescription)
        )
        Spacer(modifier.height(8.dp))
        Text(
            text = stringResource(id = title),
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = stringResource(
                id = R.string.art_info_template,
                stringResource(id = creator),
                stringResource(id = dateCreated)
            )
        )
        ShowDescriptionRow(
            showDescription = showDescription,
            onCheckedChange = onCheckedChange
        )
        if(showDescription) {
            Text(
                text = stringResource(id = imageDescription)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyArtSpaceTheme {
        ArtSpaceApp()
    }
}