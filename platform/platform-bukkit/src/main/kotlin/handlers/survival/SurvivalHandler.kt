package ru.snapix.balancer.handlers.survival

import ru.snapix.balancer.handlers.Handler

interface SurvivalHandler : Handler {
    fun updateServer(stop: Boolean = false)
}