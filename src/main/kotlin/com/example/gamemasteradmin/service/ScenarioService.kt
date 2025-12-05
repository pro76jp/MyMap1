package com.example.gamemasteradmin.service

import com.example.gamemasteradmin.entity.*
import com.example.gamemasteradmin.repository.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScenarioService(
    private val repository: ScenarioTemplateRepository,
    private val requiredTileRepository: ScenarioRequiredTileRepository,
    private val randomTileRuleRepository: ScenarioRandomTileRuleRepository,
    private val questRepository: ScenarioQuestRepository,
    private val baseTileRepository: BaseTileTemplateRepository,
    private val questTemplateRepository: QuestTemplateRepository
) {
    fun list(name: String?, page: Int, size: Int): Page<ScenarioTemplate> {
        val pageable = PageRequest.of(page, size)
        val all = repository.findAll(pageable)
        return all.map { it }.filterPage { scenario ->
            name.isNullOrBlank() || scenario.name.contains(name, true)
        }
    }

    fun find(id: Long) = repository.findById(id)

    @Transactional
    fun save(template: ScenarioTemplate) = repository.save(template)

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)

    @Transactional
    fun addRequiredTile(scenarioId: Long, baseTileId: Long, orderIndex: Int?) {
        val scenario = repository.findById(scenarioId).orElseThrow()
        val baseTile = baseTileRepository.findById(baseTileId).orElseThrow()
        val link = ScenarioRequiredTile(scenario = scenario, baseTile = baseTile, orderIndex = orderIndex)
        scenario.requiredTiles.add(link)
        requiredTileRepository.save(link)
    }

    @Transactional
    fun addRandomRule(scenarioId: Long, tileType: String?, gradeMin: Int?, gradeMax: Int?, count: Int) {
        val scenario = repository.findById(scenarioId).orElseThrow()
        val rule = ScenarioRandomTileRule(
            scenario = scenario,
            tileTypeFilter = tileType,
            gradeMin = gradeMin,
            gradeMax = gradeMax,
            count = count
        )
        scenario.randomTileRules.add(rule)
        randomTileRuleRepository.save(rule)
    }

    @Transactional
    fun addQuest(scenarioId: Long, questId: Long, type: String) {
        val scenario = repository.findById(scenarioId).orElseThrow()
        val quest = questTemplateRepository.findById(questId).orElseThrow()
        val link = ScenarioQuest(scenario = scenario, quest = quest, type = type)
        scenario.quests.add(link)
        questRepository.save(link)
    }

    @Transactional
    fun removeRequiredTile(id: Long) = requiredTileRepository.deleteById(id)

    @Transactional
    fun removeRandomRule(id: Long) = randomTileRuleRepository.deleteById(id)

    @Transactional
    fun removeQuest(id: Long) = questRepository.deleteById(id)
}
