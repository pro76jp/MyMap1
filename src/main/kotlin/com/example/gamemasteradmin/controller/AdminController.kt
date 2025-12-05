package com.example.gamemasteradmin.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AdminController {
    @GetMapping("/")
    fun index(): String = "redirect:/admin/grades"
}
