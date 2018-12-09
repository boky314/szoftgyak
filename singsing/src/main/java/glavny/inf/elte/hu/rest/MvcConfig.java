package glavny.inf.elte.hu.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/password").setViewName("password");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/cells").setViewName("index");
        registry.addViewController("/security").setViewName("index");
        registry.addViewController("/prisoners").setViewName("index");
        registry.addViewController("/areas").setViewName("index");
        registry.addViewController("/guards").setViewName("index");
        registry.addViewController("/holiday").setViewName("index");
        registry.addViewController("/logs").setViewName("index");
        registry.addViewController("/oldhome").setViewName("home");

    }

}