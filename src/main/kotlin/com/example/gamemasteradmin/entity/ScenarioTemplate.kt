package com.example.gamemasteradmin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "scenario_templates")
class ScenarioTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var name: String,

    @Lob
    @field:NotBlank
    var description: String,

    var recommendedLevelMin: Int? = null,
    var recommendedLevelMax: Int? = null,
    var randomTileCount: Int? = null,

    @OneToMany(mappedBy = "scenario", cascade = [CascadeType.ALL], orphanRemoval = true)
    var requiredTiles: MutableList<ScenarioRequiredTile> = mutableListOf(),

    @OneToMany(mappedBy = "scenario", cascade = [CascadeType.ALL], orphanRemoval = true)
    var randomTileRules: MutableList<ScenarioRandomTileRule> = mutableListOf(),

    @OneToMany(mappedBy = "scenario", cascade = [CascadeType.ALL], orphanRemoval = true)
    var quests: MutableList<ScenarioQuest> = mutableListOf()
)

@Entity
@Table(name = "scenario_required_tiles")
class ScenarioRequiredTile(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var scenario: ScenarioTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var baseTile: BaseTileTemplate? = null,

    var orderIndex: Int? = null
)

@Entity
@Table(name = "scenario_random_tile_rules")
class ScenarioRandomTileRule(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var scenario: ScenarioTemplate? = null,

    var tileTypeFilter: String? = null,
    var gradeMin: Int? = null,
    var gradeMax: Int? = null,
    var count: Int = 0
)

@Entity
@Table(name = "scenario_quests")
class ScenarioQuest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var scenario: ScenarioTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var quest: QuestTemplate? = null,

    @field:NotBlank
    @field:Size(max = 50)
    var type: String
)
