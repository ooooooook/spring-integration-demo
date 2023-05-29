package com.github.demo.flows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.stereotype.Component;

/**
 * @author yx che
 * @date 2023/5/29
 */
@Component
public class IntegrationFlowStatusChecker {

    private final IntegrationFlowContext flowContext;

    @Autowired
    public IntegrationFlowStatusChecker(IntegrationFlowContext flowContext) {
        this.flowContext = flowContext;
    }

    public boolean isFlowRunning(String flowId) {
        IntegrationFlowContext.IntegrationFlowRegistration flowRegistration = flowContext.getRegistrationById(flowId);
        if (flowRegistration != null) {
            return ((Lifecycle) flowRegistration.getIntegrationFlow()).isRunning();
        }
        return false;
    }

}
