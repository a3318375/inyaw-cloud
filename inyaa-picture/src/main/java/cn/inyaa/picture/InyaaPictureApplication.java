package cn.inyaa.picture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InyaaPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(InyaaPictureApplication.class, args);
    }

}
