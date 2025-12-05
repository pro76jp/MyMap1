package com.example.gamemasteradmin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "quest_templates")
class QuestTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var title: String,

    @Lob
    @field:NotBlank
    var description: String,

    @field:NotBlank
    @field:Size(max = 50)
    var questType: String,

    @field:NotBlank
    @field:Size(max = 50)
    var giverType: String,

    var giverRefId: Long? = null,

    @Lob
    var requirementJson: String? = null,

    @Lob
    var rewardJson: String? = null,

    var availableFromScenarioId: Long? = null
)
