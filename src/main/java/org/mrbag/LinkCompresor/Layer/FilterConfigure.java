package org.mrbag.LinkCompresor.Layer;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class FilterConfigure {

    @Bean
    public FilterRegistrationBean<?> regLayer(TraficCheckFilter filter){
        FilterRegistrationBean<? super OncePerRequestFilter> reg = new FilterRegistrationBean<>();
        reg.addUrlPatterns("/*");
        reg.setFilter(filter);
        reg.setOrder(0);
        return reg; 
    }
}
