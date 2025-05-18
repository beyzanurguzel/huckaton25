package com.example.myapplication.presentation.screens

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.camera.core.Preview as CameraXPreview
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.presentation.viewmodel.EnergyAuditViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import android.R.attr.scaleType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnergyAuditScreen(
    viewModel: EnergyAuditViewModel = viewModel()
) {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasPermission = granted }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    var isScanning by remember { mutableStateOf(false) }
    val suggestions by viewModel.suggestions.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Enerji Tarama & Öneri") }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!hasPermission) {
                Text("Kamera izni gerekiyor.", color = MaterialTheme.colorScheme.error)
            } else {
                // ➊ Canlı Kamera Önizlemesi
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))) {
                    CameraPreview(context)
                }

                // ➋ Tarama / Öneri Butonu
                Button(
                    onClick = {
                        if (!isScanning) {
                            isScanning = true
                            viewModel.scanAndAnalyze() // kameradan alınan kareleri AI'a gönder
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isScanning) "Tarama yapılıyor…" else "Öneri Al")
                }

                // ➌ Öneri Listesi
                if (suggestions.isNotEmpty()) {
                    Text("Yapay Zekâ Önerileri:", style = MaterialTheme.typography.titleMedium)
                    suggestions.forEach { sug ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(sug.title, style = MaterialTheme.typography.titleSmall)
                                Spacer(Modifier.height(4.dp))
                                Text(sug.description, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun CameraPreview(context: Context) {
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }
            cameraProviderFuture.addListener({
                val provider = cameraProviderFuture.get()
                // CameraX'in Preview sınıfını alias ile çağırıyoruz:
                val previewUseCase = CameraXPreview.Builder()
                    .build()
                    .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                val selector = CameraSelector.DEFAULT_BACK_CAMERA
                provider.unbindAll()
                provider.bindToLifecycle(
                    ctx as androidx.lifecycle.LifecycleOwner,
                    selector,
                    previewUseCase
                )
            }, ContextCompat.getMainExecutor(ctx))
            previewView
        }
    )
}

