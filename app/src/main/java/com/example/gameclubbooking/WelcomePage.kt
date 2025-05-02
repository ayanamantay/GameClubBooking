import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gameclubbooking.R
import com.example.gameclubbooking.ui.theme.PoppinsFontFamily


@Preview()
@Composable
    fun Screens(){
        val navController = rememberNavController()
//        OnboardingScreen(navController)
//        WelcomeScreen(navController)
//        CreateAccountPart1Screen()
//        CreateAccountPart2Screen(navController)
//        ForgowPasPage(navController)
    }
@Composable
fun OnboardingScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF061C2A))
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "UNIGAME Logo",
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
//
//            Text(
//                text = "UNIGAME",
//                fontSize = 28.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.White,
//                modifier = Modifier.padding(top = 16.dp)
//            )

            Text(
                text = "A next-gen gaming platform connecting gamers, clubs, and tournaments in Kazakhstan.\n\n" +
                        "Book seats, join esports events, and discover club deals — all in one place.",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)
            )


            Button(
                onClick = { navController.navigate("welcome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Text(text = "GET STARTED",   color = Color.White,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF061C2A))
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Welcome",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = PoppinsFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Happy to see you",
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        CustomInputField(label = "Email address")
        Spacer(modifier = Modifier.height(16.dp))


        CustomInputField(label = "Password")
        Spacer(modifier = Modifier.height(16.dp))


        Text(
            "Forgot password?",
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = PoppinsFontFamily,
            modifier = Modifier
                .clickable {navController.navigate("forgot_password") }
                .fillMaxWidth()
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("main") },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        ) {
            Text(
                "Login",
                fontFamily = PoppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Text(
                text = "  or login with  ",
                fontFamily = PoppinsFontFamily,
                fontSize = 14.sp,
                color = Color.White
            )
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("Google", fontFamily = PoppinsFontFamily)
            }
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text("Facebook", fontFamily = PoppinsFontFamily)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Don't have an account? ",
                fontFamily = PoppinsFontFamily,
                fontSize = 14.sp,
                color = Color.White
            )
            TextButton(onClick = { navController.navigate("create_account") }) {
                Text(
                    "Sign up Now",
                    fontFamily = PoppinsFontFamily,
                    fontSize = 14.sp,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}


@Composable
fun CreateAccountPart1Screen(navController: NavController? = null) {
    val genderOptions = listOf("Male", "Female")
    var gender by remember { mutableStateOf("Male") }

    val dayList = (1..31).map { it.toString() }
    val monthList = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )
    val yearList = (1960..2025).map { it.toString() }

    var selectedDay by remember { mutableStateOf("Day") }
    var selectedMonth by remember { mutableStateOf("Month") }
    var selectedYear by remember { mutableStateOf("Year") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFF061C2A))
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Create Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "To get started now!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 32.dp)
        )

        CustomInputField(label = "Name")
        Spacer(modifier = Modifier.height(16.dp))
        CustomInputField(label = "Surname")
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "GENDER",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row {
            genderOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 24.dp)
                ) {
                    RadioButton(
                        selected = gender == option,
                        onClick = { gender = option },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text(
                        text = option,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        CustomInputField(label = "Phone Number", keyboardType = KeyboardType.Phone)
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "AGE",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DropdownSelector(
                label = selectedDay,
                items = dayList,
                onItemSelected = { selectedDay = it },
                modifier = Modifier.weight(1f)
            )
            DropdownSelector(
                label = selectedMonth,
                items = monthList,
                onItemSelected = { selectedMonth = it },
                modifier = Modifier.weight(1f)
            )
            DropdownSelector(
                label = selectedYear,
                items = yearList,
                onItemSelected = { selectedYear = it },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { navController?.navigate("create_account2") },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Continue",
                fontFamily = PoppinsFontFamily
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { navController?.navigate("create_account2") },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            ),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Text(
                text = "Skip",
                fontFamily = PoppinsFontFamily
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
@Composable
fun CustomInputField(label: String, keyboardType: KeyboardType = KeyboardType.Text) {
    var value by remember { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        label = {
            Text(
                text = label,
                color = Color.White,
                fontFamily = PoppinsFontFamily
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.White,
            cursorColor = Color.White
        ),
        textStyle = TextStyle(
            color = Color.White,
            fontFamily = PoppinsFontFamily
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        singleLine = true
    )
}

@Composable
fun DropdownSelector(
    label: String,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            ),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Text(
                text = label,
                fontFamily = PoppinsFontFamily
            )
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(it, fontFamily = PoppinsFontFamily) },
                    onClick = {
                        onItemSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun CreateAccountPart2Screen(navController: NavHostController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFF061C2A))
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Create Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "To get started now!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 32.dp)
        )

        // Email
        CustomInputField(label = "Email", keyboardType = KeyboardType.Email)
        Spacer(modifier = Modifier.height(20.dp))

        // Password
        CustomInputField(label = "Password", keyboardType = KeyboardType.Password)
        Spacer(modifier = Modifier.height(20.dp))

        // Confirm Password
        CustomInputField(label = "Confirm Password", keyboardType = KeyboardType.Password)
        Spacer(modifier = Modifier.height(40.dp))

        // Register Button
        Button(
            onClick = {
                navController.navigate("main")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Register",
                fontFamily = PoppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ForgowPasPage(navController: NavHostController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFF061C2A))
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Forgot Password",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Write your email, we will send you a link to reset your password",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Email Input
        CustomInputField(label = "Email", keyboardType = KeyboardType.Email)

        Spacer(modifier = Modifier.height(40.dp))

        // Send Link Button
        Button(
            onClick = {
                // TODO: implement action or navigate
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(16.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Send link",
                fontFamily = PoppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

