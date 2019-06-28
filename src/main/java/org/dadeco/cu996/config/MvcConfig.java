package org.dadeco.cu996.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("overall_capacity_overview");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/overview/team").setViewName("overall_capacity_overview");
        registry.addViewController("/overview/me").setViewName("personal_capacity_overview");
        registry.addViewController("/activity").setViewName("activity");
        registry.addViewController("/profile").setViewName("profile");
        //registry.addViewController("/plan").setViewName("plan");
        registry.addViewController("/403").setViewName("403");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
    }
}
