package me.hajoo.mdc.config

import org.slf4j.MDC
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskDecorator

@Configuration
class MdcTaskDecorator: TaskDecorator {

    override fun decorate(runnable: Runnable): Runnable {
        val context = MDC.getCopyOfContextMap()
        return Runnable {
            MDC.setContextMap(context)
            runnable.run()
        }
    }
}