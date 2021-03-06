package com.tinashe.weather

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.tinashe.weather.injection.DaggerWeatherAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by tinashe on 2018/03/20.
 */
class WeatherApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    object AppInjector {

        fun init(app: WeatherApp) {

            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }

            DaggerWeatherAppComponent.builder()
                    .application(app)
                    .build()
                    .inject(app)
        }
    }
}