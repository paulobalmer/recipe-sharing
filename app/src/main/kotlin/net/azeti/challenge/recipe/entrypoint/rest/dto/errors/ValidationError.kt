package net.azeti.challenge.recipe.entrypoint.rest.dto.errors

class ValidationError(status: Int?, msg: String?, timeStamp: Long?) : StandardError(status, msg, timeStamp) {
    val errors: MutableList<FieldMessage> = ArrayList()
    fun addError(fieldName: String?, msg: String?) {
        errors.add(FieldMessage(fieldName, msg))
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
