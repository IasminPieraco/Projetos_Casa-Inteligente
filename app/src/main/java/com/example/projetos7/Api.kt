package com.example.projetos7


import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class Api(){
    val client = OkHttpClient()
    val url = "http://192.168.141.232:3000"
    companion object {
        val MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8".toMediaType()
        val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    }

    fun ligarLed(idLed:String, state: Boolean){
        val json = """
                    {
                    }
                    """.trimIndent()

        val request = Request.Builder()
            .url("${url}/switch/${idLed}/${if (state)"turn_on" else "turn_off"}")
            .post(json.toRequestBody(MEDIA_TYPE_JSON))
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                println("OK:"+response.body!!.string())
            }
        }catch (e:Exception){
            println("Erro:"+e.message)
        }



    }


    fun estadoLed(idLed: String): Boolean {
        var value = false
        val request = Request.Builder()
            .url("${url}/switch/${idLed}")
            .get()
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val jsonResponse = response.body?.string() // Obtém o corpo da resposta como uma String
                jsonResponse?.let { // Verifica se o corpo da resposta não é nulo
                    val jsonObject = JSONObject(it) // Converte a String JSON para um JSONObject

                    val id = jsonObject.getString("id")
                    value = jsonObject.getBoolean("value")
                    val state = jsonObject.getString("state")

                    // Faça o que quiser com as informações obtidas
                    println("ID: $id, Value: $value, State: $state")

                }
            }
        }catch (e:Exception){
            println("Erro:"+e.message)
        }

        return value
    }

    fun ledRGB(ligar:Boolean, r:Float,g:Float,b:Float,brilho:Float){
        val json = """
                    {
                    }
                    """.trimIndent()
        val request = Request.Builder()
            .url("${url}/light/rgb_light/turn_on?r=$r&g=$g&b=$b&brightness=$brilho")
            .post(json.toRequestBody(MEDIA_TYPE_JSON))
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                println("Funcionou:"+response.body!!.string())
            }
        }catch (e:Exception){
            println("Erro:"+e.message)
        }


    }

    fun getComponente(): Componente?{
        var value = false
        val request = Request.Builder()
            .url("${url}/componentes/mobile")
            .get()
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val jsonResponse = response.body?.string() // Obtém o corpo da resposta como uma String
                jsonResponse?.let { // Verifica se o corpo da resposta não é nulo
                    println(it+"vdfvfd")
                    val componente = Gson().fromJson(it, Componente::class.java)

                    return componente // Retorna o objeto Componente
                }
            }
        }catch (e:Exception){
            println("Erro:"+e.message)
        }
        return  null
    }

    fun setComponente(componente: Componente):Boolean{
//        val novoComponente = componente
//        novoComponente.led_cozinha = !novoComponente.led_cozinha
//        novoComponente.led_banheiro = !novoComponente.led_banheiro
//        novoComponente.led_sala = !novoComponente.led_sala
//        novoComponente.led_quarto = !novoComponente.led_quarto
        // Converte o objeto Componente em JSON
        val gson = Gson()
        val jsonComponente = gson.toJson(componente)

        // Define o tipo de conteúdo como JSON
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()

        // Cria o body da requisição POST
        val requestBody = jsonComponente.toRequestBody(mediaType)

        // Constrói a requisição POST
        val request = Request.Builder()
            .url("$url/componentes")
            .post(requestBody)
            .build()
        Log.i("Teste", "chamou a API")
        // Tenta enviar a requisição
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                Log.i("Teste", "deu certo")
                true  // Se a requisição for bem-sucedida, retorna true
            }
        } catch (e: Exception) {
            println("Erro ao enviar POST: ${e.message}")
            Log.i("Teste", " Esplique : ${e}, ${e.message}")
            false  // Em caso de falha, retorna false
        }
    }
}