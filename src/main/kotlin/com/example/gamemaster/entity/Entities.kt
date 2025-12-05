package com.example.gamemaster.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "grades", uniqueConstraints = [UniqueConstraint(columnNames = ["type", "level"])])
class Grade(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 50)
    var type: String = "",

    var level: Int = 1,

    @field:NotBlank
    @field:Size(max = 100)
    var name: String = "",

    var recommendedPlayerLevelMin: Int? = null,
    var recommendedPlayerLevelMax: Int? = null,
    var difficultyScore: Int? = null,

    @Column(columnDefinition = "TEXT")
    var extraConfig: String? = null
)

@Entity
@Table(name = "stories")
class Story(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var title: String = "",

    @Column(columnDefinition = "TEXT")
    var body: String = "",

    @field:NotBlank
    @field:Size(max = 50)
    var type: String = "",

    @field:Size(max = 200)
    var tags: String? = null
)

@Entity
@Table(name = "quest_templates")
class QuestTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var title: String = "",

    @Column(columnDefinition = "TEXT")
    var description: String = "",

    @field:Size(max = 50)
    var questType: String = "",

    @field:Size(max = 50)
    var giverType: String = "",

    var giverRefId: Long? = null,

    @Column(columnDefinition = "TEXT")
    var requirementJson: String? = null,

    @Column(columnDefinition = "TEXT")
    var rewardJson: String? = null,

    var availableFromScenarioId: Long? = null
)

@Entity
@Table(name = "npc_templates")
class NpcTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var name: String = "",

    @field:Size(max = 50)
    var npcType: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_story_id")
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
    @JoinColumn(name = "npc_id")
    var npc: NpcTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    var quest: QuestTemplate? = null,

    @field:Size(max = 50)
    var relationType: String = ""
)

@Entity
@Table(name = "object_templates")
class ObjectTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var name: String = "",

    @field:Size(max = 50)
    var objectType: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_story_id")
    var baseStory: Story? = null,

    @field:Size(max = 200)
    var tags: String? = null,

    var interactive: Boolean = false,

    @OneToMany(mappedBy = "objectTemplate", cascade = [CascadeType.ALL], orphanRemoval = true)
    var quests: MutableList<ObjectTemplateQuest> = mutableListOf()
)

@Entity
@Table(name = "object_template_quests")
class ObjectTemplateQuest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_template_id")
    var objectTemplate: ObjectTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    var quest: QuestTemplate? = null,

    @field:Size(max = 50)
    var relationType: String = ""
)

@Entity
@Table(name = "base_tile_templates")
class BaseTileTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var name: String = "",

    @field:Size(max = 50)
    var tileType: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id")
    var grade: Grade? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_story_id")
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
    @JoinColumn(name = "base_tile_id")
    var baseTile: BaseTileTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_template_id")
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
    @JoinColumn(name = "base_tile_id")
    var baseTile: BaseTileTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "npc_template_id")
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
    @JoinColumn(name = "base_tile_id")
    var baseTile: BaseTileTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    var quest: QuestTemplate? = null,

    @field:Size(max = 50)
    var type: String = ""
)

@Entity
@Table(name = "scenario_templates")
class ScenarioTemplate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotBlank
    @field:Size(max = 200)
    var name: String = "",

    @Column(columnDefinition = "TEXT")
    var description: String = "",

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
    @JoinColumn(name = "scenario_id")
    var scenario: ScenarioTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_tile_id")
    var baseTile: BaseTileTemplate? = null,

    var orderIndex: Int? = null
)

@Entity
@Table(name = "scenario_random_tile_rules")
class ScenarioRandomTileRule(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id")
    var scenario: ScenarioTemplate? = null,

    @field:Size(max = 50)
    var tileTypeFilter: String? = null,
    var gradeMin: Int? = null,
    var gradeMax: Int? = null,
    var count: Int = 1
)

@Entity
@Table(name = "scenario_quests")
class ScenarioQuest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id")
    var scenario: ScenarioTemplate? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    var quest: QuestTemplate? = null,

    @field:Size(max = 50)
    var type: String = ""
)

@Entity
@Table(name = "design_assets")
class DesignAsset(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:Size(max = 50)
    var targetType: String = "",

    var targetId: Long = 0,

    @field:Size(max = 255)
    var imagePath: String? = null,

    @field:Size(max = 255)
    var spriteSheetPath: String? = null,

    @field:Size(max = 100)
    var animationId: String? = null,

    @field:Size(max = 100)
    var variantName: String? = null
)
