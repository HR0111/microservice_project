package org.hemant.microservices.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.microservices.order.event.OrderPlacedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("OrderPlacedEvent STARTED {}: " , orderPlacedEvent);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springShopHemant@gmail.com");
            messageHelper.setTo(orderPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    
                    Hi
                    
                    Your order with order number %s is placed successfully.
                     
                    Best Regards,
                    Spring Shop HR
                    
                    """

            , orderPlacedEvent.getOrderNumber()));

        };

        try{
            javaMailSender.send(messagePreparator);
            log.info("Order Notification Email Sent !!");
        } catch (MailException e) {
            log.error("Exception occurred while sending email", e);
            throw new RuntimeException("Exception occurred while sending email from springShop", e);
        }


    }

}
