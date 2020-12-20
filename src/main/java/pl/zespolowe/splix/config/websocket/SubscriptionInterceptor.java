package pl.zespolowe.splix.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import pl.zespolowe.splix.services.ActivePlayersRegistry;
import pl.zespolowe.splix.services.GameService;

@Component
public class SubscriptionInterceptor implements ChannelInterceptor {

    @Autowired
    private GameService gameService;

    @Autowired
    private ActivePlayersRegistry playersRegistry;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            String sessionID = (String) headerAccessor.getSessionAttributes().get("sessionID");
            if (!validateSubscription(sessionID, headerAccessor.getDestination()))
                throw new MessagingException("Permission denied for this topic");
        }
        return message;
    }

    private boolean validateSubscription(String sessionID, String destination) {
        if (destination == null) return false;
        int gameID = Integer.parseInt(destination.substring(13));
        return gameService.containsPlayer(gameID, playersRegistry.getPlayer(sessionID));
    }
}
