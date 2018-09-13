package com.bn.zbase.listener;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zcn on 2018/2/11.
 */
public class CustomContextLoaderListener extends ContextLoaderListener {

    private static final String PROP_ZBASE_MAIN_CONFIG_ROOT = "zbase.main.config.root";

    @Override
    protected void customizeContext(ServletContext sc, ConfigurableWebApplicationContext wac) {
        // 加载classpath下的配置
        String strConfigApplication = "classpath:application*.properties";
        loadCustomConfigFile(wac, strConfigApplication);

        String strZbaseMainConfigRoot = wac.getEnvironment().getProperty(PROP_ZBASE_MAIN_CONFIG_ROOT);
        if (strZbaseMainConfigRoot.endsWith("/")) {
            strZbaseMainConfigRoot = strZbaseMainConfigRoot.substring(0, strZbaseMainConfigRoot.length() - 1);
        }
        // 以下分开读取的原因是：当使用“http://***/*.properties”来获取远程的配置文件时，你需要指定具的文件名，
        // 而不能由*.properties来读取所有的文件
        // 关于如何实现*.properties文件组，而不是使用下面的示例，其实也可以通过多加一个间接层来实现
        String strConfig = strZbaseMainConfigRoot + "/app.properties";
        loadCustomConfigFile(wac, strConfig);
        strConfig = strZbaseMainConfigRoot + "/app2.properties";
        loadCustomConfigFile(wac, strConfig);
        strConfig = strZbaseMainConfigRoot + "/biz.properties";
        loadCustomConfigFile(wac, strConfig);

        super.customizeContext(sc, wac);
    }

    /**
     * 加载配置文件
     * @param wac
     * @param strConfigFilePath
     */
    private void loadCustomConfigFile(ConfigurableWebApplicationContext wac, String strConfigFilePath) {

        List<Resource> listResources = new ArrayList<Resource>();
        ResourcePatternResolver objResolver = new PathMatchingResourcePatternResolver();
        try {
            listResources.addAll(Arrays.asList(objResolver.getResources(strConfigFilePath)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Resource[] locations = new Resource[listResources.size()];
        locations = listResources.toArray(locations);
        for (Resource item : locations) {
            ResourcePropertySource objRes = null;
            try {
                objRes = new ResourcePropertySource(item.getFilename(), item);
                wac.getEnvironment().getPropertySources().addLast(objRes);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
