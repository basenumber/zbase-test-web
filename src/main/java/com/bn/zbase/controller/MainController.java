package com.bn.zbase.controller;

import com.bn.zbase.config.BizParasConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zcn on 2018/9/5.
 */
@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private static final String MAIN_CONFIG_ROOT = "mainConfigRoot";

    @Autowired(required = false)
    @Qualifier("bizParasConfig")
    private BizParasConfig bizParasConfig;

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {
        String strVal = "nothing";
        if (bizParasConfig != null) {
            strVal = bizParasConfig.getMainConfigRoot();
        }

        model.addAttribute(MAIN_CONFIG_ROOT, strVal);
        logger.info(strVal);
        return "index";
    }
}
