package net.azeti.challenge.recipe.dataprovider.rest.mapper

import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.dataprovider.rest.entity.UserEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface UserEntityMapper {

    fun toDomain(entity: UserEntity) : User

    fun toEntity(domain: User) : UserEntity

}