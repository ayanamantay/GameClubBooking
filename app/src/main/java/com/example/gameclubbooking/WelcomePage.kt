import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gameclubbooking.R


@Preview()
@Composable
    fun Screens(){
        //OnboardingScreen()
        //WelcomeScreen()
        //CreateAccountPart1Screen()
        //CreateAccountPart2Screen()
//        ForgowPasPage()
    }

// 1. Onboarding Screen
@Composable
fun OnboardingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.DarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       Image(
           painter = painterResource(id = R.drawable.photo_2025_04_27_00_55_03),
           contentDescription = null,
           modifier = Modifier.size(140.dp)
       )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "UNIGAME is a next-gen gaming platform connecting gamers, clubs, and tournament organizers in KAZAKHSTAN. We make seat booking, esports tournaments, and club promotions effortless, all in one place.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.White,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Normal
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {  navController.navigate("welcome") }) {
            Text("GET START")
        }
    }
}

// 2. Welcome Screen
@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Welcome",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            "Happy to see you",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(34.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Email address") },
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Password") },
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text("Forgot password?", modifier = Modifier.align(Alignment.End),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(34.dp))

        Button(
            onClick = {                navController.navigate("main") // Login logic complete
            },
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(44.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                modifier = Modifier
                    .weight(3f)
                    .height(2.dp)
                    .padding(horizontal = 20.dp)

            )
            Text(
                text = "or login with",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Divider(
                modifier = Modifier
                    .weight(3f)
                    .height(2.dp)
                    .padding(horizontal = 20.dp)
            )
        }
        Spacer(modifier = Modifier.height(44.dp))

        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.size(width = 140.dp, height = 44.dp)
            ) {
                Text("Google")
            }
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.size(width = 140.dp, height = 44.dp)
            ) {
                Text("Facebook")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Do not have an account? ",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 36.dp)
            )
            TextButton(
                onClick = {                     navController.navigate("create_account")
                },
                modifier = Modifier.width(118.dp)
            ) {
                Text("Sign up Now",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp)
            }
        }
    }
}

// 3. Create Account - Part 1 (Name, Surname, Gender, Phone, Age)
@Composable
fun CreateAccountPart1Screen() {
    var gender by remember { mutableStateOf("Male") }
    var expandedDay by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf("Day") }

    var expandedMonth by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf("Month") }

    var expandedYear by remember { mutableStateOf(false) }
    var selectedYear by remember { mutableStateOf("Year") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Create Account",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            "To get started now!",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(34.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Name") },
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Surname") },
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(34.dp))

        Text(
            "GENDER", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Row(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            RadioButton(
                selected = gender == "Male",
                onClick = { gender = "Male" }
            )
            Text(
                "Male",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(20.dp))
            RadioButton(
                selected = gender == "Female",
                onClick = { gender = "Female" }
            )
            Text(
                "Female", fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            "AGE",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f)) {
                OutlinedButton(
                    onClick = { expandedDay = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedDay)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = expandedDay, onDismissRequest = { expandedDay = false }) {
                    (1..31).forEach { day ->
                        DropdownMenuItem(
                            text = { Text(day.toString()) },
                            onClick = {
                                selectedDay = day.toString()
                                expandedDay = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(modifier = Modifier.weight(1f)) {
                OutlinedButton(
                    onClick = { expandedMonth = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedMonth)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(
                    expanded = expandedMonth,
                    onDismissRequest = { expandedMonth = false }) {
                    listOf(
                        "January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December"
                    ).forEach { month ->
                        DropdownMenuItem(
                            text = { Text(month) },
                            onClick = {
                                selectedMonth = month
                                expandedMonth = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(modifier = Modifier.weight(1f)) {
                OutlinedButton(
                    onClick = { expandedYear = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedYear)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = expandedYear, onDismissRequest = { expandedYear = false }) {
                    (1960..2025).forEach { year ->
                        DropdownMenuItem(
                            text = { Text(year.toString()) },
                            onClick = {
                                selectedYear = year.toString()
                                expandedYear = false
                            }
                        )
                    }
                }
            }
        }
            Spacer(modifier = Modifier.height(36.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text(
                        "Continue",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.size(width = 90.dp, height = 28.dp)
                    )
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(
                        "Skip",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.size(width = 90.dp, height = 28.dp)
                    )
                }
            }
        }
    }

// 4. Create Account - Part 2 (Email, Password, Confirm Password)
@Composable
fun CreateAccountPart2Screen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Create Account",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            "To get started now!",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Name") },
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Surname") },
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Confirm Password") },
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        )


        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(width = 400.dp, height = 44.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text("Register")
        }
        }
    }

    @Composable
        fun ForgowPasPage(){
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "Forgot Password",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                "Write your email, we will send you a link to reset your password",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Email") },
                modifier = Modifier.size(width = 400.dp, height = 44.dp)
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(width = 400.dp, height = 44.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Text("Send link")
            }
        }
}