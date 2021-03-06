package com.shpakovskiy.cambot.di

import android.content.Context
import androidx.work.WorkManager
import com.shpakovskiy.cambot.bluetooth.BluetoothConnector
import com.shpakovskiy.cambot.common.SOCKET_SERVER_PORT
import com.shpakovskiy.cambot.common.WEB_SERVER_PORT
import com.shpakovskiy.cambot.data.LocalWebServer
import com.shpakovskiy.cambot.data.LocalWebSocketServer
import com.shpakovskiy.cambot.util.getDeviceIpAddress
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.net.InetSocketAddress
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideEmbeddedServer(@ApplicationContext context: Context): LocalWebServer {
        return LocalWebServer(assetManager = context.assets)
    }

    @Provides
    @Singleton
    fun provideWebSocketServer(@ApplicationContext context: Context): LocalWebSocketServer {
        val server = LocalWebSocketServer(
            InetSocketAddress(getDeviceIpAddress(context), SOCKET_SERVER_PORT)
        )
        server.isReuseAddr = true
        return server
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideBluetoothConnector(): BluetoothConnector {
        return BluetoothConnector()
    }
}