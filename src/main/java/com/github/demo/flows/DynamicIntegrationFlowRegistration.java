package com.github.demo.flows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.stereotype.Component;

@Component
public class DynamicIntegrationFlowRegistration {

    private final IntegrationFlowContext flowContext;

    @Autowired
    public DynamicIntegrationFlowRegistration(IntegrationFlowContext flowContext) {
        this.flowContext = flowContext;
    }

    public IntegrationFlowContext.IntegrationFlowRegistration registerIntegrationFlow(IntegrationFlow flow, String flowId) {
        return flowContext.registration(flow)
                .id(flowId)
                .register();
    }

    public void unregisterIntegrationFlow(String flowId) {
        flowContext.remove(flowId);
    }

}
