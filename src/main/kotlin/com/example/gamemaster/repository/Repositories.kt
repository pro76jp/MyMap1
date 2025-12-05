package com.example.gamemaster.repository

import com.example.gamemaster.entity.*
import org.springframework.data.jpa.repository.JpaRepository

interface GradeRepository : JpaRepository<Grade, Long> {
    fun findByTypeContainingIgnoreCaseAndNameContainingIgnoreCase(type: String, name: String): List<Grade>
    fun existsByTypeAndLevel(type: String, level: Int): Boolean
}

interface StoryRepository : JpaRepository<Story, Long> {
    fun findByTitleContainingIgnoreCaseOrTypeContainingIgnoreCase(title: String, type: String): List<Story>
}

interface QuestTemplateRepository : JpaRepository<QuestTemplate, Long> {
    fun findByTitleContainingIgnoreCase(title: String): List<QuestTemplate>
}

interface NpcTemplateRepository : JpaRepository<NpcTemplate, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<NpcTemplate>
}

interface NpcTemplateQuestRepository : JpaRepository<NpcTemplateQuest, Long>

interface ObjectTemplateRepository : JpaRepository<ObjectTemplate, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<ObjectTemplate>
}

interface ObjectTemplateQuestRepository : JpaRepository<ObjectTemplateQuest, Long>

interface BaseTileTemplateRepository : JpaRepository<BaseTileTemplate, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<BaseTileTemplate>
}

interface BaseTileObjectRepository : JpaRepository<BaseTileObject, Long>
interface BaseTileNpcRepository : JpaRepository<BaseTileNpc, Long>
interface BaseTileQuestRepository : JpaRepository<BaseTileQuest, Long>

interface ScenarioTemplateRepository : JpaRepository<ScenarioTemplate, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<ScenarioTemplate>
}

interface ScenarioRequiredTileRepository : JpaRepository<ScenarioRequiredTile, Long>
interface ScenarioRandomTileRuleRepository : JpaRepository<ScenarioRandomTileRule, Long>
interface ScenarioQuestRepository : JpaRepository<ScenarioQuest, Long>

interface DesignAssetRepository : JpaRepository<DesignAsset, Long> {
    fun findByTargetTypeContainingIgnoreCase(targetType: String): List<DesignAsset>
}
