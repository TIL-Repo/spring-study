package me.hajoo.mdc.config

import me.hajoo.mdc.filter.MdcFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomFilterRegister {

    @Bean
    fun mdcFilter(): FilterRegistrationBean<MdcFilter> {
        val bean = FilterRegistrationBean(MdcFilter())
        bean.urlPatterns = arrayListOf("/")
        bean.order = 0
        return bean
    }
}