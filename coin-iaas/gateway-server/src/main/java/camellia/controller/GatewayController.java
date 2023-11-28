package camellia.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author anthea
 * @date 2023/10/24 14:54
 */
@RestController
@RequestMapping("/gateway")
public class GatewayController {
    @GetMapping("/flow/rules")
    public Set<GatewayFlowRule> getRoles() {
        return GatewayRuleManager.getRules();
    }

    @GetMapping("/api/groups")
    public Set<ApiDefinition> getGroups() {
        return GatewayApiDefinitionManager.getApiDefinitions();
    }
}
