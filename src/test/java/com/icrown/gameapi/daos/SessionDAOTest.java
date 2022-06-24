package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.MemberPlayerModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest(classes = SessionDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class SessionDAOTest {
    @Autowired
    SessionDAO sessionDAO;

    @Test
    public void getSessionByToken(){
        String token = "token";
        sessionDAO.getSessionByToken(token);
    }

    @Test
    public void expiredSessionByToken() {
        String token = "token";
        Date expired = new Date();
        sessionDAO.expiredSessionByToken(token, expired);
    }
}
