package com.braiso_22.bichota_timer.tasks.di

import com.braiso_22.bichota_timer.tasks.data.TaskRepositoryMock
import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetCategorizedTasks
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetExecutionById
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetSegmentById
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetTaskById
import com.braiso_22.bichota_timer.tasks.domain.usecases.UpsertExecution
import com.braiso_22.bichota_timer.tasks.domain.usecases.UpsertTask
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetTasksWithAllExecutionsByUser
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetTasksWithExecutionsInDateRange
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetWorkedHoursOfTasks
import com.braiso_22.bichota_timer.tasks.domain.usecases.UpsertSegment
import com.braiso_22.bichota_timer.tasks.presentation.add_task.AddTaskViewModel
import com.braiso_22.bichota_timer.tasks.presentation.my_day.MyDayViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val taskModule = module {
    singleOf(::TaskRepositoryMock).bind<TaskRepository>()
    singleOf(::GetTasksWithAllExecutionsByUser).bind()
    singleOf(::GetTasksWithExecutionsInDateRange).bind()
    singleOf(::GetWorkedHoursOfTasks).bind()
    singleOf(::GetCategorizedTasks).bind()
    singleOf(::GetTaskById).bind()
    singleOf(::GetSegmentById).bind()
    singleOf(::GetExecutionById).bind()
    singleOf(::UpsertTask).bind()
    singleOf(::UpsertExecution).bind()
    singleOf(::UpsertSegment).bind()

    viewModelOf(::MyDayViewModel)
    viewModelOf(::AddTaskViewModel)
}