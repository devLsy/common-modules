package com.example.commonmodules.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * STOMP 설정 파일
 * STOMP는 WebSocket 위에서 동작한다.
 * WebSocket 연결 후(endPoint) 구독/발행으로 메시지를 주고 받는다.
 *
 * 사용하고자 하는 위치에서 선언 - private final SimpMessagingTemplate messagingTemplate;
 * 사용하고자 하는 위치에서 사용 - messagingTemplate.convertAndSend("/sub/sample", payload);
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 메시지 브로커 옵션 구성
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // 구독 : 클라이언트가 이 접두사로 시작하는 주제를 구독하여 메시지를 받을 수 있다. (서버 -> 클라이언트)
        config.enableSimpleBroker("/sub");

        // 발행 : 이 접두사로 시작하는 메시지는 @MessageMapping이 달린 메서드로 라우팅 된다. (클라이언트 -> 서버)
        config.setApplicationDestinationPrefixes("/pub");
    }

    /**
     * 엔드포인트 등록 및 SockJS 폴백 옵션 구성 (WebSocket을 지원하지 않는 브라우저에서도 SockJS를 통해 WebSocket 기능을 사용할 수 있게 함)
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 클라이언트가 WebSocket에 연결하기 위한 엔드포인트 설정
                .setAllowedOriginPatterns("*") // 모든 도메인의 요청을 허용
//                .setAllowedOriginPatterns("http://localhost:8080") // 특정 도메인의 요청을 허용
                .withSockJS(); // SockJS를 사용가능하게 함
    }
}
