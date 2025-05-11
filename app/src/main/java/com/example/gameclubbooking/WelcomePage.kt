
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.gameclubbooking.FirebaseAuthManager
import com.example.gameclubbooking.R
import com.example.gameclubbooking.SharedRegistrationViewModel
import com.example.gameclubbooking.UserProfile
import com.example.gameclubbooking.ui.theme.PoppinsFontFamily
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
                Text(
                    text = "GET STARTED", color = Color.White,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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

        CustomInputField(label = "Email address", text = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))


        CustomInputField(label = "Password", text = password, onValueChange = { password = it })
        Spacer(modifier = Modifier.height(16.dp))


        Text(
            "Forgot password?",
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = PoppinsFontFamily,
            modifier = Modifier
                .clickable { navController.navigate("forgot_password") }
                .fillMaxWidth()
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                FirebaseAuthManager.login(
                    email = email,
                    password = password,
                    onSuccess = { navController.navigate("main") },
                    onError = { error -> errorMessage = error }
                )
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
                "Login",
                fontFamily = PoppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        errorMessage?.let {
            Text(
                it,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
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
            TextButton(onClick = { navController.navigate("create_account2") }) {
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
fun CreateAccountPart1Screen(
    navController: NavController,
    registrationViewModel: SharedRegistrationViewModel = viewModel()
) {
    val genderOptions = listOf("Male", "Female")
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFF101828))
            .padding(horizontal = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Profile Information",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign  = TextAlign.Center,
            fontFamily = PoppinsFontFamily,
            modifier = Modifier.padding( vertical = 20.dp, horizontal = 80.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        CustomInputField(
            label = "Name",
            text = registrationViewModel.name,
            onValueChange = { registrationViewModel.name = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomInputField(
            label = "Surname",
            text = registrationViewModel.surname,
            onValueChange = { registrationViewModel.surname = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomInputField(
            label = "Phone Number",
            text = registrationViewModel.phoneNumber,
            onValueChange = { registrationViewModel.phoneNumber = it },
            keyboardType = KeyboardType.Phone
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomInputField(
            label = "City and Address",
            text = registrationViewModel.cityAndAddress,
            onValueChange = { registrationViewModel.cityAndAddress = it }
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Gender",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontFamily = PoppinsFontFamily
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            genderOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    RadioButton(
                        selected = registrationViewModel.gender == option,
                        onClick = { registrationViewModel.gender = option },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text(
                        text = option,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontFamily = PoppinsFontFamily
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Birthday",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            fontFamily = PoppinsFontFamily
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            DropdownSelector(
                label = registrationViewModel.selectedDay,
                items = (1..31).map { it.toString() },
                onItemSelected = { registrationViewModel.selectedDay = it },
                modifier = Modifier.weight(1f)
            )
            DropdownSelector(
                label = registrationViewModel.selectedMonth,
                items = listOf(
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
                ),
                onItemSelected = { registrationViewModel.selectedMonth = it },
                modifier = Modifier.weight(1f)
            )
            DropdownSelector(
                label = registrationViewModel.selectedYear,
                items = (1960..2025).map { it.toString() },
                onItemSelected = { registrationViewModel.selectedYear = it },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    val userProfile = UserProfile(
                        name = registrationViewModel.name,
                        surname = registrationViewModel.surname,
                        phoneNumber = registrationViewModel.phoneNumber,
                        gender = registrationViewModel.gender,
                        cityAndAddress = registrationViewModel.cityAndAddress,
                        birthDay = registrationViewModel.selectedDay.toIntOrNull() ?: 1,
                        birthMonth = monthStringToNumber(registrationViewModel.selectedMonth),
                        birthYear = registrationViewModel.selectedYear.toIntOrNull() ?: 2000
                    )

                    FirebaseAuthManager.uploadUserProfile(userId, userProfile,
                        onSuccess = {
                            navController.navigate("main")
                        },
                        onError = { error ->
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Save Profile",
                color = Color(0xFF101828),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily
            )
        }
    }
}

fun monthStringToNumber(month: String): Int {
    return listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ).indexOf(month) + 1
}

@Composable
fun CustomInputField(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = Color.White,
                fontFamily = PoppinsFontFamily
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 8.dp),
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
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var confirmPassword by remember { mutableStateOf("") }


    CustomInputField(label = "Email address", text = email, onValueChange = { email = it })
    CustomInputField(label = "Password", text = password, onValueChange = { password = it })

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

        CustomInputField(
            label = "Email",
            text = email,
            onValueChange = { email = it },
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(20.dp))

        CustomInputField(
            label = "Password",
            text = password,
            onValueChange = { password = it },
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.height(20.dp))

        CustomInputField(
            label = "Confirm Password",
            text = confirmPassword,
            onValueChange = { confirmPassword = it },
            keyboardType = KeyboardType.Password
        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match"
                } else {
                    FirebaseAuthManager.register(
                        email = email,
                        password = password,
                        onSuccess = {
                            navController.navigate("create_account1")
                        },
                        onError = { error -> errorMessage = error }
                    )
                }
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


        errorMessage?.let {
            Text(it, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ForgowPasPage(navController: NavHostController) {
    val scrollState = rememberScrollState()
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

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

        CustomInputField(
            label = "Email",
            text = email,
            onValueChange = { email = it },
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                FirebaseAuthManager.sendPasswordReset(
                    email = email,
                    onSuccess = {
                        message = "Reset link sent successfully!"
                        coroutineScope.launch {
                            delay(2000)
                            navController.navigate("welcome") {
                                popUpTo("forgot_password") { inclusive = true }
                            }
                        }
                    },
                    onError = { error ->
                        message = error
                    }
                )
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

        message?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                color = if (it.contains("success", ignoreCase = true)) Color.Green else Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}


