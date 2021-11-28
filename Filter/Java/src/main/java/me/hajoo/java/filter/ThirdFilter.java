package me.hajoo.java.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class ThirdFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * IllegalStateException 오류 발생
     * 이유 : getInputStream(), getReader()를 이용해서 한번만 읽을 수 있음
     * 해결 : ContentCachingRequestWrapper 클래스 이용
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("ThirdFilter start");
        HttpServletRequest req = (HttpServletRequest) request;

        // 오류 발생
//        BufferedReader reader = req.getReader();
//        String line = "";
//        int lineCnt = 1;
//        while ((line = reader.readLine()) != null) {
//            log.info(lineCnt + " " + line);
//            lineCnt += 1;
//        }

        // 오류 해결
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(req);
        log.info(new String(requestWrapper.getContentAsByteArray()));

        chain.doFilter(req, response);
        log.info("ThirdFilter end");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
