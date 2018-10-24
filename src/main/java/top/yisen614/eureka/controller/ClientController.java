package top.yisen614.eureka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/getInfo")
    public String getInfo() {
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("EUREKA-CLIENT");
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort());
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
        return response;
    }

}
