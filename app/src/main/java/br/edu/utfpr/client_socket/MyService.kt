package br.edu.utfpr.client_socket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.io.BufferedReader
import java.io.BufferedWriter
import java.net.Socket
import java.util.Timer
import java.util.TimerTask

class MyService : Service() {
    private lateinit var inputStream: BufferedReader
    private lateinit var outputStream: BufferedWriter
    private lateinit var clientSocket: Socket


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    @Deprecated("Deprecated in Java")
    override fun onStart(intent: Intent?, startId: Int): Int {
        val timer = Timer()

        timer.schedule(MinhaTimerTask("hora"), 0, 1000)

        return START_NOT_STICKY

    }


    inner class MinhaTimerTask(msg: String) : TimerTask() {
        override fun run() {
        }
    }
}