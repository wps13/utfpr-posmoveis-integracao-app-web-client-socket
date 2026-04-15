package br.edu.utfpr.client_socket

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.net.Socket
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private lateinit var rbData: RadioButton
    private lateinit var rbHora: RadioButton
    private lateinit var btEnviar: Button
    private lateinit var tvResultado: TextView

    private lateinit var clientSocket: Socket
    private lateinit var inputStream: BufferedReader
    private lateinit var outputStream: BufferedWriter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rbData = findViewById(R.id.rbData)
        rbHora = findViewById(R.id.rbHora)
        btEnviar = findViewById(R.id.btEnviar)
        tvResultado = findViewById(R.id.tvResultado)

    }

    fun btEnviarOnClick(view: View) {
        Thread {
            val ip = "10.0.2.2"
            val port = 12345

            try {
                if (!::clientSocket.isInitialized) {
                    clientSocket = Socket(ip, port) // linha bloqueante
                    // Conectado com server
                    outputStream =
                        clientSocket.getOutputStream().bufferedWriter(Charset.forName("utf-8"))
                    inputStream =
                        clientSocket.getInputStream().bufferedReader(Charset.forName("utf-8"))
                    // Fluxo de IO criado
                }

                val protocol = when (rbHora.isChecked) {
                    true -> "hora"
                    false -> "data"
                }

                outputStream.write(protocol + "\n")
                outputStream.flush()
                // mensagem enviada ao servidor, sem bloqueios

                val result = inputStream.readLine() // linha bloqueante
                // mensagem recebida do servidor

                runOnUiThread {
                    tvResultado.text = result
                }

            } catch (e: Exception) {
                runOnUiThread {
                    tvResultado.text = "Erro: " + e.message
                }
            } finally {
            }
        }.start()
    }

    override fun onStop() {
        super.onStop()
        clientSocket.close()
    }
}