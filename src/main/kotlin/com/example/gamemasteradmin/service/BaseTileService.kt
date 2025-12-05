package com.example.gamemasteradmin.service

import com.example.gamemasteradmin.entity.*
import com.example.gamemasteradmin.repository.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BaseTileService(
    private val repository: BaseTileTemplateRepository,
    private val objectRepository: BaseTileObjectRepository,
    private val npcRepository: BaseTileNpcRepository,
    private val questRepository: BaseTileQuestRepository,
    private val objectTemplateRepository: ObjectTemplateRepository,
    private val npcTemplateRepository: NpcTemplateRepository,
    private val questTemplateRepository: QuestTemplateRepository
) {
    fun list(name: String?, tileType: String?, page: Int, size: Int): Page<BaseTileTemplate> {
        val pageable = PageRequest.of(page, size)
        val all = repository.findAll(pageable)
        return all.map { it }.filterPage { tile ->
            (name.isNullOrBlank() || tile.name.contains(name, true)) &&
                (tileType.isNullOrBlank() || tile.tileType.contains(tileType, true))
        }
    }

    fun find(id: Long) = repository.findById(id)

    @Transactional
    fun save(template: BaseTileTemplate) = repository.save(template)

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)

    @Transactional
    fun addObject(tileId: Long, objectId: Long, required: Boolean, min: Int?, max: Int?) {
        val tile = repository.findById(tileId).orElseThrow()
        val obj = objectTemplateRepository.findById(objectId).orElseThrow()
        val link = BaseTileObject(baseTile = tile, objectTemplate = obj, required = required, minCount = min, maxCount = max)
        tile.objects.add(link)
        objectRepository.save(link)
    }

    @Transactional
    fun addNpc(tileId: Long, npcId: Long, min: Int?, max: Int?) {
        val tile = repository.findById(tileId).orElseThrow()
        val npc = npcTemplateRepository.findById(npcId).orElseThrow()
        val link = BaseTileNpc(baseTile = tile, npcTemplate = npc, minCount = min, maxCount = max)
        tile.npcs.add(link)
        npcRepository.save(link)
    }

    @Transactional
    fun addQuest(tileId: Long, questId: Long, type: String) {
        val tile = repository.findById(tileId).orElseThrow()
        val quest = questTemplateRepository.findById(questId).orElseThrow()
        val link = BaseTileQuest(baseTile = tile, quest = quest, type = type)
        tile.quests.add(link)
        questRepository.save(link)
    }

    @Transactional
    fun removeObject(id: Long) = objectRepository.deleteById(id)

    @Transactional
    fun removeNpc(id: Long) = npcRepository.deleteById(id)

    @Transactional
    fun removeQuest(id: Long) = questRepository.deleteById(id)
}
