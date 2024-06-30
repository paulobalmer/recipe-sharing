package net.azeti.challenge.recipe.entrypoint.rest.mapper

import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.CreateUserRequest
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.CreateUserResponse
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
@Mapper(componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserDtoMapper {

    fun toDomain(createUserRequest : CreateUserRequest) : User

    fun toCreateUserResponseDto(domain : User) : CreateUserResponse

}