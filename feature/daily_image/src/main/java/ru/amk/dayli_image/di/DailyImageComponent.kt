package ru.amk.dayli_image.di

import dagger.Component
import ru.amk.core.di.CoreComponent
import ru.amk.dayli_image.ui.DailyImageFragment

@Component(
    dependencies = [CoreComponent::class],
    modules = [DailyImageModule::class]
)
interface DailyImageComponent {

    fun injectDailyImageFragment(dailyImageFragment: DailyImageFragment)

    @Component.Builder
    interface DailyImageComponentBuilder {

        fun build(): DailyImageComponent

        fun coreComponent(coreComponent: CoreComponent): DailyImageComponentBuilder
    }
}