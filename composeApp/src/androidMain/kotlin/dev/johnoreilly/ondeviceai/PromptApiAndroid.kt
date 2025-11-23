package dev.johnoreilly.ondeviceai

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
}