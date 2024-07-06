package ru.snapix.balancer

import kotlinx.serialization.Serializable
import ru.snapix.library.network.ServerType

@Serializable
data class BalancerServer(
    val name: String,
    val map: String,
    val port: Int,
    val players: List<String> = mutableListOf(),
    val maxPlayers: Int,
    val serverType: ServerType,
    var state: State,
    val mode: Mode
) {
    fun canJoin(): Boolean {
        return players.size < maxPlayers && (state == State.WAITING || state == State.STARTING)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BalancerServer) return false

        if (name != other.name) return false
        if (serverType != other.serverType) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    private constructor(builder: Builder) : this(
        builder.name ?: error("Name cannot be null"),
        builder.map ?: error("Map cannot be null"),
        builder.port ?: error("Port cannot be null"),
        builder.players,
        builder.maxPlayers,
        builder.serverType,
        builder.state,
        builder.mode
    )

    class Builder {
        var name: String? = null
        var map: String? = null
        var port: Int? = null
        var players: List<String> = emptyList()
        var maxPlayers: Int = 0
        var serverType: ServerType = ServerType.UNKNOWN
        var state: State = State.RESTARTING
        var mode: Mode = Mode.UNKNOWN

        fun build() = BalancerServer(this)
    }
}

fun balancerServer(builder: BalancerServer.Builder.() -> Unit) = BalancerServer.Builder().apply(builder).build()
