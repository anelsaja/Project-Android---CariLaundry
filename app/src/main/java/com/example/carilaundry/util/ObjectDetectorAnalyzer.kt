package com.example.carilaundry.util

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

// Kelas ini bertugas menganalisa setiap frame gambar dari kamera
class ObjectDetectorAnalyzer(
    private val onObjectsDetected: (Int) -> Unit // Callback mengirim jumlah benda
) : ImageAnalysis.Analyzer {

    // Konfigurasi Detektor
    private val options = ObjectDetectorOptions.Builder()
        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE) // Mode cepat (Video)
        .enableMultipleObjects() // Bisa mendeteksi banyak benda sekaligus
        .enableClassification()  // Mencoba menebak jenis benda (Baju/Celana/dll)
        .build()

    private val detector: ObjectDetector = ObjectDetection.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            detector.process(image)
                .addOnSuccessListener { detectedObjects ->
                    // Kirim jumlah objek yang terdeteksi ke UI
                    onObjectsDetected(detectedObjects.size)
                }
                .addOnFailureListener {
                    // Handle error jika perlu
                }
                .addOnCompleteListener {
                    imageProxy.close() // PENTING: Tutup image agar kamera tidak macet
                }
        } else {
            imageProxy.close()
        }
    }
}