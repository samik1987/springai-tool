package com.example.springai_tool.Controllers;

import com.example.springai_tool.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public String Chat(@RequestBody String msg)
    {
        return chatService.getChat(msg);
    }
}
