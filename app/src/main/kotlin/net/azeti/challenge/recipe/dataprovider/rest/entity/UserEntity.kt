package net.azeti.challenge.recipe.dataprovider.rest.entity

import jakarta.persistence.*
import java.io.Serializable
import java.util.Date
import java.util.UUID

@Entity
@Table(name = UserEntity.tableName, schema = UserEntity.schema)
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "`name`")
    val name: String,

    @Column(name = "username")
    val username: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "`password`")
    var password: String,

    @Column(name = "active")
    val active: Boolean = true,

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date = Date(),

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date? = null,

) : Serializable {

    companion object {
        const val schema: String = "public"
        const val tableName: String = "users"
    }
}
