package ru.amk.mars_photo.di

import dagger.Component
import ru.amk.core.di.CoreComponent
import ru.amk.mars_photo.ui.MarsPhotoFragment

@Component(
    dependencies = [CoreComponent::class],
    modules = [MarsPhotoModule::class]
)
interface MarsPhotoComponent {

    fun injectMarsPhotoFragment(marsPhotoFragment: MarsPhotoFragment)

    @Component.Builder
    interface MarsPhotoComponentBuilder {

        fun build(): MarsPhotoComponent

        fun coreComponent(coreComponent: CoreComponent): MarsPhotoComponentBuilder
    }
}