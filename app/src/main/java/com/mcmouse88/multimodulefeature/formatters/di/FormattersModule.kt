package com.mcmouse88.multimodulefeature.formatters.di

import com.mcmouse88.multimodulefeature.formatters.DataTimeFormatter
import com.mcmouse88.multimodulefeature.formatters.DefaultDataTimeFormatter
import com.mcmouse88.multimodulefeature.formatters.DefaultPriceFormatter
import com.mcmouse88.multimodulefeature.formatters.PriceFormatter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface FormattersModule {

    @Binds
    fun bindDateTimeFormatter(
        formatter: DefaultDataTimeFormatter
    ): DataTimeFormatter

    @Binds
    fun bindPriceFormatter(
        formatter: DefaultPriceFormatter
    ): PriceFormatter
}