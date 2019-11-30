package com.honor.back.honorwebapp;

//import com.mchange.v2.c3p0.ComboPooledDataSource;
//import org.apache.tomcat.dbcp.dbcp2.*;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;

import javax.sql.DataSource;
import javax.servlet.MultipartConfigElement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.honor.back.honorwebapp","dao","services","Utils","sql"},basePackageClasses = ConfigClass.class)
public class ConfigClass {
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("10240KB"));
        factory.setMaxRequestSize(DataSize.parse("102400KB"));
        return factory.createMultipartConfig();
    }
}
