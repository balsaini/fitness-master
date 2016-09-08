package com.chyld.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("fit.exchange");
    }

    @Bean
    public Queue runQueue() {
        return new Queue("fit.queue.run");
    }

    @Bean
    public Queue posQueue() {
        return new Queue("fit.queue.pos");
    }

    @Bean
    public Binding binding1(TopicExchange topic, Queue runQueue){
        return BindingBuilder.bind(runQueue).to(topic).with("fit.topic.run.*");

    }

    @Bean
    public Binding binding2(TopicExchange topic, Queue posQueue) {
        return BindingBuilder.bind(posQueue).to(topic).with("fit.topic.pos");
    }
}
