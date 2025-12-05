package com.example.gamemaster.controller

import com.example.gamemaster.entity.*
import com.example.gamemaster.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import jakarta.validation.Valid

@Controller
@RequestMapping("/admin")
class HomeController {
    @GetMapping
    fun index(): String = "layout/index"
}

@Controller
@RequestMapping("/admin/grades")
class GradeController(private val service: GradeService) {
    @GetMapping
    fun list(@RequestParam(required = false) type: String?, @RequestParam(required = false) name: String?, model: Model): String {
        model.addAttribute("grades", service.list(type, name))
        model.addAttribute("grade", Grade())
        return "grade/list"
    }

    @PostMapping
    fun create(@ModelAttribute @Valid grade: Grade, bindingResult: BindingResult, redirectAttributes: RedirectAttributes): String {
        return if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "입력값 오류")
            "redirect:/admin/grades"
        } else {
            service.save(grade)
            "redirect:/admin/grades"
        }
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        model.addAttribute("grade", service.get(id))
        return "grade/edit"
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long, @ModelAttribute @Valid grade: Grade, bindingResult: BindingResult): String {
        if (bindingResult.hasErrors()) return "grade/edit"
        grade.id = id
        service.save(grade)
        return "redirect:/admin/grades"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/grades"
    }
}

@Controller
@RequestMapping("/admin/stories")
class StoryController(private val service: StoryService) {
    @GetMapping
    fun list(@RequestParam(required = false) keyword: String?, model: Model): String {
        model.addAttribute("stories", service.list(keyword))
        model.addAttribute("story", Story())
        return "story/list"
    }

    @PostMapping
    fun create(@ModelAttribute story: Story): String {
        service.save(story)
        return "redirect:/admin/stories"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        model.addAttribute("story", service.get(id))
        return "story/edit"
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long, @ModelAttribute story: Story): String {
        story.id = id
        service.save(story)
        return "redirect:/admin/stories"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/stories"
    }
}

@Controller
@RequestMapping("/admin/quests")
class QuestController(private val service: QuestTemplateService) {
    @GetMapping
    fun list(@RequestParam(required = false) keyword: String?, model: Model): String {
        model.addAttribute("quests", service.list(keyword))
        model.addAttribute("quest", QuestTemplate())
        return "quest/list"
    }

    @PostMapping
    fun create(@ModelAttribute quest: QuestTemplate): String {
        service.save(quest)
        return "redirect:/admin/quests"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        model.addAttribute("quest", service.get(id))
        return "quest/edit"
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long, @ModelAttribute quest: QuestTemplate): String {
        quest.id = id
        service.save(quest)
        return "redirect:/admin/quests"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/quests"
    }
}

@Controller
@RequestMapping("/admin/npcs")
class NpcController(
    private val service: NpcTemplateService,
    private val storyService: StoryService,
    private val questService: QuestTemplateService
) {
    @GetMapping
    fun list(@RequestParam(required = false) keyword: String?, model: Model): String {
        model.addAttribute("npcs", service.list(keyword))
        model.addAttribute("npc", NpcTemplate())
        model.addAttribute("stories", storyService.list(null))
        return "npc/list"
    }

    @PostMapping
    fun create(@ModelAttribute npc: NpcTemplate): String {
        service.save(npc)
        return "redirect:/admin/npcs"
    }

    @GetMapping("/{id}")
    fun detail(@PathVariable id: Long, model: Model): String {
        model.addAttribute("npc", service.get(id))
        model.addAttribute("stories", storyService.list(null))
        model.addAttribute("quests", questService.list(null))
        return "npc/detail"
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long, @ModelAttribute npc: NpcTemplate): String {
        npc.id = id
        service.save(npc)
        return "redirect:/admin/npcs/$id"
    }

    @PostMapping("/{id}/quests")
    fun addQuest(@PathVariable id: Long, @RequestParam questId: Long, @RequestParam relationType: String): String {
        service.addQuest(id, questId, relationType)
        return "redirect:/admin/npcs/$id"
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

@Controller
@RequestMapping("/admin/objects")
class ObjectController(
    private val service: ObjectTemplateService,
    private val storyService: StoryService,
    private val questService: QuestTemplateService
) {
    @GetMapping
    fun list(@RequestParam(required = false) keyword: String?, model: Model): String {
        model.addAttribute("objects", service.list(keyword))
        model.addAttribute("object", ObjectTemplate())
        model.addAttribute("stories", storyService.list(null))
        return "object/list"
    }

    @PostMapping
    fun create(@ModelAttribute obj: ObjectTemplate): String {
        service.save(obj)
        return "redirect:/admin/objects"
    }

    @GetMapping("/{id}")
    fun detail(@PathVariable id: Long, model: Model): String {
        model.addAttribute("object", service.get(id))
        model.addAttribute("stories", storyService.list(null))
        model.addAttribute("quests", questService.list(null))
        return "object/detail"
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long, @ModelAttribute obj: ObjectTemplate): String {
        obj.id = id
        service.save(obj)
        return "redirect:/admin/objects/$id"
    }

    @PostMapping("/{id}/quests")
    fun addQuest(@PathVariable id: Long, @RequestParam questId: Long, @RequestParam relationType: String): String {
        service.addQuest(id, questId, relationType)
        return "redirect:/admin/objects/$id"
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

@Controller
@RequestMapping("/admin/base-tiles")
class BaseTileController(
    private val service: BaseTileTemplateService,
    private val gradeService: GradeService,
    private val storyService: StoryService,
    private val objectService: ObjectTemplateService,
    private val npcService: NpcTemplateService,
    private val questService: QuestTemplateService
) {
    @GetMapping
    fun list(@RequestParam(required = false) keyword: String?, model: Model): String {
        model.addAttribute("tiles", service.list(keyword))
        model.addAttribute("tile", BaseTileTemplate())
        model.addAttribute("grades", gradeService.list(null, null))
        model.addAttribute("stories", storyService.list(null))
        return "tile/list"
    }

    @PostMapping
    fun create(@ModelAttribute tile: BaseTileTemplate): String {
        service.save(tile)
        return "redirect:/admin/base-tiles"
    }

    @GetMapping("/{id}")
    fun detail(@PathVariable id: Long, model: Model): String {
        model.addAttribute("tile", service.get(id))
        model.addAttribute("grades", gradeService.list(null, null))
        model.addAttribute("stories", storyService.list(null))
        model.addAttribute("objects", objectService.list(null))
        model.addAttribute("npcs", npcService.list(null))
        model.addAttribute("quests", questService.list(null))
        return "tile/detail"
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long, @ModelAttribute tile: BaseTileTemplate): String {
        tile.id = id
        service.save(tile)
        return "redirect:/admin/base-tiles/$id"
    }

    @PostMapping("/{id}/objects")
    fun addObject(@PathVariable id: Long, @RequestParam objectId: Long, @RequestParam(required = false) required: Boolean?, @RequestParam(required = false) minCount: Int?, @RequestParam(required = false) maxCount: Int?): String {
        service.addObject(id, objectId, required ?: false, minCount, maxCount)
        return "redirect:/admin/base-tiles/$id"
    }

    @PostMapping("/{id}/npcs")
    fun addNpc(@PathVariable id: Long, @RequestParam npcId: Long, @RequestParam(required = false) minCount: Int?, @RequestParam(required = false) maxCount: Int?): String {
        service.addNpc(id, npcId, minCount, maxCount)
        return "redirect:/admin/base-tiles/$id"
    }

    @PostMapping("/{id}/quests")
    fun addQuest(@PathVariable id: Long, @RequestParam questId: Long, @RequestParam type: String): String {
        service.addQuest(id, questId, type)
        return "redirect:/admin/base-tiles/$id"
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
}

@Controller
@RequestMapping("/admin/scenarios")
class ScenarioController(
    private val service: ScenarioTemplateService,
    private val tileService: BaseTileTemplateService,
    private val questService: QuestTemplateService
) {
    @GetMapping
    fun list(@RequestParam(required = false) keyword: String?, model: Model): String {
        model.addAttribute("scenarios", service.list(keyword))
        model.addAttribute("scenario", ScenarioTemplate())
        return "scenario/list"
    }

    @PostMapping
    fun create(@ModelAttribute scenario: ScenarioTemplate): String {
        service.save(scenario)
        return "redirect:/admin/scenarios"
    }

    @GetMapping("/{id}")
    fun detail(@PathVariable id: Long, model: Model): String {
        model.addAttribute("scenario", service.get(id))
        model.addAttribute("tiles", tileService.list(null))
        model.addAttribute("quests", questService.list(null))
        return "scenario/detail"
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long, @ModelAttribute scenario: ScenarioTemplate): String {
        scenario.id = id
        service.save(scenario)
        return "redirect:/admin/scenarios/$id"
    }

    @PostMapping("/{id}/required-tiles")
    fun addRequiredTile(@PathVariable id: Long, @RequestParam tileId: Long, @RequestParam(required = false) orderIndex: Int?): String {
        service.addRequiredTile(id, tileId, orderIndex)
        return "redirect:/admin/scenarios/$id"
    }

    @PostMapping("/{id}/random-rules")
    fun addRandomRule(@PathVariable id: Long, @RequestParam(required = false) tileTypeFilter: String?, @RequestParam(required = false) gradeMin: Int?, @RequestParam(required = false) gradeMax: Int?, @RequestParam count: Int): String {
        service.addRandomRule(id, tileTypeFilter, gradeMin, gradeMax, count)
        return "redirect:/admin/scenarios/$id"
    }

    @PostMapping("/{id}/quests")
    fun addScenarioQuest(@PathVariable id: Long, @RequestParam questId: Long, @RequestParam type: String): String {
        service.addScenarioQuest(id, questId, type)
        return "redirect:/admin/scenarios/$id"
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
    fun deleteScenarioQuest(@PathVariable scenarioId: Long, @PathVariable linkId: Long): String {
        service.removeScenarioQuest(linkId)
        return "redirect:/admin/scenarios/$scenarioId"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/scenarios"
    }
}

@Controller
@RequestMapping("/admin/designs")
class DesignAssetController(private val service: DesignAssetService) {
    @GetMapping
    fun list(@RequestParam(required = false) keyword: String?, model: Model): String {
        model.addAttribute("assets", service.list(keyword))
        model.addAttribute("asset", DesignAsset())
        return "design/list"
    }

    @PostMapping
    fun create(@ModelAttribute asset: DesignAsset): String {
        service.save(asset)
        return "redirect:/admin/designs"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        model.addAttribute("asset", service.get(id))
        return "design/edit"
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long, @ModelAttribute asset: DesignAsset): String {
        asset.id = id
        service.save(asset)
        return "redirect:/admin/designs"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/designs"
    }
}
