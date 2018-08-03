package com.welove520.timelineupload.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * Created by Raomengyang on 18-8-3.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */
class UploadService : Service() {
    override fun onBind(intent: Intent?): IBinder {
        startUpload()
        return UploadBinder()
    }

    private fun startUpload() {

    }

    class UploadBinder : Binder() {
        fun getService(): Binder {
            return this@UploadBinder
        }

    }
}