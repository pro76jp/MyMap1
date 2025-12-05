package com.example.gamemasteradmin.service

import com.example.gamemasteradmin.entity.NpcTemplate
import com.example.gamemasteradmin.entity.NpcTemplateQuest
import com.example.gamemasteradmin.entity.QuestTemplate
import com.example.gamemasteradmin.repository.NpcTemplateQuestRepository
import com.example.gamemasteradmin.repository.NpcTemplateRepository
import com.example.gamemasteradmin.repository.QuestTemplateRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NpcService(
    private val repository: NpcTemplateRepository,
    private val questRepository: NpcTemplateQuestRepository,
    private val questTemplateRepository: QuestTemplateRepository
) {
    fun list(name: String?, npcType: String?, page: Int, size: Int): Page<NpcTemplate> {
        val pageable = PageRequest.of(page, size)
        val all = repository.findAll(pageable)
        return all.map { it }.filterPage { npc ->
            (name.isNullOrBlank() || npc.name.contains(name, true)) &&
                (npcType.isNullOrBlank() || npc.npcType.contains(npcType, true))
        }
    }

    fun find(id: Long) = repository.findById(id)

    @Transactional
    fun save(npcTemplate: NpcTemplate) = repository.save(npcTemplate)

    @Transactional
    fun addQuest(npcId: Long, questId: Long, relationType: String) {
        val npc = repository.findById(npcId).orElseThrow()
        val quest: QuestTemplate = questTemplateRepository.findById(questId).orElseThrow()
        val link = NpcTemplateQuest(npc = npc, quest = quest, relationType = relationType)
        npc.quests.add(link)
        questRepository.save(link)
    }

    @Transactional
    fun removeQuest(linkId: Long) = questRepository.deleteById(linkId)

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)
}
