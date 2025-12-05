package com.example.gamemasteradmin.service

import com.example.gamemasteradmin.entity.ObjectTemplate
import com.example.gamemasteradmin.entity.ObjectTemplateQuest
import com.example.gamemasteradmin.entity.QuestTemplate
import com.example.gamemasteradmin.repository.ObjectTemplateQuestRepository
import com.example.gamemasteradmin.repository.ObjectTemplateRepository
import com.example.gamemasteradmin.repository.QuestTemplateRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ObjectService(
    private val repository: ObjectTemplateRepository,
    private val linkRepository: ObjectTemplateQuestRepository,
    private val questTemplateRepository: QuestTemplateRepository
) {
    fun list(name: String?, objectType: String?, page: Int, size: Int): Page<ObjectTemplate> {
        val pageable = PageRequest.of(page, size)
        val all = repository.findAll(pageable)
        return all.map { it }.filterPage { obj ->
            (name.isNullOrBlank() || obj.name.contains(name, true)) &&
                (objectType.isNullOrBlank() || obj.objectType.contains(objectType, true))
        }
    }

    fun find(id: Long) = repository.findById(id)

    @Transactional
    fun save(template: ObjectTemplate) = repository.save(template)

    @Transactional
    fun addQuest(objectId: Long, questId: Long, relationType: String) {
        val obj = repository.findById(objectId).orElseThrow()
        val quest: QuestTemplate = questTemplateRepository.findById(questId).orElseThrow()
        val link = ObjectTemplateQuest(objectTemplate = obj, quest = quest, relationType = relationType)
        obj.quests.add(link)
        linkRepository.save(link)
    }

    @Transactional
    fun removeQuest(linkId: Long) = linkRepository.deleteById(linkId)

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)
}
