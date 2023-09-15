package com.amonteiro.a23_11_sparks.composeexo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amonteiro.a23_11_sparks.composeexo.ui.theme._23_11_sparksTheme
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _23_11_sparksTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting()
                }
            }
        }
    }
}

/* -------------------------------- */
// //jeu de donnée
/* -------------------------------- */
const val LONG_TEXT =
    "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500"

data class PictureData(val url: String, val text: String, val longText: String = LONG_TEXT)

val myList = arrayListOf(
    PictureData("https://picsum.photos/200", "ABCD"),
    PictureData("https://picsum.photos/201", "BCDE"),
    PictureData("https://picsum.photos/202", "CDEF"),
    PictureData("https://picsum.photos/203", "EFGH")
)

/* -------------------------------- */
// Preview
/* -------------------------------- */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    _23_11_sparksTheme {
        Greeting()
    }
}


/* -------------------------------- */
// Composable
/* -------------------------------- */
@Composable
fun testRemember() {
    val expanded = remember { mutableStateOf(false) }
    val extraWidth by animateDpAsState(if (expanded.value) 150.dp else 100.dp)

    Column {
        ElevatedButton(
            onClick = { expanded.value = !expanded.value },
            modifier = Modifier.widthIn(min = extraWidth)
        ) {
            Text(if (expanded.value) "Show less" else "Show more")
        }

        ElevatedButton(
            onClick = { expanded.value = !expanded.value },
        ) {
            Text(if (expanded.value) "Show less" else "Show more")
        }

        MyButton(expanded)
        MyButton()
    }
}

//Permet d'écouter l'état en dehors de la méthode
@Composable
fun MyButton(expanded: MutableState<Boolean> = remember { mutableStateOf(false) }) {
    ElevatedButton(
        onClick = { expanded.value = !expanded.value },
    ) {
        Text(if (expanded.value) "Show less" else "Show more")
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val searchText = remember { mutableStateOf("") }
    val filterList = myList.filter { it.text.contains(searchText.value) }

    Column(modifier = modifier.padding(8.dp)) {
        SearchBar(textValue =  searchText)
        Spacer(Modifier.size(8.dp))
        LazyColumn(modifier = Modifier.weight(1F), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filterList.size) {
                PictureRowItem(data = filterList[it])
            }
        }
        Button(
            onClick = { searchText.value = "" },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Icon(
                Icons.Filled.Done,
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Clear Filter")

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier, textValue: MutableState<String>) {
    TextField(
        value = textValue.value,
        onValueChange = { newValue ->
            textValue.value = newValue
        }, //Action
        leadingIcon = { //Image d'icone
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        label = { Text("") }, //valeur par défaut
        placeholder = { //Texte d'aide
            Text("Votre recherche ici")
        },
        //Comment le composant doit se placer
        modifier = modifier
            .fillMaxWidth() // Prend toute la largeur
            .heightIn(min = 56.dp) //Hauteur minimum
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PictureRowItem(
    modifier: Modifier = Modifier,
    data: PictureData
) {
    var isExpanded by remember { mutableStateOf(false) }
    val displayedText = if (isExpanded) data.longText else (data.longText.take(20) + "...")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.background(Color.Gray)
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        GlideImage(
            model = data.url,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(100.dp)
        )

        Column(
            Modifier
                .weight(1F)
                .padding(8.dp)) {
            Text(
                text = data.text,
                fontSize = 20.sp,
                lineHeight = 116.sp, //hauteur
                textAlign = TextAlign.Center,
                color = Color.Black,
            )

            Text(
                text = displayedText,
                fontSize = 14.sp,
                color = Color.Blue,
                modifier = Modifier.animateContentSize()
            )
        }
    }

}




