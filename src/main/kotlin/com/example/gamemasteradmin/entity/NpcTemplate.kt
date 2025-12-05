package com.example.gamemasteradmin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "npc_templates")
class NpcTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var name: String,

    @field:NotBlank
    @field:Size(max = 50)
    var npcType: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var baseStory: Story? = null,

    @field:Size(max = 200)
    var tags: String? = null,

    var defaultAffinityStart: Int? = null,

    @OneToMany(mappedBy = "npc", cascade = [CascadeType.ALL], orphanRemoval = true)
    var quests: MutableList<NpcTemplateQuest> = mutableListOf()
)

@Entity
@Table(name = "npc_template_quests")
class NpcTemplateQuest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var npc: NpcTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var quest: QuestTemplate? = null,

    @field:NotBlank
    @field:Size(max = 50)
    var relationType: String
)
