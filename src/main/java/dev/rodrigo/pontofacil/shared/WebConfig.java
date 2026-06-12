package dev.rodrigo.pontofacil.shared;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Serve o frontend (HTML/CSS/JS) pelo proprio backend, na mesma origem (porta 8080).
 * Assim a aplicacao roda com um unico comando e sem necessidade de CORS.
 * A pasta do frontend e localizada automaticamente a partir do diretorio de trabalho.
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.frontend.path:}")
    private String frontendPathConfig;

    private String resolverFrontend() {
        // 1) Caminho explicito via propriedade
        if (frontendPathConfig != null && !frontendPathConfig.isBlank()) {
            Path p = Paths.get(frontendPathConfig);
            if (Files.exists(p.resolve("index.html"))) return p.toAbsolutePath().toString();
        }
        // 2) Procura subindo a partir do diretorio de trabalho por uma pasta com index.html + js/app.js
        Path dir = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        for (int i = 0; i < 5 && dir != null; i++) {
            if (Files.exists(dir.resolve("index.html")) && Files.exists(dir.resolve("js/app.js"))) {
                return dir.toString();
            }
            dir = dir.getParent();
        }
        return null;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String frontend = resolverFrontend();
        if (frontend == null) {
            log.warn("Frontend nao encontrado para servir estaticamente. Sirva-o separadamente (ex: Live Server).");
            return;
        }
        log.info("Servindo frontend de: {}", frontend);
        String location = "file:" + frontend.replace("\\", "/") + "/";
        registry.addResourceHandler("/", "/index.html").addResourceLocations(location);
        registry.addResourceHandler("/css/**").addResourceLocations(location + "css/");
        registry.addResourceHandler("/js/**").addResourceLocations(location + "js/");
        registry.addResourceHandler("/assets/**").addResourceLocations(location + "assets/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}
