package com.bn.zbase.config;

import com.bn.zbase.condition.PropConfigurationCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;


/**
 * Created by zcn on 2018/2/5.
 */
@Configuration
@Conditional(PropConfigurationCondition.class)
public class PropConfiguration {

    @Value("${propconfig.enabled}")
    private String _strConfigRoot;

    @Value("${zbase.config.app.val01}")
    private String _strAppVal01;

    @Value("${zbase.profiles.active}")
    private String _strProfilesActive;

    @Bean("bizParasConfig")
    public BizParasConfig appConfig() {
        BizParasConfig objBean = new BizParasConfig();
        objBean.setMainConfigRoot(_strConfigRoot);
        return objBean;
    }

}
