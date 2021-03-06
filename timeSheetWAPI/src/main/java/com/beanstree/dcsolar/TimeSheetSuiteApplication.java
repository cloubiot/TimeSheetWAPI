package com.beanstree.dcsolar;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@ComponentScan({"com.timeSheet.*","com.beanstree.*"})
@EnableJpaRepositories({"com.timeSheet.*","com.beanstree.*"})
@EntityScan({"com.timeSheet.*","com.beanstree.*"})
@EnableAutoConfiguration
@EnableJms
@EnableCaching
public class TimeSheetSuiteApplication {

//	@Bean
//	public Queue queue() {
//		return new ActiveMQQueue("email.queue5");
//	}
	public static void main(String[] args) {
		SpringApplication.run(TimeSheetSuiteApplication.class, args);
	}
}
