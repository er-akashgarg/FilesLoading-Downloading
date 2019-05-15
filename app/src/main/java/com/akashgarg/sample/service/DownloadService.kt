package com.akashgarg.sample.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.akashgarg.sample.BuildConfig
import com.akashgarg.sample.R
import com.akashgarg.sample.restclient.ApiCallInterface
import com.akashgarg.sample.utils.AppConstants
import com.akashgarg.sample.utils.Download
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * @author Akash Garg on 15-05-2019.
 */

class DownloadService : Service() {
    private var notificationBuilder: NotificationCompat.Builder? = null
    private var notificationManager: NotificationManager? = null
    private var totalFileSize: Int = 0
    private var fileDirectory: File? = null
    private var appName = "app_name"
    private val NOTIF_ID = 10
    private var fileType: String = ".png"
    private val TAG = DownloadService::class.java.canonicalName


    override fun onCreate() {
        super.onCreate()
        fileDirectory =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MindValley")
        if (!fileDirectory!!.exists()) {
            fileDirectory!!.mkdirs()
        }
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(TAG, ":--------onStartCommand-----: " + intent.getStringExtra(AppConstants.URL))
        if (null != intent.getStringExtra(AppConstants.URL)) {
            initNotification()
            initDownload(intent.getStringExtra(AppConstants.URL))
        }
        return Service.START_REDELIVER_INTENT
    }

    private fun initNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("id", "an", NotificationManager.IMPORTANCE_LOW)

            notificationChannel.description = "no sound"
            notificationChannel.setSound(null, null)
            notificationChannel.enableLights(false)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
            notificationManager!!.createNotificationChannel(notificationChannel)
        }

        notificationBuilder = NotificationCompat.Builder(this, "id")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentTitle("Download")
            .setContentText("Downloading File..")
            .setDefaults(0)
            .setOngoing(true)
            .setAutoCancel(false)

        notificationManager!!.notify(NOTIF_ID, notificationBuilder!!.build())
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun initDownload(url: String) {
        Log.e(TAG, ":URL to Download--: $url")

        if (url.contentEquals(".pdf")) {
            fileType = ".pdf"
        } else if (url.contentEquals(".png")
            || url.contentEquals(".jpeg")
            || url.contentEquals(".jpg")
        ) {
            fileType = ".png"
        }

        if (AppConstants.checkInternetConnection(applicationContext)) {
            val api = ApiCallInterface.create()
            api.downloadImageRX(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(object : Observer<ResponseBody> {
                    override fun onNext(responseBody: ResponseBody) {
                        try {
                            downloadFile(responseBody)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        stopSelf()
                    }

                    override fun onComplete() {
                        Log.e(TAG, "@akash---onComplete---- ")
                        stopSelf()
                    }
                })


        } else {
            Toast.makeText(this, "Network is not available for downloading taxi.apk file--", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "---------------Network is not available for downloading taxi.apk file---------- ")
            stopSelf()
        }

    }

    @Throws(IOException::class)
    private fun downloadFile(body: ResponseBody) {
        var count: Int
        val data = ByteArray(1024 * 4)
        val fileSize = body.contentLength()
        val bis = BufferedInputStream(body.byteStream(), 1024 * 8)
        val outputFolder =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MindValley")

        Log.e(
            TAG,
            "@akash---System.currentTimeMillis() + fileType---- " + System.currentTimeMillis() + " : " + fileType
        )
        val imgFile = File(outputFolder.absolutePath + "/" + System.currentTimeMillis() + fileType)
        val output = FileOutputStream(imgFile)
        var total: Long = 0
        val startTime = System.currentTimeMillis()
        var timeCount = 1
        val download = Download()

        while (true) {
            count = bis.read(data)
            if (count == -1)
                break

            total += count.toLong()
            totalFileSize = (fileSize / Math.pow(1024.0, 2.0)).toInt()
            val current = Math.round(total / Math.pow(1024.0, 2.0)).toDouble()

            val progress = (total * 100 / fileSize).toInt()

            val currentTime = System.currentTimeMillis() - startTime

            download.totalFileSize = totalFileSize

            if (currentTime > 1000 * timeCount) {
                download.currentFileSize = current.toInt()
                download.progress = progress
                sendNotification(download)
                timeCount++
            }
            output.write(data, 0, count)
        }
        onDownloadComplete(imgFile)
        output.flush()
        output.close()
        bis.close()
        stopSelf()
    }

    @SuppressLint("DefaultLocale")
    private fun sendNotification(download: Download) {
        Log.e(TAG, "-###download Progress---" + download.progress + "/n Size: " + download.currentFileSize)
        notificationBuilder!!.setProgress(100, download.progress, false)
        notificationBuilder!!.setContentText(
            String.format(
                "Downloaded (%d/%d) MB",
                download.currentFileSize,
                download.totalFileSize
            )
        )
        notificationManager!!.notify(NOTIF_ID, notificationBuilder!!.build())
    }


    private fun onDownloadComplete(s: File) {
        Log.e(TAG, "@akash------------------onDownloadComplete------------File Path: " + s.absolutePath)
        val download = Download()
        download.progress = 100
        notificationManager!!.cancel(0)
        notificationBuilder!!.setProgress(0, 100, false)

        var shareType = "image/png"
        if (s.absolutePath.contains(".pdf"))
            shareType = "application/pdf"

        val intent = getShareGlobal(this, s.absolutePath, shareType, appName)

        val contentIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        notificationBuilder = NotificationCompat.Builder(this, "id")
        notificationBuilder!!.setContentTitle("Downloaded File")
        notificationBuilder!!.setContentText("File Download Complete")
            .setWhen(System.currentTimeMillis())
            .setContentIntent(contentIntent)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setAutoCancel(true)
            .setOngoing(false)
            .setSmallIcon(R.drawable.ic_file_download)
            .build()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager!!.notify(1, notificationBuilder!!.build())
    }


    override fun onTaskRemoved(rootIntent: Intent) {
        Log.e(TAG, "------------------onTaskRemoved------------" + rootIntent.getStringExtra("url"))
        notificationManager?.cancel(NOTIF_ID)
    }

    /*Share file globally for any SDK version*/
    private fun getShareGlobal(context: Context, path: String, shareType: String, title: String): Intent {
        val file = File(path)
        val photoUri: Uri
        if (Build.VERSION.SDK_INT >= 24)
            photoUri = FileProvider.getUriForFile(
                context.applicationContext,
                BuildConfig.APPLICATION_ID + ".fileProvider",
                file
            )
        else
            photoUri = Uri.fromFile(file)

        val sharingIntent = Intent(Intent.ACTION_VIEW)
        sharingIntent.setDataAndType(photoUri, shareType)
        sharingIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return sharingIntent
    }

    private fun getMimeType(url: String): String? {
        val parts = url.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val extension = parts[parts.size - 1]
        var type: String? = null
        val mime = MimeTypeMap.getSingleton()
        type = mime.getMimeTypeFromExtension(extension)
        return type
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "downloading completed")
        notificationManager?.cancel(NOTIF_ID)
        stopSelf()
    }
}
