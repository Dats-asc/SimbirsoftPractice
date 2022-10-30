@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.simbirsoftpracticeapp.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simbirsoftpracticeapp.R

class AuthComposableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    AuthComposable()
                }
            }
        }
    }
}

@Composable
fun Toolbar() {
    val toolbarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = Colors.Leaf,
        navigationIconContentColor = Color.White,
        titleContentColor = Color.White
    )
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.authorization),
                fontFamily = FontFamily(Font(R.font.officinasansextraboldscc)),
                fontSize = 21.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO: back arrow handle*/ }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Назад")
            }
        },
        colors = toolbarColors
    )
}

@Composable
fun Body() {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var signInButtonIsEnabled by remember {
        mutableStateOf(false)
    }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(
                text = stringResource(R.string.auth_msg),
                style = TextStyle(
                    color = Colors.Black70,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp, 40.dp, 32.dp, 20.dp)
                    .background(Color.White, RectangleShape)
            )
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_vk_logo),
                            contentDescription = ""
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_fb_logo),
                            contentDescription = "",
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_ok_logo),
                            contentDescription = ""
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.or_auth_with_app),
                style = TextStyle(
                    color = Colors.Black70,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                EmailTextField(onTextChanged = { text ->
                    email = text
                    signInButtonIsEnabled = email.length >= 6 && password.length >= 6
                })
                Spacer(modifier = Modifier.height(20.dp))
                PasswordTextField(onTextChanged = { text ->
                    password = text
                    signInButtonIsEnabled = email.length >= 6 && password.length >= 6
                })
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
            SignInButton(isEnabled = signInButtonIsEnabled)
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                item { LinkLikeText(text = stringResource(R.string.forgot_password), link = "") }
                item { LinkLikeText(text = stringResource(R.string.registration), link = "") }
            }
        }
    }

}

@Composable
fun EmailTextField(onTextChanged: (String) -> Unit) {
    val title = stringResource(R.string.email)
    val placeholder = stringResource(R.string.email_hint)
    var input by remember { mutableStateOf("") }
    var placeholderVisibility by remember { mutableStateOf(false) }

    Column {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 12.sp,
                color = Colors.Black38,
                fontFamily = FontFamily(Font(R.font.roboto))
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 4.dp, 0.dp, 8.dp)
        ) {
            if (placeholderVisibility) {
                Text(
                    text = placeholder,
                    style = TextStyle(color = Colors.Black38, fontSize = 16.sp),
                )
            }
            BasicTextField(
                value = input,
                onValueChange = { newInput ->
                    input = newInput
                    onTextChanged(newInput)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            placeholderVisibility = false
                        } else {
                            placeholderVisibility = input.isEmpty()
                        }
                    },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.Black12)
        )
    }
}

@Composable
fun PasswordTextField(onTextChanged: (String) -> Unit) {
    val title = stringResource(R.string.password)
    val placeholder = stringResource(R.string.password_hint)
    var input by remember { mutableStateOf("") }
    var placeholderVisibility by remember { mutableStateOf(false) }

    Column {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 12.sp,
                color = Colors.Black38,
                fontFamily = FontFamily(Font(R.font.roboto))
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 4.dp, 0.dp, 8.dp)
        ) {
            if (placeholderVisibility) {
                Text(
                    text = placeholder,
                    style = TextStyle(color = Colors.Black38, fontSize = 16.sp),
                )
            }
            BasicTextField(
                value = input,
                onValueChange = { newInput ->
                    input = newInput
                    onTextChanged(newInput)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            placeholderVisibility = false
                        } else {
                            placeholderVisibility = input.isEmpty()
                        }
                    },
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
            )
            Box(modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { /*TODO*/ }) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.ic_open_eye),
                            contentDescription = "Show password",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.ic_closed_eye),
                            contentDescription = "Hide password",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.Black12)
        )
    }
}

@Composable
fun SignInButton(isEnabled: Boolean) {
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = Colors.Leaf,
        disabledContainerColor = Color.LightGray,

        )
    Button(
        onClick = { /*TODO*/ },
        enabled = isEnabled,
        shape = RoundedCornerShape(2.dp),
        colors = buttonColors,
        modifier = Modifier.fillMaxWidth(),

        ) {
        Text(text = "ВОЙТИ")
    }
}

@Composable
fun LinkLikeText(text: String, link: String) {
    ClickableText(
        text = AnnotatedString(
            text = text,
            paragraphStyle = ParagraphStyle(),
            spanStyle = SpanStyle(
                color = Colors.Leaf,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            )
        ),
        onClick = {/*TODO: handle link*/ }
    )
}

@Composable
@Preview(showBackground = true)
fun AuthComposable() {
    Column {
        Toolbar()
        Body()
    }
}