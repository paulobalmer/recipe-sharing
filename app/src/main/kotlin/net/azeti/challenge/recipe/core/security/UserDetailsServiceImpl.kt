package net.azeti.challenge.recipe.core.security


import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.core.usecase.LoadUserByEmailUseCase
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val loadUserByEmailUseCase: LoadUserByEmailUseCase
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails? {
        val user: User? = loadUserByEmailUseCase.execute(email)
        if(user != null) {
            return CustomUserDetails(user)
        }
        throw UsernameNotFoundException("Could not find user")
    }
}