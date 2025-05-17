package com.example.myapplication.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController // 🔹 bu import eklendi

@Composable
fun LoginScreen(navController: NavController, type: String) { // 🔹 navController eklendi
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val title = if (type == "corporate") "Kurumsal Giriş" else "Bireysel Giriş"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-posta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                println("Giriş yapıldı: $email / $password [type=$type]")
                navController.navigate("survey") // 🔹 yönlendirme artık çalışır
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Giriş Yap")
        }
    }
}
