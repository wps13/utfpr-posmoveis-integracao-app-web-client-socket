package br.edu.utfpr.client_socket

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import java.io.BufferedReader
import java.io.BufferedWriter
import java.net.Socket
import java.nio.charset.Charset

class MyReceiver : BroadcastReceiver() {

    private lateinit var inputStream: BufferedReader
    private lateinit var outputStream: BufferedWriter
    private lateinit var clientSocket: Socket


    override fun onReceive(context: Context, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)

        if (level < 10) {
            Thread {
                conexaoTask("hora")
            }.start()
        }
    }

    fun conexaoTask(protocol: String) {
        var result = ""
        try {
            if (!::clientSocket.isInitialized) {
                val ip = BuildConfig.SERVER_IP
                val port = BuildConfig.SERVER_PORT

                clientSocket = Socket(ip, port) //linha é bloqueante ou dará exceção
                //Conectado com o Server

                outputStream =
                    clientSocket.getOutputStream().bufferedWriter(Charset.forName("utf-8"))
                inputStream =
                    clientSocket.getInputStream().bufferedReader(Charset.forName("utf-8"))
                //Fluxo de IO Criado
            }


            outputStream.write(protocol + "\n")
            outputStream.flush()
            //Mensagem enviada ao servidor, sem bloqueios

            result = inputStream.readLine() //linha bloqueante
            //mensagem recebida do servidor
        } catch (e: Exception) {
            result = e.message.toString()
        }
    }

}