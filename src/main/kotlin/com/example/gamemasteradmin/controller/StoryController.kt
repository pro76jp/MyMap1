package com.example.gamemasteradmin.controller

import com.example.gamemasteradmin.entity.Story
import com.example.gamemasteradmin.service.StoryService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/stories")
class StoryController(private val service: StoryService) {
    @GetMapping
    fun list(@RequestParam(required = false) title: String?,
             @RequestParam(required = false) type: String?,
             @RequestParam(defaultValue = "0") page: Int,
             model: Model): String {
        model.addAttribute("stories", service.list(title, type, page, 20))
        model.addAttribute("searchTitle", title)
        model.addAttribute("searchType", type)
        return "stories/list"
    }

    @GetMapping("/new")
    fun createForm(model: Model): String {
        model.addAttribute("story", Story(title = "", body = "", type = ""))
        return "stories/form"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val story = service.find(id).orElseThrow()
        model.addAttribute("story", story)
        return "stories/form"
    }

    @PostMapping
    fun save(@Valid story: Story, result: BindingResult): String {
        if (result.hasErrors()) return "stories/form"
        service.save(story)
        return "redirect:/admin/stories"
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/stories"
    }
}
