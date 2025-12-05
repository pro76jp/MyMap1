package com.example.gamemasteradmin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "object_templates")
class ObjectTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var name: String,

    @field:NotBlank
    @field:Size(max = 50)
    var objectType: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var baseStory: Story? = null,

    @field:Size(max = 200)
    var tags: String? = null,

    var interactive: Boolean = true,

    @OneToMany(mappedBy = "objectTemplate", cascade = [CascadeType.ALL], orphanRemoval = true)
    var quests: MutableList<ObjectTemplateQuest> = mutableListOf()
)

@Entity
@Table(name = "object_template_quests")
class ObjectTemplateQuest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var objectTemplate: ObjectTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var quest: QuestTemplate? = null,

    @field:NotBlank
    @field:Size(max = 50)
    var relationType: String
)
