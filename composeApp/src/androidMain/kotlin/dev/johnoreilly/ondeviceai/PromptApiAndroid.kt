package dev.johnoreilly.ondeviceai

import com.google.mlkit.genai.common.DownloadStatus
import com.google.mlkit.genai.prompt.Generation

class PromptApiAndroid: PromptApi {
    private val generativeModel = Generation.getClient()

    override suspend fun generateContent(prompt: String): String? {
        try {
            val response = generativeModel.generateContent(prompt)
            return response.candidates.firstOrNull()?.text
        } catch (e: Exception) {
            return e.message
        }
    }

    suspend fun downloadModel() {
        generativeModel.download().collect { status ->
            when (status) {
                is DownloadStatus.DownloadStarted ->
                    println("starting download for Gemini Nano")

                is DownloadStatus.DownloadProgress ->
                    println("Nano ${status.totalBytesDownloaded} bytes downloaded")

                DownloadStatus.DownloadCompleted -> {
                    println("Gemini Nano download complete")
                }

                is DownloadStatus.DownloadFailed -> {
                    println("Nano download failed ${status.e.message}")
                }
            }
        }
    }
}