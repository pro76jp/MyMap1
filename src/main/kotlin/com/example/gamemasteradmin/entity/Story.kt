package com.example.gamemasteradmin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "stories")
class Story(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var title: String,

    @Lob
    @field:NotBlank
    var body: String,

    @field:NotBlank
    @field:Size(max = 50)
    var type: String,

    @field:Size(max = 200)
    var tags: String? = null
)
