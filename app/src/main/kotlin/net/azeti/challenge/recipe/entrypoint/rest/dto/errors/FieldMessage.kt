package net.azeti.challenge.recipe.entrypoint.rest.dto.errors

import java.io.Serializable

class FieldMessage(
    val fieldName: String?,
    val message: String?
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
