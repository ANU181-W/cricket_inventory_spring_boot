package com.example.demo.model;

import java.util.List;

public class GeminiRequest {
    private String model;
    private List<Message> messages;

    public GeminiRequest(String model, String userPrompt) {
        this.model = model;
        this.messages = List.of(
                new Message("system",
                        "You are a skilled cricket content creator and web designer. " +
                                "Always return clean, valid HTML content (not JSON or plain text). " +
                                "Your output must include interactive design, animations, and " +
                                "3–4 genuine images from trusted cricket sources (Wikipedia, BCCI, ESPNcricinfo, etc.)."),
                new Message("user", userPrompt)
        );
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
