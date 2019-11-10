package com.honor.back.honorwebapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.honor.back.honorwebapp",basePackageClasses = ConfigClass.class)
public class ConfigClass {
}
