package com.honor.back.honorwebapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@CrossOrigin
@RestController
public class Controller{
    @Autowired
    ResultedQuery rsq;
    @RequestMapping("/getMain")
    public void getMain() throws SQLException {
        ResultSet rs=rsq.getResultSet("SELECT table_name FROM information_schema.tables where table_schema='std_763'");
        while (rs.next()){
            for(int i=1;i<rs.getMetaData().getColumnCount()+1;i++)
                System.out.println(rs.getString(i));
        }
    }
}
