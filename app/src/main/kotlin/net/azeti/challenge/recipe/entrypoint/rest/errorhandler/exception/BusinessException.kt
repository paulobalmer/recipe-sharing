package net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception

import net.azeti.challenge.recipe.entrypoint.rest.dto.errors.FieldMessage

class BusinessException : RuntimeException {

    private val errors: MutableList<FieldMessage> = ArrayList()

    constructor(msg: String?) : super(msg)
    constructor(msg: String?, cause: Throwable?) : super(msg, cause)

    fun addError(fieldName: String?, msg: String?) {
        errors.add(FieldMessage(fieldName, msg))
    }

    fun listErrors() = errors

    companion object {
        private const val serialVersionUID = 1L
    }
}
