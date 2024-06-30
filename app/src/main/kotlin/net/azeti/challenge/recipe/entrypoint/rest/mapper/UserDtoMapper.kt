package net.azeti.challenge.recipe.entrypoint.rest.mapper

import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.CreateUserRequestDto
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.CreateUserResponseDto
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
@Mapper(componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserDtoMapper {

    fun toDomain(createUserRequest : CreateUserRequestDto) : User

    fun toCreateUserResponseDto(domain : User) : CreateUserResponseDto

}