package com.example.gamemasteradmin.controller

import com.example.gamemasteradmin.entity.ObjectTemplate
import com.example.gamemasteradmin.service.ObjectService
import com.example.gamemasteradmin.service.QuestService
import com.example.gamemasteradmin.service.StoryService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/objects")
class ObjectController(
    private val service: ObjectService,
    private val storyService: StoryService,
    private val questService: QuestService
) {
    @GetMapping
    fun list(@RequestParam(required = false) name: String?,
             @RequestParam(required = false) objectType: String?,
             @RequestParam(defaultValue = "0") page: Int,
             model: Model): String {
        model.addAttribute("objects", service.list(name, objectType, page, 20))
        model.addAttribute("searchName", name)
        model.addAttribute("searchType", objectType)
        return "objects/list"
    }

    @GetMapping("/new")
    fun createForm(model: Model): String {
        model.addAttribute("objectTemplate", ObjectTemplate(name = "", objectType = ""))
        model.addAttribute("stories", storyService.list(null, null, 0, 100).content)
        return "objects/form"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val obj = service.find(id).orElseThrow()
        model.addAttribute("objectTemplate", obj)
        model.addAttribute("stories", storyService.list(null, null, 0, 100).content)
        model.addAttribute("quests", questService.list(null, null, 0, 100).content)
        return "objects/form"
    }

    @PostMapping
    fun save(@Valid objectTemplate: ObjectTemplate, result: BindingResult, model: Model): String {
        if (result.hasErrors()) {
            model.addAttribute("stories", storyService.list(null, null, 0, 100).content)
            return "objects/form"
        }
        service.save(objectTemplate)
        return "redirect:/admin/objects"
    }

    @PostMapping("/{objectId}/quests")
    fun addQuest(@PathVariable objectId: Long, @RequestParam questId: Long, @RequestParam relationType: String): String {
        service.addQuest(objectId, questId, relationType)
        return "redirect:/admin/objects/$objectId"
    }

    @PostMapping("/{objectId}/quests/{linkId}/delete")
    fun deleteQuest(@PathVariable objectId: Long, @PathVariable linkId: Long): String {
        service.removeQuest(linkId)
        return "redirect:/admin/objects/$objectId"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/objects"
    }
}
