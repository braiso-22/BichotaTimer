package com.braiso_22.bichota_timer.tasks.domain.entities

import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.datetime.LocalDateTime

data class Task(
    val id: String = "",
    val name: String = "",
    val isWorkRelated: Boolean = true,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val ticketId: Int? = null,
    val isCompleted: Boolean = false,
    val executions: List<Execution> = emptyList(),
    val userId: String,
)
