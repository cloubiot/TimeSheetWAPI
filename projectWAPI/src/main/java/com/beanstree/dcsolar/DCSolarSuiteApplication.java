package com.beanstree.dcsolar;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@ComponentScan({"com.projectLog.*","com.beanstree.*"})
@EnableJpaRepositories({"com.projectLog.*","com.beanstree.*"})
@EntityScan({"com.projectLog.*","com.beanstree.*"})
@EnableAutoConfiguration
@EnableJms
public class DCSolarSuiteApplication {

//	@Bean
//	public Queue queue() {
//		return new ActiveMQQueue("email.queue5");
//	}
	public static void main(String[] args) {
		SpringApplication.run(DCSolarSuiteApplication.class, args);
	}
}
