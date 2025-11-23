import Foundation
import FoundationModels
import ComposeApp


class PromptApiIos: PromptApi {
    
    func generateContent(prompt: String) async throws -> String? {
        var responseText = ""
        
        do {
            let session = LanguageModelSession()
            let response = try await session.respond(to: prompt)
            responseText = response.content
        } catch let error {
            responseText = error.localizedDescription
        }

        return responseText
    }
    
}
