package com.example.gamemasteradmin.service

import com.example.gamemasteradmin.entity.QuestTemplate
import com.example.gamemasteradmin.repository.QuestTemplateRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestService(private val repository: QuestTemplateRepository) {
    fun list(title: String?, questType: String?, page: Int, size: Int): Page<QuestTemplate> {
        val pageable = PageRequest.of(page, size)
        val all = repository.findAll(pageable)
        return all.map { it }.filterPage { quest ->
            (title.isNullOrBlank() || quest.title.contains(title, true)) &&
                (questType.isNullOrBlank() || quest.questType.contains(questType, true))
        }
    }

    fun find(id: Long) = repository.findById(id)

    @Transactional
    fun save(questTemplate: QuestTemplate) = repository.save(questTemplate)

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)
}
