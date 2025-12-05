package com.example.gamemasteradmin.service

import com.example.gamemasteradmin.entity.Story
import com.example.gamemasteradmin.repository.StoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StoryService(private val repository: StoryRepository) {
    fun list(title: String?, type: String?, page: Int, size: Int): Page<Story> {
        val pageable = PageRequest.of(page, size)
        val all = repository.findAll(pageable)
        return all.map { it }.filterPage { story ->
            (title.isNullOrBlank() || story.title.contains(title, true)) &&
                (type.isNullOrBlank() || story.type.contains(type, true))
        }
    }

    fun find(id: Long) = repository.findById(id)

    @Transactional
    fun save(story: Story) = repository.save(story)

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)
}
