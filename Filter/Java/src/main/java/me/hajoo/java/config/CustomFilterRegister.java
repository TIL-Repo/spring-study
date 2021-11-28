package me.hajoo.java.config;

import me.hajoo.java.filter.FirstFilter;
import me.hajoo.java.filter.SecondFilter;
import me.hajoo.java.filter.ThirdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class CustomFilterRegister {

    /*
    * 필터 등록 순서대로 필터 순서가 매겨짐
    * 값이 작을수록 높은 우선순위를 가지는 필터
    * */

    @Bean
    public FilterRegistrationBean firstFilter() {
        FilterRegistrationBean firstFilter = new FilterRegistrationBean(new FirstFilter());
        ArrayList<String> urls = new ArrayList<>(); urls.add("/");
        firstFilter.setUrlPatterns(urls);
        firstFilter.setOrder(0);
        return firstFilter;
    }

    @Bean
    public FilterRegistrationBean secondFilter() {
        FilterRegistrationBean secondFilter = new FilterRegistrationBean(new SecondFilter());
        ArrayList<String> urls = new ArrayList<>(); urls.add("/"); urls.add("/second-filter");
        secondFilter.setUrlPatterns(urls);
        secondFilter.setOrder(1);
        return secondFilter;
    }

    @Bean
    public FilterRegistrationBean thirdFilter() {
        FilterRegistrationBean thirdFilter = new FilterRegistrationBean(new ThirdFilter());
        ArrayList<String> urls = new ArrayList<>(); urls.add("/"); urls.add("/third-filter");
        thirdFilter.setUrlPatterns(urls);
        thirdFilter.setOrder(2);
        return thirdFilter;
    }

}
