package dev.johnoreilly.ondeviceai

import androidx.compose.ui.window.ComposeUIViewController
import dev.johnoreilly.ondeviceai.ui.App
import dev.johnoreilly.ondeviceai.ui.promptApi
import platform.UIKit.UIViewController


fun MainViewController(promptApiIos: PromptApi): UIViewController {
    promptApi = promptApiIos
    return ComposeUIViewController {
        App()
    }
}