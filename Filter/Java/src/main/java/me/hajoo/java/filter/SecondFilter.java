package me.hajoo.java.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class SecondFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("SecondFilter start");
        chain.doFilter(request, response);
        log.info("SecondFilter end");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
