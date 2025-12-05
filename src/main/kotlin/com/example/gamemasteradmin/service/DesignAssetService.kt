package com.example.gamemasteradmin.service

import com.example.gamemasteradmin.entity.DesignAsset
import com.example.gamemasteradmin.repository.DesignAssetRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DesignAssetService(private val repository: DesignAssetRepository) {
    fun list(targetType: String?, page: Int, size: Int): Page<DesignAsset> {
        val pageable = PageRequest.of(page, size)
        val all = repository.findAll(pageable)
        return all.map { it }.filterPage { asset ->
            targetType.isNullOrBlank() || asset.targetType.contains(targetType, true)
        }
    }

    fun find(id: Long) = repository.findById(id)

    @Transactional
    fun save(asset: DesignAsset) = repository.save(asset)

    @Transactional
    fun delete(id: Long) = repository.deleteById(id)
}
