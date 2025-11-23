package dev.johnoreilly.ondeviceai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.Markdown
import dev.johnoreilly.ondeviceai.PromptApi
import kotlinx.coroutines.launch


var promptApi: PromptApi? = null

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    var prompt by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var response by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Enter your prompt",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Button(
                onClick = {
                    if (prompt.text.isNotBlank()) {
                        keyboardController?.hide()
                        scope.launch {
                            try {
                                isLoading = true
                                response = ""
                                response = promptApi?.generateContent(prompt.text)
                                    ?: "Error generating content"
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                },
                enabled = !isLoading && prompt.text.isNotBlank(),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Submit Prompt")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.width(12.dp))
                Text("Generating response…")
            }
        }

        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Markdown(response)
        }

    }
}

