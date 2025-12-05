package com.example.gamemasteradmin.controller

import com.example.gamemasteradmin.entity.NpcTemplate
import com.example.gamemasteradmin.service.NpcService
import com.example.gamemasteradmin.service.QuestService
import com.example.gamemasteradmin.service.StoryService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/npcs")
class NpcController(
    private val service: NpcService,
    private val storyService: StoryService,
    private val questService: QuestService
) {
    @GetMapping
    fun list(@RequestParam(required = false) name: String?,
             @RequestParam(required = false) npcType: String?,
             @RequestParam(defaultValue = "0") page: Int,
             model: Model): String {
        model.addAttribute("npcs", service.list(name, npcType, page, 20))
        model.addAttribute("searchName", name)
        model.addAttribute("searchType", npcType)
        return "npcs/list"
    }

    @GetMapping("/new")
    fun createForm(model: Model): String {
        model.addAttribute("npcTemplate", NpcTemplate(name = "", npcType = ""))
        model.addAttribute("stories", storyService.list(null, null, 0, 100).content)
        return "npcs/form"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val npc = service.find(id).orElseThrow()
        model.addAttribute("npcTemplate", npc)
        model.addAttribute("stories", storyService.list(null, null, 0, 100).content)
        model.addAttribute("quests", questService.list(null, null, 0, 100).content)
        return "npcs/form"
    }

    @PostMapping
    fun save(@Valid npcTemplate: NpcTemplate, result: BindingResult, model: Model): String {
        if (result.hasErrors()) {
            model.addAttribute("stories", storyService.list(null, null, 0, 100).content)
            return "npcs/form"
        }
        service.save(npcTemplate)
        return "redirect:/admin/npcs"
    }

    @PostMapping("/{npcId}/quests")
    fun addQuest(@PathVariable npcId: Long, @RequestParam questId: Long, @RequestParam relationType: String): String {
        service.addQuest(npcId, questId, relationType)
        return "redirect:/admin/npcs/$npcId"
    }

    @PostMapping("/{npcId}/quests/{linkId}/delete")
    fun deleteQuest(@PathVariable npcId: Long, @PathVariable linkId: Long): String {
        service.removeQuest(linkId)
        return "redirect:/admin/npcs/$npcId"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/npcs"
    }
}
