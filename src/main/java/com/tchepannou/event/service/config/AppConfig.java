package com.tchepannou.event.service.config;

import com.tchepannou.event.service.dao.EventDao;
import com.tchepannou.event.service.dao.jdbc.JdbcEventDao;
import com.tchepannou.event.service.service.command.SearchCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Declare your services here!
 */
@Configuration
public class AppConfig {
    //-- Attributes
    @Value("${database.driver}")
    private String driver;

    @Value ("${database.url}")
    private String url;

    @Value ("${database.username}")
    private String username;

    @Value ("${database.password}")
    private String password;


    //-- Beans
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    DataSource dataSource (){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        return ds;
    }

//    //-- JMS Config
//    @Bean JmsListenerContainerFactory jmsContainerFactory(ConnectionFactory connectionFactory) {
//        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        return factory;
//    }
//
//    @Bean JmsTemplate eventLogQueue(ConnectionFactory factory){
//        return new JmsTemplate(factory);
//    }


    //-- Dao
    @Bean
    EventDao eventDao (){
        return new JdbcEventDao();
    }

    //-- Command
    @Bean
    SearchCommand searchCommand (){
        return new SearchCommand();
    }
}
