package com.major.SkillsSwapCommunity;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Raw WebSocket endpoint for tools like WebSocket King
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*");

        // SockJS endpoint (optional, for frontend use)
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Subscriber destination
        config.setApplicationDestinationPrefixes("/app"); // Client sender prefix
        config.enableSimpleBroker("/topic"); // Enables in-memory broker
        config.setApplicationDestinationPrefixes("/app"); // For @MessageMapping
    }


}
