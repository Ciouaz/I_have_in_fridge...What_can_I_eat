package org.example;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class ChatGPTHelper {
    OpenAiService service;
    String question;

    public ChatGPTHelper() {
        service = new OpenAiService("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", Duration.ofSeconds(30));
    }

    public String getMealIdea(List<String> products, String meal) {

        try {
            products.get(0);
            String allProducts = String.join(",", products);
            question = "Podaj trzy pomysły na " + meal + " korzystając wyłącznie z tych składników: " + allProducts + ".";

            return askChatGPT(question);

        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null,
                    "Nie masz produktów w lodówce!",
                    "Pusta lodówka",
                    JOptionPane.ERROR_MESSAGE);
            return null;

        }
    }

    private String askChatGPT(String question) {
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(List.of(new ChatMessage("user", question)))
                .model("gpt-3.5-turbo")
                .build();
        List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();

        StringBuilder stringBuilder = new StringBuilder();

        choices.stream()
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent)
                .forEach(stringBuilder::append);

        return stringBuilder.toString();
    }
}
