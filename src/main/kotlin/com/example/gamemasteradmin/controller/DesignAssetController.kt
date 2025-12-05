package com.example.gamemasteradmin.controller

import com.example.gamemasteradmin.entity.DesignAsset
import com.example.gamemasteradmin.service.DesignAssetService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/designs")
class DesignAssetController(private val service: DesignAssetService) {
    @GetMapping
    fun list(@RequestParam(required = false) targetType: String?,
             @RequestParam(defaultValue = "0") page: Int,
             model: Model): String {
        model.addAttribute("designs", service.list(targetType, page, 20))
        model.addAttribute("searchType", targetType)
        return "designs/list"
    }

    @GetMapping("/new")
    fun createForm(model: Model): String {
        model.addAttribute("designAsset", DesignAsset(targetType = "", targetId = 0))
        return "designs/form"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val asset = service.find(id).orElseThrow()
        model.addAttribute("designAsset", asset)
        return "designs/form"
    }

    @PostMapping
    fun save(@Valid designAsset: DesignAsset, result: BindingResult): String {
        if (result.hasErrors()) return "designs/form"
        service.save(designAsset)
        return "redirect:/admin/designs"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/designs"
    }
}
