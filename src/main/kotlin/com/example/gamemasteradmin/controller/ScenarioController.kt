package com.example.gamemasteradmin.controller

import com.example.gamemasteradmin.entity.ScenarioTemplate
import com.example.gamemasteradmin.service.*
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/scenarios")
class ScenarioController(
    private val service: ScenarioService,
    private val baseTileService: BaseTileService,
    private val questService: QuestService
) {
    @GetMapping
    fun list(@RequestParam(required = false) name: String?,
             @RequestParam(defaultValue = "0") page: Int,
             model: Model): String {
        model.addAttribute("scenarios", service.list(name, page, 20))
        model.addAttribute("searchName", name)
        return "scenarios/list"
    }

    @GetMapping("/new")
    fun createForm(model: Model): String {
        setup(model)
        model.addAttribute("scenarioTemplate", ScenarioTemplate(name = "", description = ""))
        return "scenarios/form"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        setup(model)
        val scenario = service.find(id).orElseThrow()
        model.addAttribute("scenarioTemplate", scenario)
        return "scenarios/form"
    }

    @PostMapping
    fun save(@Valid scenarioTemplate: ScenarioTemplate, result: BindingResult, model: Model): String {
        if (result.hasErrors()) {
            setup(model)
            return "scenarios/form"
        }
        service.save(scenarioTemplate)
        return "redirect:/admin/scenarios"
    }

    @PostMapping("/{scenarioId}/required-tiles")
    fun addRequiredTile(
        @PathVariable scenarioId: Long,
        @RequestParam baseTileId: Long,
        @RequestParam(required = false) orderIndex: Int?
    ): String {
        service.addRequiredTile(scenarioId, baseTileId, orderIndex)
        return "redirect:/admin/scenarios/$scenarioId"
    }

    @PostMapping("/{scenarioId}/random-rules")
    fun addRandomRule(
        @PathVariable scenarioId: Long,
        @RequestParam(required = false) tileTypeFilter: String?,
        @RequestParam(required = false) gradeMin: Int?,
        @RequestParam(required = false) gradeMax: Int?,
        @RequestParam count: Int
    ): String {
        service.addRandomRule(scenarioId, tileTypeFilter, gradeMin, gradeMax, count)
        return "redirect:/admin/scenarios/$scenarioId"
    }

    @PostMapping("/{scenarioId}/quests")
    fun addQuest(@PathVariable scenarioId: Long, @RequestParam questId: Long, @RequestParam type: String): String {
        service.addQuest(scenarioId, questId, type)
        return "redirect:/admin/scenarios/$scenarioId"
    }

    @PostMapping("/{scenarioId}/required-tiles/{linkId}/delete")
    fun deleteRequiredTile(@PathVariable scenarioId: Long, @PathVariable linkId: Long): String {
        service.removeRequiredTile(linkId)
        return "redirect:/admin/scenarios/$scenarioId"
    }

    @PostMapping("/{scenarioId}/random-rules/{linkId}/delete")
    fun deleteRandomRule(@PathVariable scenarioId: Long, @PathVariable linkId: Long): String {
        service.removeRandomRule(linkId)
        return "redirect:/admin/scenarios/$scenarioId"
    }

    @PostMapping("/{scenarioId}/quests/{linkId}/delete")
    fun deleteQuest(@PathVariable scenarioId: Long, @PathVariable linkId: Long): String {
        service.removeQuest(linkId)
        return "redirect:/admin/scenarios/$scenarioId"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/scenarios"
    }

    private fun setup(model: Model) {
        model.addAttribute("baseTiles", baseTileService.list(null, null, 0, 200).content)
        model.addAttribute("quests", questService.list(null, null, 0, 200).content)
    }
}
