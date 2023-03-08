package com.example.server;

import com.example.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WomApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Value("${server.port}")
    private String port;
    @Value("${springdoc.api-docs.path}")
    private String apiDocs;
    @Value("${springdoc.swagger-ui.path}")
    private String swaggerUi;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String hostAddress = CommonUtil.getHostAddress();
        log.info("remote address: http://{}:{}", hostAddress, port);

        log.info("visit homepage: http://localhost:{}/", port);
        log.info("visit api-docs: http://localhost:{}{}", port, apiDocs);
        log.info("visit swagger-ui: http://localhost:{}{}", port, swaggerUi);
    }
}
