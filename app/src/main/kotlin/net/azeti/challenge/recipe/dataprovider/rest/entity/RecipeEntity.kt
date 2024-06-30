package net.azeti.challenge.recipe.dataprovider.rest.entity

import jakarta.persistence.*
import java.io.Serializable
import java.util.Date
import java.util.UUID

@Entity
@Table(name = RecipeEntity.tableName, schema = RecipeEntity.schema)
class RecipeEntity : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: UUID = UUID.randomUUID()

    @Column(name = "title")
    lateinit var title: String

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: UserEntity

    @Column(name = "description")
    var description: String? = null

    @Column(name = "instructions")
    lateinit var instructions: String

    @Column(name = "servings")
    var servings: String = ""

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = false)
    var ingredients: List<IngredientEntity>? = mutableListOf()

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date = Date()

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date? = null

    companion object {
        const val schema: String = "public"
        const val tableName: String = "recipes"
    }

    constructor()

    constructor(
        id: UUID,
        title: String,
        user: UserEntity,
        description: String?,
        instructions: String,
        servings: String,
        ingredients: List<IngredientEntity>,
        createdAt: Date,
        updatedAt: Date?
    ) {
        this.id = id
        this.title = title
        this.user = user
        this.description = description
        this.instructions = instructions
        this.servings = servings
        this.ingredients = ingredients
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}
