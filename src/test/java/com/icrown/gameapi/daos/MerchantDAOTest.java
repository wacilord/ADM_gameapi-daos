package com.icrown.gameapi.daos;

import com.icrown.gameapi.models.AGT_MerchantModel;
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

@SpringBootTest(classes = MerchantDAOTest.class)
@ComponentScan(basePackages = "com.icrown.gameapi")
@EnableAutoConfiguration
public class MerchantDAOTest {
    @Autowired
    MerchantDAO merchantDAO;

    @Test
    public void getMerchant(){
        String mctGuid = "mctGuid";
        merchantDAO.getMerchant(mctGuid);
    }
    @Test
    public void getAllMerchant(){
        merchantDAO.getAllMerchant();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void addMerchant(){
        AGT_MerchantModel model = new AGT_MerchantModel();
        model.setMCT_Enable(true);
        model.setMCT_BackendAllowIP("127.0.0.1");
        model.setMCT_GUID("guid");
        model.setMCT_Currency("RMB");
        model.setLAG_Code("CN");
        model.setMCT_AllowIP("127.0.0.1");
        model.setMCT_Domain("domain");
        model.setMCT_FriendlyName("fr");
        model.setMCT_FrontendAllowIP("127.0.0.1");
        model.setMCT_MerchantCode("xxxxxx");
        merchantDAO.addMerchant(model);
    }

}
