package net.azeti.challenge.recipe.dataprovider.rest.entity

import jakarta.persistence.*
import net.azeti.challenge.recipe.core.enums.IngredientUnitEnum
import java.io.Serializable
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

@Entity
@Table(name = IngredientEntity.tableName, schema = IngredientEntity.schema)
class IngredientEntity : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: UUID = UUID.randomUUID()

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    lateinit var recipe: RecipeEntity

    @Column(name = "value")
    lateinit var value: BigDecimal

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    lateinit var unit: IngredientUnitEnum

    @Column(name = "type")
    lateinit var type: String

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date = Date()

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date? = null

    companion object {
        const val schema: String = "public"
        const val tableName: String = "ingredients"
    }

    constructor()

    constructor(
        id: UUID,
        recipe: RecipeEntity,
        value: BigDecimal,
        unit: IngredientUnitEnum,
        type: String,
        createdAt: Date,
        updatedAt: Date?
    ) {
        this.id = id
        this.recipe = recipe
        this.value = value
        this.unit = unit
        this.type = type
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}
