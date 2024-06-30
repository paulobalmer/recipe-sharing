package net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception

class InternalServerErrorException : RuntimeException {
    constructor(msg: String?) : super(msg)
    constructor(msg: String?, cause: Throwable?) : super(msg, cause)

    companion object {
        private const val serialVersionUID = 1L
    }
}
