package com.example.gamemaster.service

import com.example.gamemaster.entity.*
import com.example.gamemaster.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class GradeService(private val repository: GradeRepository) {
    fun list(type: String?, name: String?): List<Grade> {
        val t = type ?: ""
        val n = name ?: ""
        return repository.findByTypeContainingIgnoreCaseAndNameContainingIgnoreCase(t, n)
    }

    fun get(id: Long): Grade = repository.findById(id).orElseThrow()

    fun save(grade: Grade): Grade {
        if (grade.id == null && repository.existsByTypeAndLevel(grade.type, grade.level)) {
            throw IllegalArgumentException("이미 동일한 타입/레벨 등급이 존재합니다")
        }
        return repository.save(grade)
    }

    fun delete(id: Long) = repository.deleteById(id)
}

@Service
class StoryService(private val repository: StoryRepository) {
    fun list(keyword: String?): List<Story> {
        val term = keyword ?: ""
        return repository.findByTitleContainingIgnoreCaseOrTypeContainingIgnoreCase(term, term)
    }

    fun get(id: Long): Story = repository.findById(id).orElseThrow()
    fun save(story: Story): Story = repository.save(story)
    fun delete(id: Long) = repository.deleteById(id)
}

@Service
class QuestTemplateService(private val repository: QuestTemplateRepository) {
    fun list(keyword: String?): List<QuestTemplate> = repository.findByTitleContainingIgnoreCase(keyword ?: "")
    fun get(id: Long): QuestTemplate = repository.findById(id).orElseThrow()
    fun save(quest: QuestTemplate): QuestTemplate = repository.save(quest)
    fun delete(id: Long) = repository.deleteById(id)
}

@Service
class NpcTemplateService(
    private val repository: NpcTemplateRepository,
    private val questRepository: QuestTemplateRepository,
    private val npcQuestRepository: NpcTemplateQuestRepository
) {
    fun list(keyword: String?): List<NpcTemplate> = repository.findByNameContainingIgnoreCase(keyword ?: "")
    fun get(id: Long): NpcTemplate = repository.findById(id).orElseThrow()
    fun save(npc: NpcTemplate): NpcTemplate = repository.save(npc)
    fun delete(id: Long) = repository.deleteById(id)

    @Transactional
    fun addQuest(npcId: Long, questId: Long, relationType: String) {
        val npc = get(npcId)
        val quest = questRepository.findById(questId).orElseThrow()
        val link = NpcTemplateQuest(npc = npc, quest = quest, relationType = relationType)
        npc.quests.add(link)
        npcQuestRepository.save(link)
    }

    @Transactional
    fun removeQuest(linkId: Long) {
        npcQuestRepository.deleteById(linkId)
    }
}

@Service
class ObjectTemplateService(
    private val repository: ObjectTemplateRepository,
    private val questRepository: QuestTemplateRepository,
    private val objectQuestRepository: ObjectTemplateQuestRepository
) {
    fun list(keyword: String?): List<ObjectTemplate> = repository.findByNameContainingIgnoreCase(keyword ?: "")
    fun get(id: Long): ObjectTemplate = repository.findById(id).orElseThrow()
    fun save(obj: ObjectTemplate): ObjectTemplate = repository.save(obj)
    fun delete(id: Long) = repository.deleteById(id)

    @Transactional
    fun addQuest(objectId: Long, questId: Long, relationType: String) {
        val obj = get(objectId)
        val quest = questRepository.findById(questId).orElseThrow()
        val link = ObjectTemplateQuest(objectTemplate = obj, quest = quest, relationType = relationType)
        obj.quests.add(link)
        objectQuestRepository.save(link)
    }

    @Transactional
    fun removeQuest(linkId: Long) = objectQuestRepository.deleteById(linkId)
}

@Service
class BaseTileTemplateService(
    private val repository: BaseTileTemplateRepository,
    private val objectRepository: ObjectTemplateRepository,
    private val npcRepository: NpcTemplateRepository,
    private val questRepository: QuestTemplateRepository,
    private val tileObjectRepository: BaseTileObjectRepository,
    private val tileNpcRepository: BaseTileNpcRepository,
    private val tileQuestRepository: BaseTileQuestRepository
) {
    fun list(keyword: String?): List<BaseTileTemplate> = repository.findByNameContainingIgnoreCase(keyword ?: "")
    fun get(id: Long): BaseTileTemplate = repository.findById(id).orElseThrow()
    fun save(tile: BaseTileTemplate): BaseTileTemplate = repository.save(tile)
    fun delete(id: Long) = repository.deleteById(id)

    @Transactional
    fun addObject(tileId: Long, objectId: Long, required: Boolean, min: Int?, max: Int?) {
        val tile = get(tileId)
        val obj = objectRepository.findById(objectId).orElseThrow()
        val link = BaseTileObject(baseTile = tile, objectTemplate = obj, required = required, minCount = min, maxCount = max)
        tile.objects.add(link)
        tileObjectRepository.save(link)
    }

    @Transactional
    fun addNpc(tileId: Long, npcId: Long, min: Int?, max: Int?) {
        val tile = get(tileId)
        val npc = npcRepository.findById(npcId).orElseThrow()
        val link = BaseTileNpc(baseTile = tile, npcTemplate = npc, minCount = min, maxCount = max)
        tile.npcs.add(link)
        tileNpcRepository.save(link)
    }

    @Transactional
    fun addQuest(tileId: Long, questId: Long, type: String) {
        val tile = get(tileId)
        val quest = questRepository.findById(questId).orElseThrow()
        val link = BaseTileQuest(baseTile = tile, quest = quest, type = type)
        tile.quests.add(link)
        tileQuestRepository.save(link)
    }

    fun removeObject(id: Long) = tileObjectRepository.deleteById(id)
    fun removeNpc(id: Long) = tileNpcRepository.deleteById(id)
    fun removeQuest(id: Long) = tileQuestRepository.deleteById(id)
}

@Service
class ScenarioTemplateService(
    private val repository: ScenarioTemplateRepository,
    private val baseTileRepository: BaseTileTemplateRepository,
    private val questRepository: QuestTemplateRepository,
    private val requiredTileRepository: ScenarioRequiredTileRepository,
    private val randomRuleRepository: ScenarioRandomTileRuleRepository,
    private val scenarioQuestRepository: ScenarioQuestRepository
) {
    fun list(keyword: String?): List<ScenarioTemplate> = repository.findByNameContainingIgnoreCase(keyword ?: "")
    fun get(id: Long): ScenarioTemplate = repository.findById(id).orElseThrow()
    fun save(scenario: ScenarioTemplate): ScenarioTemplate = repository.save(scenario)
    fun delete(id: Long) = repository.deleteById(id)

    @Transactional
    fun addRequiredTile(scenarioId: Long, tileId: Long, orderIndex: Int?) {
        val scenario = get(scenarioId)
        val tile = baseTileRepository.findById(tileId).orElseThrow()
        val link = ScenarioRequiredTile(scenario = scenario, baseTile = tile, orderIndex = orderIndex)
        scenario.requiredTiles.add(link)
        requiredTileRepository.save(link)
    }

    @Transactional
    fun addRandomRule(scenarioId: Long, tileType: String?, gradeMin: Int?, gradeMax: Int?, count: Int) {
        val scenario = get(scenarioId)
        val rule = ScenarioRandomTileRule(scenario = scenario, tileTypeFilter = tileType, gradeMin = gradeMin, gradeMax = gradeMax, count = count)
        scenario.randomTileRules.add(rule)
        randomRuleRepository.save(rule)
    }

    @Transactional
    fun addScenarioQuest(scenarioId: Long, questId: Long, type: String) {
        val scenario = get(scenarioId)
        val quest = questRepository.findById(questId).orElseThrow()
        val link = ScenarioQuest(scenario = scenario, quest = quest, type = type)
        scenario.quests.add(link)
        scenarioQuestRepository.save(link)
    }

    fun removeRequiredTile(id: Long) = requiredTileRepository.deleteById(id)
    fun removeRandomRule(id: Long) = randomRuleRepository.deleteById(id)
    fun removeScenarioQuest(id: Long) = scenarioQuestRepository.deleteById(id)
}

@Service
class DesignAssetService(private val repository: DesignAssetRepository) {
    fun list(keyword: String?): List<DesignAsset> = repository.findByTargetTypeContainingIgnoreCase(keyword ?: "")
    fun get(id: Long): DesignAsset = repository.findById(id).orElseThrow()
    fun save(asset: DesignAsset): DesignAsset = repository.save(asset)
    fun delete(id: Long) = repository.deleteById(id)
}
