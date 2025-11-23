
package dev.johnoreilly.ondeviceai

interface PromptApi {
    suspend fun generateContent(prompt: String): String?
}
