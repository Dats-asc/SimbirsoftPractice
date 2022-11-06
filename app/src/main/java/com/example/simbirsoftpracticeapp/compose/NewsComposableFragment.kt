@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.simbirsoftpracticeapp.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.fragment.app.Fragment
import com.example.simbirsoftpracticeapp.R
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent

class NewsComposableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                }
            }
        }
    }
}

@Composable
fun NewsScreen() {
    androidx.compose.material3.Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            // Toolbar
            NewsToolbar()

            //Content
            NewsBody()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NewsComposablePreview() {
    NewsScreen()
}

@Composable
fun NewsToolbar() {
    val toolbarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Colors.Leaf,
        navigationIconContentColor = Color.White,
        titleContentColor = Color.White
    )
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Новости",
                fontFamily = FontFamily(Font(R.font.officinasansextraboldscc)),
                fontSize = 21.sp
            )
        },
        colors = toolbarColors,
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Фильтрация",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun NewsBody() {
    val events = listOf(
        CharityEvent(
            0,
            "test",
            "2-10.2022",
            "Test",
            "adress",
            listOf("8800222132"),
            "",
            "",
            "",
            "desc",
            listOf(),
            53,
            "",
            "",
            0
        ),
        CharityEvent(
            1,
            "test",
            "2-10.2022",
            "Test",
            "adress",
            listOf("8800222132"),
            "",
            "",
            "",
            "desc",
            listOf(),
            53,
            "",
            "",
            0
        ),
        CharityEvent(
            3,
            "test",
            "2-10.2022",
            "Test",
            "adress",
            listOf("8800222132"),
            "",
            "",
            "",
            "desc",
            listOf(),
            53,
            "",
            "",
            0
        )
    )
    Column(modifier = Modifier.padding(4.dp)) {
        NewsCardList(events)
    }
}

@Composable
fun NewsCardList(events: List<CharityEvent>) {
    LazyColumn {
        events.forEach { event ->
            item { NewsCard(event) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun NewsCard(event: CharityEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(2.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_fade),
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize()
                        .zIndex(0.2f),
                )
                Image(
                    painter = painterResource(id = R.drawable.card_example),
                    contentDescription = "",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(0.1f)
                )
                Text(
                    text = event.title,
                    style = TextStyle(
                        fontSize = 21.sp,
                        color = Colors.BlueGray,
                        fontFamily = FontFamily(Font(R.font.officinasansextraboldscc)),
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(32.dp, 0.dp)
                        .zIndex(0.3f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_card_separator),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = event.description,
                style = TextStyle(
                    color = Colors.Black38,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp, 0.dp)
            )

            val cardButtonColors = ButtonDefaults.buttonColors(
                containerColor = Colors.Leaf,
                contentColor = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = cardButtonColors,
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(0.dp, 0.dp, 4.dp, 4.dp),
            ) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    item {
                        Text(
                            text = event.date,
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.roboto))
                            )
                        )
                    }
                }
            }
        }
    }
}