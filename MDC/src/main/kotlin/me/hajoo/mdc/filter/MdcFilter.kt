package me.hajoo.mdc.filter

import org.slf4j.MDC
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MdcFilter: OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        MDC.put("traceId", UUID.randomUUID().toString())
        filterChain.doFilter(request, response)
        MDC.clear()
    }
}