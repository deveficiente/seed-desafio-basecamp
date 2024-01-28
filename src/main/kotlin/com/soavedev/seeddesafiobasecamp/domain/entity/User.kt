package com.soavedev.seeddesafiobasecamp.domain.entity

import com.soavedev.seeddesafiobasecamp.domain.enums.UserRoles
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
@Table(name = "tb_user")
data class User(
        @Id
        var id: UUID,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        var login: String,

        @Column(nullable = false)
        var userPassword: String,

        @Column(nullable = false, unique = true)
        var emailAddress: String,

        @Column(nullable = false)
        var role: UserRoles,

        @Column(nullable = false)
        var status: String,

        var location: String,

        var shortBio: String,

        var profilePictureUrl: String
) : UserDetails {
        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                if(role == UserRoles.ADMIN)
                        return mutableListOf(
                                SimpleGrantedAuthority("ROLE_ADMIN"),
                                SimpleGrantedAuthority("ROLE_USER")
                        )
                return mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
        }

        override fun getPassword(): String {
                return userPassword
        }

        override fun getUsername(): String {
                return emailAddress
        }

        override fun isAccountNonExpired(): Boolean {
                return true
        }

        override fun isAccountNonLocked(): Boolean {
                return true
        }

        override fun isCredentialsNonExpired(): Boolean {
                return true
        }

        override fun isEnabled(): Boolean {
                return true
        }
}
