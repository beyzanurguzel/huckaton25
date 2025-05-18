package com.example.myapplication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.filled.ArrowForward

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    navController: NavController,
    openDrawer: () -> Unit,
    taskViewModel: TaskViewModel = viewModel()
) {
    // Tarih metni
    val today = remember { Calendar.getInstance() }
    val dateText = remember {
        SimpleDateFormat("EEEE, d MMMM", Locale("tr"))
            .format(today.time)
            .replaceFirstChar { it.uppercase() }
    }

    // Dummy görev listesi (alt kısım için)
    val extraTasks = listOf(
        Triple("Kısa duş al (max 5 dk)", "Suyu ve enerjiyi birlikte korusun.", "+10 puan"),
        Triple("Telefonu %80'den fazla şarj etme", "Aşırı şarj enerji israfıdır.", "+5 puan"),
        Triple("Enerji tasarruflu ampul kullan", "Kalıcı tasarruf sağlar.", "+8 puan")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Görevlerim", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menü")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                // Hafta gezintisi
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { today.add(Calendar.DAY_OF_YEAR, -7) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Önceki hafta")
                    }
                    Text(
                        text = dateText,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                    IconButton(onClick = { today.add(Calendar.DAY_OF_YEAR, 7) }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Sonraki hafta")
                    }
                }
            }

            item {
                // Öne Çıkan Görevler başlığı
                Text(
                    "Öne Çıkan Görevler",
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }

            // Birden fazla öne çıkan kart
            items(listOf(
                FeaturedData(
                    title = "Mahalle Takibi",
                    subtitle = "Arkadaşlarınla birlikte günlük görevleri kontrol et!",
                    avatars = listOf(R.drawable.lego1, R.drawable.lego2),
                    gradient = Brush.verticalGradient(listOf(Color(0xFF80DEEA), Color(0xFF4DD0E1))),
                    buttonText = "Şimdi",
                    buttonTextColor = Color(0xFF4DD0E1)
                ),
                FeaturedData(
                    title = "Su Tasarrufu",
                    subtitle = "Gereksiz su kullanımını azaltmak için raporla.",
                    avatars = listOf(R.drawable.lego3, R.drawable.lego4),
                    gradient = Brush.verticalGradient(listOf(Color(0xFFA5D6A7), Color(0xFF81C784))),
                    buttonText = "Başla",
                    buttonTextColor = Color(0xFF81C784)
                )
            )) { data ->
                FeaturedTaskCard(
                    title = data.title,
                    description = data.subtitle,
                    avatars = data.avatars,
                    buttonText = data.buttonText,
                    gradient = data.gradient,
                    buttonTextColor = data.buttonTextColor,
                    onButtonClick = { /* Aksiyon */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                // Tüm Görevler başlığı
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Tüm Görevler",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        "hepsini gör",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { /* eylem */ }
                    )
                }
            }

            // Alt görevler
            items(extraTasks) { (title, desc, pts) ->
                TaskCard(
                    title = title,
                    description = desc,
                    avatars = listOf(R.drawable.lego1, R.drawable.lego2),
                    points = pts,
                    gradient = Brush.horizontalGradient(listOf(Color(0xFFFFAB91), Color(0xFFFF8A65))),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 16.dp)
                )
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

// Veri sınıfı öne çıkan kartlar için
private data class FeaturedData(
    val title: String,
    val subtitle: String,
    val avatars: List<Int>,
    val gradient: Brush,
    val buttonText: String,
    val buttonTextColor: Color
)

@Composable
fun FeaturedTaskCard(
    title: String,
    description: String,
    avatars: List<Int>,
    buttonText: String,
    gradient: Brush,
    buttonTextColor: Color,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .shadow(8.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(20.dp)
        ) {
            Column(Modifier.align(Alignment.TopStart)) {
                Text(
                    title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    ),
                    maxLines = 2
                )
            }

            Row(
                Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 0.dp, y = 8.dp)
            ) {
                avatars.forEachIndexed { index, res ->
                    Image(
                        painter = painterResource(res),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .border(2.dp, Color.White, CircleShape)
                            .clip(CircleShape)
                            .offset(x = (index * -12).dp)
                    )
                }
            }

            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.95f)),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Text(buttonText, color = buttonTextColor)
            }
        }
    }
}

@Composable
fun TaskCard(
    title: String,
    description: String,
    avatars: List<Int>,
    points: String,
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp)
        ) {
            Column(Modifier.align(Alignment.TopStart)) {
                Text(title, style = MaterialTheme.typography.titleMedium.copy(color = Color.White))
                Spacer(Modifier.height(4.dp))
                Text(description, style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.9f)))
            }
            Row(Modifier.align(Alignment.BottomStart)) {
                avatars.forEachIndexed { index, res ->
                    Image(
                        painter = painterResource(res),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .border(1.dp, Color.White, CircleShape)
                            .clip(CircleShape)
                            .offset(x = (index * -8).dp)
                    )
                }
            }
            Text(
                points,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White, fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}
