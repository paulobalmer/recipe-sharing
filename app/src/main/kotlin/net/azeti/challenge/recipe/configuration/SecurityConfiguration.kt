package net.azeti.challenge.recipe.configuration

import jakarta.servlet.http.HttpServletResponse
import net.azeti.challenge.recipe.core.security.UserDetailsServiceImpl
import net.azeti.challenge.recipe.core.security.jwt.JwtTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfiguration(
    private val userDetailsService: UserDetailsServiceImpl
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Throws(Exception::class)
    protected fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }


    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Autowired
    private val jwtTokenFilter: JwtTokenFilter? = null

    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val hierarchy = RoleHierarchyImpl()
        hierarchy.setHierarchy("ADMIN > USER")
        return hierarchy
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {
            it.requestMatchers("/auth/token").permitAll()
            it.requestMatchers(HttpMethod.POST, "/users").permitAll()
            it.anyRequest().authenticated()
        }
        http.formLogin { it.disable() }
        http.logout { it.disable() }
        http.cors{ it.disable() }
        http.csrf { it.disable() }

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}