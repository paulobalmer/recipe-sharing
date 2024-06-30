package net.azeti.challenge.recipe.entrypoint.rest.dto.errors

import java.io.Serializable

open class StandardError(
    val status: Int?,
    val message: String?,
    val timeStamp: Long?
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
