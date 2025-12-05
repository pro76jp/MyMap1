package com.example.gamemasteradmin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "grades", uniqueConstraints = [UniqueConstraint(columnNames = ["type", "level"])])
class Grade(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 50)
    var type: String,

    @field:NotNull
    @field:Min(1)
    var level: Int,

    @field:NotBlank
    @field:Size(max = 100)
    var name: String,

    var recommendedPlayerLevelMin: Int? = null,
    var recommendedPlayerLevelMax: Int? = null,
    var difficultyScore: Int? = null,

    @field:Size(max = 2000)
    var extraConfig: String? = null
)
