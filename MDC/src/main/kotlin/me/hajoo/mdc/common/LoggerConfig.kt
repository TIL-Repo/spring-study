package me.hajoo.mdc.common

import org.slf4j.Logger

inline fun <reified T> T.logger(): Logger {
    return org.slf4j.LoggerFactory.getLogger(T::class.java)
}