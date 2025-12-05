package com.example.gamemasteradmin.controller

import com.example.gamemasteradmin.entity.Grade
import com.example.gamemasteradmin.service.GradeService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admin/grades")
class GradeController(private val service: GradeService) {
    @GetMapping
    fun list(@RequestParam(required = false) type: String?,
             @RequestParam(required = false) name: String?,
             @RequestParam(defaultValue = "0") page: Int,
             model: Model): String {
        model.addAttribute("grades", service.list(type, name, page, 20))
        model.addAttribute("searchType", type)
        model.addAttribute("searchName", name)
        return "grades/list"
    }

    @GetMapping("/new")
    fun createForm(model: Model): String {
        model.addAttribute("grade", Grade(type = "", level = 1, name = ""))
        return "grades/form"
    }

    @GetMapping("/{id}")
    fun edit(@PathVariable id: Long, model: Model): String {
        val grade = service.find(id).orElseThrow()
        model.addAttribute("grade", grade)
        return "grades/form"
    }

    @PostMapping
    fun save(@Valid grade: Grade, result: BindingResult, model: Model): String {
        if (result.hasErrors()) {
            return "grades/form"
        }
        return try {
            service.save(grade)
            "redirect:/admin/grades"
        } catch (ex: IllegalArgumentException) {
            result.rejectValue("type", "duplicate", ex.message ?: "중복")
            "grades/form"
        }
    }

    @PostMapping("/{id}/delete")
    fun delete(@PathVariable id: Long): String {
        service.delete(id)
        return "redirect:/admin/grades"
    }
}
