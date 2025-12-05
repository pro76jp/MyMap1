package com.example.gamemasteradmin.repository

import com.example.gamemasteradmin.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GradeRepository : JpaRepository<Grade, Long> {
    fun existsByTypeAndLevel(type: String, level: Int): Boolean
}

@Repository
interface StoryRepository : JpaRepository<Story, Long>

@Repository
interface QuestTemplateRepository : JpaRepository<QuestTemplate, Long>

@Repository
interface NpcTemplateRepository : JpaRepository<NpcTemplate, Long>

@Repository
interface NpcTemplateQuestRepository : JpaRepository<NpcTemplateQuest, Long>

@Repository
interface ObjectTemplateRepository : JpaRepository<ObjectTemplate, Long>

@Repository
interface ObjectTemplateQuestRepository : JpaRepository<ObjectTemplateQuest, Long>

@Repository
interface BaseTileTemplateRepository : JpaRepository<BaseTileTemplate, Long>

@Repository
interface BaseTileObjectRepository : JpaRepository<BaseTileObject, Long>

@Repository
interface BaseTileNpcRepository : JpaRepository<BaseTileNpc, Long>

@Repository
interface BaseTileQuestRepository : JpaRepository<BaseTileQuest, Long>

@Repository
interface ScenarioTemplateRepository : JpaRepository<ScenarioTemplate, Long>

@Repository
interface ScenarioRequiredTileRepository : JpaRepository<ScenarioRequiredTile, Long>

@Repository
interface ScenarioRandomTileRuleRepository : JpaRepository<ScenarioRandomTileRule, Long>

@Repository
interface ScenarioQuestRepository : JpaRepository<ScenarioQuest, Long>

@Repository
interface DesignAssetRepository : JpaRepository<DesignAsset, Long>
