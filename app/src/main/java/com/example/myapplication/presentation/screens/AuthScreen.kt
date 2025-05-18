package com.example.myapplication.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavController
) {
    var isRegister by remember { mutableStateOf(false) }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var acceptTerms by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isRegister) "Kayıt Ol" else "Giriş Yap",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isRegister) {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                label = { Text("Ad Soyad") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            label = { Text("E-posta") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            label = { Text("Şifre") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        if (isRegister) {
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                label = { Text("Şifreyi Onayla") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                label = { Text("Telefon Numarası") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = acceptTerms,
                        onValueChange = { acceptTerms = it }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = acceptTerms,
                    onCheckedChange = null
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Kullanım Şartlarını ve Gizlilik Politikasını kabul ediyorum",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
            }
            Spacer(Modifier.height(16.dp))
        }

        Button(
            onClick = {
                // AuthScreen.kt içindeki buton bloğunda:
                if (isRegister) {
                    // Kayıt işlemi başarılı
                    isRegister = false
                    navController.navigate("selection") {
                        popUpTo("auth") { inclusive = true }
                    }
                } else {
                    // Giriş işlemi
                    navController.navigate("home") {
                        popUpTo("auth") { inclusive = true }
                    }
                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(if (isRegister) "Kayıt Ol" else "Giriş Yap")
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = { isRegister = !isRegister }) {
            Text(
                text = if (isRegister) "Hesabınız var mı? Giriş Yap" else "Hesabınız yok mu? Kayıt Ol",
                fontSize = 14.sp
            )
        }

        if (!isRegister) {
            Text(
                text = "Şifremi Unuttum",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { /* forgot password */ }
            )
        }

        Spacer(Modifier.height(24.dp))
        Divider()
        Spacer(Modifier.height(16.dp))
        Text("veya sosyal hesapla giriş", style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            IconButton(onClick = { /* Google OAuth */ }) {
                Icon(Icons.Default.Fingerprint, contentDescription = "Google ile Giriş")
            }
            IconButton(onClick = { /* Apple OAuth */ }) {
                Icon(Icons.Default.Fingerprint, contentDescription = "Apple ile Giriş")
            }
        }
    }
}
