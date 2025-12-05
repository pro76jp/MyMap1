package com.example.gamemasteradmin.controller

import com.example.gamemasteradmin.entity.QuestTemplate
import com.example.gamemasteradmin.service.QuestService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/quests")
class QuestController(private val service: QuestService) {
    @GetMapping
    fun list(@RequestParam(required = false) title: String?,
             @RequestParam(required = false) questType: String?,
             @RequestParam(defaultValue = "0") page: Int,
             model: Model): String {
        model.addAttribute("quests", service.list(title, questType, page, 20))
        model.addAttribute("searchTitle", title)
        model.addAttribute("searchQuestType", questType)
        return "quests/list"
    }

    @GetMapping("/new")
    fun createForm(model: Model): String {
        model.addAttribute("questTemplate", QuestTemplate(title = "", description = "", questType = "", giverType = ""))
        return "quests/form"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val quest = service.find(id).orElseThrow()
        model.addAttribute("questTemplate", quest)
        return "quests/form"
    }

    @PostMapping
    fun save(@Valid questTemplate: QuestTemplate, result: BindingResult): String {
        if (result.hasErrors()) return "quests/form"
        service.save(questTemplate)
        return "redirect:/admin/quests"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/quests"
    }
}
