package com.braiso_22.bichota_timer.di

import com.braiso_22.bichota_timer.tasks.di.taskModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(taskModule)
    }
}