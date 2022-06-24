package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.AgentModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = AgtFunctionDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class AgtFunctionDAOTest {
    @Autowired
    AgtFunctionDAO agtFunctionDAO;

    @Test
    public void getAllFunction(){
        agtFunctionDAO.getAllFunction();
    }

    @Test
    public void getFunType(){
        agtFunctionDAO.getFunType();
    }

    @Test
    public void getFun(){
        agtFunctionDAO.getFun();
    }


}
