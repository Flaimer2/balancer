package ru.snapix.balancer

import kotlinx.serialization.Serializable

@Serializable
data class PlayerConnect(val name: String, val server: BalancerServer) {

}
