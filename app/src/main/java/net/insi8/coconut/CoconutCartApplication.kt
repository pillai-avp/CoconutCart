package net.insi8.coconut

import android.app.Application
import net.insi8.coconut.api.modules.apiModule
import net.insi8.coconut.ui.list.groceriesCartModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class CoconutCartApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        configureTimber()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@CoconutCartApplication)
            modules(
                apiModule,
                groceriesCartModule
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    private fun configureTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // handle some kind of  online logging if necessary.
        }
    }
}