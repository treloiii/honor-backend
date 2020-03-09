package com.honor.back.honorwebapp;

//import com.mchange.v2.c3p0.ComboPooledDataSource;
//import org.apache.tomcat.dbcp.dbcp2.*;
import Entities.GalleryAlbum;
import Entities.GalleryImage;
import com.coxautodev.graphql.tools.GraphQLResolver;
import scalars.DateScalar;
import scalars.DoubleScalar;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.MultipartConfigElement;
import java.util.List;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.honor.back.honorwebapp","dao","services", "utils","sql","controllers","scalars","query"})
public class ConfigClass {
    @Autowired
    ApplicationContext context;
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("200MB"));
        factory.setMaxRequestSize(DataSize.parse("200MB"));
        return factory.createMultipartConfig();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }
    @Bean
    public RuntimeWiring getRuntimeWiring(){
        return RuntimeWiring.newRuntimeWiring().scalar(new DateScalar()).scalar(new DoubleScalar()).build();
    }
    @Bean
    public GraphQLResolver<GalleryAlbum> imageResolver(){
        return new GraphQLResolver<GalleryAlbum>() {
          public List<GalleryImage> images(GalleryAlbum album,int limit){
              List<GalleryImage> images=album.getImages();
              int normalizedLimit = limit > 0 ? limit : images.size();
              return images.subList(0, Math.min(normalizedLimit, images.size()));
          }
        };
    }
//    @Bean
//    public PostQuery postQuery(){
//        return context.getBean(PostQuery.class);
//    }
//    @Bean
//    public PostMutation postMutation(){
//        return context.getBean(PostMutation.class);
//    }
}
