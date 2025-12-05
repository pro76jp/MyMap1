package com.example.gamemasteradmin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "design_assets")
class DesignAsset(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 50)
    var targetType: String,

    var targetId: Long,

    @field:Size(max = 500)
    var imagePath: String? = null,

    @field:Size(max = 500)
    var spriteSheetPath: String? = null,

    @field:Size(max = 100)
    var animationId: String? = null,

    @field:Size(max = 100)
    var variantName: String? = null
)
