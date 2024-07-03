package net.azeti.challenge.recipe.core.security


import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.core.usecase.LoadUserByUsernameUseCase
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val loadUserByUsernameUseCase: LoadUserByUsernameUseCase
) : UserDetailsService {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        logger.info("Getting information for user $username")
        val user: User? = loadUserByUsernameUseCase.execute(username)
        if(user != null) {
            logger.warn("Information for user $username found")
            return CustomUserDetails(user)
        }
        logger.error("Could not find the user $username")
        throw UsernameNotFoundException("Could not find user")
    }
}