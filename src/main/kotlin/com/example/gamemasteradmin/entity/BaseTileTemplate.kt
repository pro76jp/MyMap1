package com.example.gamemasteradmin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "base_tile_templates")
class BaseTileTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var name: String,

    @field:NotBlank
    @field:Size(max = 50)
    var tileType: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var grade: Grade? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var baseStory: Story? = null,

    @field:Size(max = 200)
    var envTags: String? = null,

    @OneToMany(mappedBy = "baseTile", cascade = [CascadeType.ALL], orphanRemoval = true)
    var objects: MutableList<BaseTileObject> = mutableListOf(),

    @OneToMany(mappedBy = "baseTile", cascade = [CascadeType.ALL], orphanRemoval = true)
    var npcs: MutableList<BaseTileNpc> = mutableListOf(),

    @OneToMany(mappedBy = "baseTile", cascade = [CascadeType.ALL], orphanRemoval = true)
    var quests: MutableList<BaseTileQuest> = mutableListOf()
)

@Entity
@Table(name = "base_tile_objects")
class BaseTileObject(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var baseTile: BaseTileTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var objectTemplate: ObjectTemplate? = null,

    var required: Boolean = false,
    var minCount: Int? = null,
    var maxCount: Int? = null
)

@Entity
@Table(name = "base_tile_npcs")
class BaseTileNpc(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var baseTile: BaseTileTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var npcTemplate: NpcTemplate? = null,

    var minCount: Int? = null,
    var maxCount: Int? = null
)

@Entity
@Table(name = "base_tile_quests")
class BaseTileQuest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var baseTile: BaseTileTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var quest: QuestTemplate? = null,

    @field:NotBlank
    @field:Size(max = 50)
    var type: String
)
