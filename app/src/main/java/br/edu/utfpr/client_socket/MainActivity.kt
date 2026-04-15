package br.edu.utfpr.client_socket

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.net.Socket
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private lateinit var rbData: RadioButton
    private lateinit var rbHora: RadioButton
    private lateinit var btEnviar: Button
    private lateinit var tvResultado: TextView

    private lateinit var clientSocket: Socket


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
        val ip = "10.0.2.2"
        val port = 12345
        var protocol = ""

        try {
            clientSocket = Socket(ip, port) // linha bloqueante
            // Conectado com server

            val outputStream =
                clientSocket.getOutputStream().bufferedWriter(Charset.forName("utf-8"))
            val inputStream = clientSocket.getInputStream().bufferedReader(Charset.forName("utf-8"))
            // Fluxo de IO criado

            when (rbHora.isChecked) {
                true -> protocol = "hora"
                false -> protocol = "data"
            }

            outputStream.write(protocol + "\n")
            outputStream.flush()
            // mensagem enviada ao servidor, sem bloqueios

            val result = inputStream.readLine() // linha bloqueante
            // mensagem recebida do servidor
            tvResultado.text = result
        } catch (e: Exception) {

        } finally {

        }

        Toast
            .makeText(this, protocol, Toast.LENGTH_SHORT)
            .show()
    }
}