package com.bn.zbase.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by zcn on 2018/2/11.
 * PropConfiguration类由spring生成bean的条件
 */
public class PropConfigurationCondition implements Condition {

    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return conditionContext.getEnvironment().getProperty("propconfig.enabled").equalsIgnoreCase("true");
    }
}
