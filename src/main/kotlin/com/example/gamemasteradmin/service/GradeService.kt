package com.example.gamemasteradmin.service

import com.example.gamemasteradmin.entity.Grade
import com.example.gamemasteradmin.repository.GradeRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GradeService(private val repository: GradeRepository) {
    fun list(type: String?, name: String?, page: Int, size: Int): Page<Grade> {
        val pageable = PageRequest.of(page, size)
        val all = repository.findAll(pageable)
        return all.map { it }.filterPage { grade ->
            (type.isNullOrBlank() || grade.type.contains(type, true)) &&
                (name.isNullOrBlank() || grade.name.contains(name, true))
        }
    }

    fun find(id: Long) = repository.findById(id)

    @Transactional
    fun save(grade: Grade): Grade {
        if (grade.id == null && repository.existsByTypeAndLevel(grade.type, grade.level)) {
            throw IllegalArgumentException("같은 타입과 레벨의 등급이 이미 존재합니다")
        }
        return repository.save(grade)
    }

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)
}
