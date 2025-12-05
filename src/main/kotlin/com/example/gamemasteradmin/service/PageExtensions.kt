package com.example.gamemasteradmin.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

fun <T> Page<T>.filterPage(predicate: (T) -> Boolean): Page<T> {
    val content = this.content.filter(predicate)
    return PageImpl(content, this.pageable, content.size.toLong())
}
