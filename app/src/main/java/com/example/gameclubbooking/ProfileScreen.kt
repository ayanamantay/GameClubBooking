package com.example.gameclubbooking

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gameclubbooking.ui.theme.PoppinsFontFamily
import java.time.LocalDate
import java.time.Period


@SuppressLint("SuspiciousIndentation")
@Preview(showBackground = true)
@Composable
fun MyPreview() {
    val navController = rememberNavController()
//    ProfileScreen(navController)
//    YourProfileScreen(navController)
//        HelpCenterScreen(navController)
//        SettingsScreen(navController)
//        NotificationScreen(navController)
//        PasswordManagerScreen(navController)

//        InviteFriendsScreen(navController)
    PrivacyPolicyScreen(navController)
}

class UserInfoViewModel : ViewModel() {
    var userInfo by mutableStateOf(
        UserProfile("", "", "", "Male", 1, 1, 2000)
    )
        private set

    fun updateUserInfo(
        name: String,
        surname: String,
        phoneNumber: String,
        gender: String,
        email: String = "",
        cityAndAddress: String = ""
    ) {
        // No need to pass birth day, month, or year anymore
        userInfo = UserProfile(
            name = name,
            surname = surname,
            phoneNumber = phoneNumber,
            gender = gender,
            birthDay = 0, // Default value since birth date isn't saved
            birthMonth = 0, // Default value
            birthYear = 0, // Default value
            email = email,
            cityAndAddress = cityAndAddress
        )
    }
}

data class UserProfile(
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val gender: String,
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val email: String = "",
    val cityAndAddress: String = ""
) {
    val age: Int
        get() {
            val today = LocalDate.now()
            val birthDate = LocalDate.of(birthYear, birthMonth, birthDay)
            return Period.between(birthDate, today).years
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userInfoViewModel: UserInfoViewModel = viewModel()
) {
    val backgroundColor = Color(0xFF101828)
    val userInfo = userInfoViewModel.userInfo

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Avatar Circle
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                color = Color.Gray.copy(alpha = 0.2f)
            ) {
                // You can put Image(...) here later
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = userInfo.name + " " + userInfo.surname,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Profile Items
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileItem("Your Profile") { navController.navigate(Screen.YourProfile.route) }
                ProfileItem("Add Cards") { navController.navigate(Screen.AddCard.route) }
                ProfileItem("Settings") { navController.navigate(Screen.Settings.route) }
                ProfileItem("Help Center") { navController.navigate(Screen.HelpCenter.route) }
                ProfileItem("Privacy Policy") { navController.navigate(Screen.PrivacyPolicy.route) }
                ProfileItem("Log out") { navController.navigate("welcome") }
            }
        }
    }
}

@Composable
fun ProfileItem(title: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    ) {
        Divider(color = Color.Gray.copy(alpha = 0.3f))
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YourProfileScreen(
    navController: NavController,
    userInfoViewModel: UserInfoViewModel = viewModel()
) {
    val userInfo = userInfoViewModel.userInfo
    val backgroundColor = Color(0xFF101828)

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Your Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                color = Color.Gray.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Show only available data
            ProfileField("Name", userInfo.name)
            ProfileField("Surname", userInfo.surname)
            ProfileField("Phone", userInfo.phoneNumber)
            ProfileField("Gender", userInfo.gender)
            ProfileField("Age", userInfo.age.toString())
            if (userInfo.cityAndAddress.isNotBlank()) {
                ProfileField("Address", userInfo.cityAndAddress)
            }

            if (userInfo.email.isNotBlank()) {
                ProfileField("Email", userInfo.email)
            }

        }
    }
}


@Composable
fun ProfileField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = PoppinsFontFamily,
            color = Color.White.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFontFamily,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                .padding(12.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpCenterScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("FAQ", "Contact Us")
    val backgroundColor = Color(0xFF101828)

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Help Center",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(padding)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = backgroundColor,
                contentColor = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                fontFamily = PoppinsFontFamily
                            )
                        }
                    )
                }
            }

            when (selectedTab) {
                0 -> FAQScreen()
                1 -> ContactUsScreen()
            }
        }
    }
}

@Composable
fun ContactUsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101828))
            .padding(20.dp)
    ) {
        Text(
            "Need Help?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = PoppinsFontFamily,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "You can reach us through the following options:",
            fontSize = 16.sp,
            fontFamily = PoppinsFontFamily,
            color = Color.White.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(24.dp))

        ContactCard("Email Support", "support@gameclubbooking.com", Icons.Default.Email)
        Spacer(modifier = Modifier.height(16.dp))
        ContactCard("Phone", "+7 707 123 4567", Icons.Default.Phone)
        Spacer(modifier = Modifier.height(16.dp))
        ContactCard("Visit Us", "Al-Farabi Ave 71, Almaty, Kazakhstan", Icons.Default.Place)
    }
}

@Composable
fun ContactCard(title: String, subtitle: String, icon: ImageVector) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = PoppinsFontFamily,
                    color = Color.White
                )
                Text(
                    subtitle,
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }
        }
    }
}

@Composable
fun FAQScreen() {
    val faqList = listOf(
        "How do I book a game club?" to "Go to the club details page and click the 'Book Now' button. You’ll need to select a time and confirm your booking.",
        "Can I cancel my booking?" to "Yes, you can cancel your active booking from the 'My Orders' screen by selecting the order and tapping 'Cancel'.",
        "Do I need an account to book?" to "Yes, you must be logged in to book a game club.",
        "What payment methods are supported?" to "Currently, we support in-person payment at the club. Online payment will be available soon.",
        "How do I know if a club is available?" to "Available time slots are shown on each club’s detail page."
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101828))
            .padding(16.dp)
    ) {
        items(faqList) { (question, answer) ->
            FAQItem(question, answer)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = question,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.White
            )
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = answer,
                    fontSize = 14.sp,
                    fontFamily = PoppinsFontFamily,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}


const val SETTINGS_ROUTE = "settings"
const val NOTIFICATIONS_ROUTE = "notifications"
const val PASSWORD_MANAGER_ROUTE = "password_manager"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val backgroundColor = Color(0xFF101828)
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Settings",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(20.dp)) {
            SettingItem("Notification Settings", Icons.Default.Notifications) {
                navController.navigate(Screen.Notifications.route)
            }
            SettingItem("Password Manager", Icons.Default.Lock) {
                navController.navigate(Screen.PasswordManager.route)
            }
            SettingItem("Delete Account", Icons.Default.Delete, isDestructive = true) {
                showDeleteDialog = true
            }
        }
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        // Perform delete
                        showDeleteDialog = false
                    }) { Text("Delete", color = MaterialTheme.colorScheme.error) }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Delete Account?") },
                text = { Text("Are you sure you want to delete your account? This action cannot be undone.") }
            )
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    icon: ImageVector,
    isDestructive: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF101840)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(icon, contentDescription = title, tint = Color.White)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                title, style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PoppinsFontFamily,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    Scaffold(
        containerColor = Color(0xFF101828),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF101828)),
                title = {
                    Text(
                        "Notifications",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 80.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            item { SectionTitle("TODAY") }
            items(2) {
                NotificationItem("UQue seat is booked", "Your booking is confirmed.")
            }

            item { SectionTitle("YESTERDAY") }
            items(2) {
                NotificationItem("Reminder", "Don't miss your booking.")
            }

            item { SectionTitle("5 DECEMBER") }
            items(2) {
                NotificationItem("Welcome!", "Thanks for joining GameClub.")
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.LightGray,
        fontFamily = PoppinsFontFamily,
        modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun NotificationItem(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White)
        Spacer(Modifier.width(20.dp))
        Column {
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = PoppinsFontFamily,
                color = Color.White
            )
            Text(
                description,
                fontSize = 14.sp,
                fontFamily = PoppinsFontFamily,
                color = Color.LightGray
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerScreen(navController: NavController) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xFF101828),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF101828)),
                title = {
                    Text(
                        "Password Manager",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 80.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            PasswordField("Current Password", currentPassword) { currentPassword = it }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    "Forgot Password?",
                    fontSize = 14.sp,
                    color = Color.LightGray,
                    fontFamily = PoppinsFontFamily,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            PasswordField("New Password", newPassword) { newPassword = it }
            Spacer(Modifier.height(16.dp))

            PasswordField("Confirm New Password", confirmPassword) { confirmPassword = it }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { /* Update password logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    "Update Password",
                    color = Color.Black,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun PasswordField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                color = Color.White,
                fontFamily = PoppinsFontFamily
            )
        },
        textStyle = TextStyle(color = Color.White, fontFamily = PoppinsFontFamily),
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteFriendsScreen(navController: NavController) {
    val friends = listOf(
        "Adam" to "45212 526334",
        "Lisa" to "45212 526334",
        "Jungkook" to "45212 526334",
        "Jungkook" to "45212 526334",
        "Jungkook" to "45212 526334",
        "Jungkook" to "45212 526334",
        "Jungkook" to "45212 526334"
    )

    Scaffold(
        containerColor = Color(0xFF101828),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF101828)),
                title = {
                    Text(
                        "Invite Friends",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 70.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            items(friends) { (name, number) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = name,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = PoppinsFontFamily
                        )
                        Text(
                            text = number,
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            fontFamily = PoppinsFontFamily
                        )
                    }
                    Button(
                        onClick = { /* Invite logic */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("Invite", color = Color.Black, fontFamily = PoppinsFontFamily)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    val policyText = """
        **1. Introduction**

        GameClub Booking ("we", "our", or "us") values your privacy. This Privacy Policy explains how we collect, use, and protect your information when you use our app.

        **2. Information We Collect**

        - Name, phone number, email
        - Booking history and club preferences
        - Device and usage data

        **3. How We Use Your Information**

        - To manage your bookings and notify you
        - To personalize your experience
        - To improve our service and prevent misuse

        **4. Sharing Your Information**

        We do not sell your personal data. We may share information only:
        - With trusted club partners for booking purposes
        - If required by law

        **5. Data Security**

        We use industry-standard security measures to protect your data. You are responsible for keeping your password confidential.

        **6. Your Rights**

        You can:
        - Access or correct your data
        - Delete your account at any time

        **7. Updates**

        We may update this Privacy Policy. You will be notified of significant changes.

        **8. Contact**

        For questions, email us at support@gameclubapp.com
    """.trimIndent()

    Scaffold(
        containerColor = Color(0xFF101828),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF101828)),
                title = {
                    Text(
                        "Privacy Policy",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 60.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Text(
                text = policyText,
                fontSize = 14.sp,
                color = Color.White,
                fontFamily = PoppinsFontFamily
            )
        }
    }
}




