package com.example.gamemasteradmin.controller

import com.example.gamemasteradmin.entity.BaseTileTemplate
import com.example.gamemasteradmin.service.*
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/base-tiles")
class BaseTileController(
    private val service: BaseTileService,
    private val gradeService: GradeService,
    private val storyService: StoryService,
    private val objectService: ObjectService,
    private val npcService: NpcService,
    private val questService: QuestService
) {
    @GetMapping
    fun list(@RequestParam(required = false) name: String?,
             @RequestParam(required = false) tileType: String?,
             @RequestParam(defaultValue = "0") page: Int,
             model: Model): String {
        model.addAttribute("tiles", service.list(name, tileType, page, 20))
        model.addAttribute("searchName", name)
        model.addAttribute("searchType", tileType)
        return "basetiles/list"
    }

    @GetMapping("/new")
    fun createForm(model: Model): String {
        setupForm(model)
        model.addAttribute("baseTileTemplate", BaseTileTemplate(name = "", tileType = ""))
        return "basetiles/form"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        setupForm(model)
        val tile = service.find(id).orElseThrow()
        model.addAttribute("baseTileTemplate", tile)
        return "basetiles/form"
    }

    @PostMapping
    fun save(@Valid baseTileTemplate: BaseTileTemplate, result: BindingResult, model: Model): String {
        if (result.hasErrors()) {
            setupForm(model)
            return "basetiles/form"
        }
        service.save(baseTileTemplate)
        return "redirect:/admin/base-tiles"
    }

    @PostMapping("/{tileId}/objects")
    fun addObject(@PathVariable tileId: Long, @RequestParam objectId: Long,
                  @RequestParam(required = false, defaultValue = "false") required: Boolean,
                  @RequestParam(required = false) minCount: Int?,
                  @RequestParam(required = false) maxCount: Int?): String {
        service.addObject(tileId, objectId, required, minCount, maxCount)
        return "redirect:/admin/base-tiles/$tileId"
    }

    @PostMapping("/{tileId}/npcs")
    fun addNpc(@PathVariable tileId: Long, @RequestParam npcId: Long,
               @RequestParam(required = false) minCount: Int?,
               @RequestParam(required = false) maxCount: Int?): String {
        service.addNpc(tileId, npcId, minCount, maxCount)
        return "redirect:/admin/base-tiles/$tileId"
    }

    @PostMapping("/{tileId}/quests")
    fun addQuest(@PathVariable tileId: Long, @RequestParam questId: Long, @RequestParam type: String): String {
        service.addQuest(tileId, questId, type)
        return "redirect:/admin/base-tiles/$tileId"
    }

    @PostMapping("/{tileId}/objects/{linkId}/delete")
    fun deleteObject(@PathVariable tileId: Long, @PathVariable linkId: Long): String {
        service.removeObject(linkId)
        return "redirect:/admin/base-tiles/$tileId"
    }

    @PostMapping("/{tileId}/npcs/{linkId}/delete")
    fun deleteNpc(@PathVariable tileId: Long, @PathVariable linkId: Long): String {
        service.removeNpc(linkId)
        return "redirect:/admin/base-tiles/$tileId"
    }

    @PostMapping("/{tileId}/quests/{linkId}/delete")
    fun deleteQuest(@PathVariable tileId: Long, @PathVariable linkId: Long): String {
        service.removeQuest(linkId)
        return "redirect:/admin/base-tiles/$tileId"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/base-tiles"
    }

    private fun setupForm(model: Model) {
        model.addAttribute("grades", gradeService.list(null, null, 0, 100).content)
        model.addAttribute("stories", storyService.list(null, null, 0, 100).content)
        model.addAttribute("objectsList", objectService.list(null, null, 0, 100).content)
        model.addAttribute("npcsList", npcService.list(null, null, 0, 100).content)
        model.addAttribute("questsList", questService.list(null, null, 0, 100).content)
    }
}
