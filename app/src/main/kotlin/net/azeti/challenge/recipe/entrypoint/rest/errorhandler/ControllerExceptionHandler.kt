package net.azeti.challenge.recipe.entrypoint.rest.errorhandler

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import jakarta.servlet.http.HttpServletRequest
import net.azeti.challenge.recipe.entrypoint.rest.dto.errors.StandardError
import net.azeti.challenge.recipe.entrypoint.rest.dto.errors.ValidationError
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.AuthorizationException
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.DataIntegrityException
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.ObjectNotFoundException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(AuthorizationException::class)
    fun businessException(e: AuthorizationException): ResponseEntity<StandardError> {
        val err = StandardError(HttpStatus.UNAUTHORIZED.value(), e.message, System.currentTimeMillis())
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body<StandardError>(err)
    }

    @ExceptionHandler(BusinessException::class)
    fun businessException(e: BusinessException): ResponseEntity<ValidationError> {
        val err = ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.message, System.currentTimeMillis())
        for (x in e.listErrors()) {
            err.addError(x.fieldName, x.message)
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body<ValidationError>(err)
    }

    @ExceptionHandler(ObjectNotFoundException::class)
    fun objectNotFound(e: ObjectNotFoundException): ResponseEntity<StandardError> {
        val err = StandardError(HttpStatus.NOT_FOUND.value(), e.message, System.currentTimeMillis())
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body<StandardError>(err)
    }

    @ExceptionHandler(DataIntegrityException::class)
    fun dataIntegrity(e: DataIntegrityException): ResponseEntity<StandardError> {
        val err = StandardError(HttpStatus.BAD_REQUEST.value(), e.message, System.currentTimeMillis())
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<StandardError>(err)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validation(e: MethodArgumentNotValidException): HttpEntity<ValidationError> {
        val err = ValidationError(
            HttpStatus.BAD_REQUEST.value(),
            "invalid request! mandatory fields are blank or missing!", System.currentTimeMillis()
        )
        for (x in e.bindingResult.fieldErrors) {
            err.addError(x.field, x.defaultMessage)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<ValidationError>(err)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<StandardError> {
        val cause = e.cause
        val errorMessage = if (cause is MismatchedInputException) {
            "The field '${cause.path.joinToString(".") { it.fieldName ?: "unknown" }}' is missing or null"
        } else {
            "Invalid request payload"
        }

        val err = StandardError(HttpStatus.BAD_REQUEST.value(), errorMessage, System.currentTimeMillis())
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException,
        request: HttpServletRequest
    ): ResponseEntity<StandardError> {
        val err = StandardError(HttpStatus.BAD_REQUEST.value(), "Provided value is invalid for this field", System.currentTimeMillis())
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err)

    }
}
