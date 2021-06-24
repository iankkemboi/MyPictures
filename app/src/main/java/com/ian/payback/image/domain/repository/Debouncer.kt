package com.ian.payback.image.domain.repository


import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class Debouncer {
    private val scheduler = Executors.newSingleThreadScheduledExecutor()
    private val delayedMap = ConcurrentHashMap<Any, Future<*>>()

    fun debounce(
        key: Any, runnable: Runnable, delay: Long,
        unit: TimeUnit?
    ) {
        val prev = delayedMap.put(
            key, scheduler.schedule({
                try {
                    runnable.run()
                } finally {
                    delayedMap.remove(key)
                }
            }, delay, unit)
        )
        prev?.cancel(true)
    }

    fun shutdown() {
        scheduler.shutdownNow()
    }
}