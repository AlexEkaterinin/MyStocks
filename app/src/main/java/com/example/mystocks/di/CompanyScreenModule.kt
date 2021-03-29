package com.example.mystocks.di

import android.app.Activity
import com.example.mystocks.company_profile.CompanyProfileActivity
import com.example.mystocks.company_profile.CompanyProfilePresenter
import com.example.mystocks.company_profile.CompanyProfileScreenContract
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class CompanyScreenModule {

    @Binds
    abstract fun bindCompanyProfileActivity(impl: CompanyProfileActivity): CompanyProfileScreenContract.View

    @Binds
    abstract fun bindCompanyProfilePresenter(impl: CompanyProfilePresenter): CompanyProfileScreenContract.Presenter
}

@InstallIn(ActivityComponent::class)
@Module
object CompanyProfileActivityModule {

    @Provides
    fun provideCompanyProfileActivity(activity: Activity): CompanyProfileActivity {
        return activity as CompanyProfileActivity
    }
}