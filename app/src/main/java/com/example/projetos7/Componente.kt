package com.example.projetos7

import android.util.Log
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import java.util.Date
import kotlin.reflect.KMutableProperty1

data class Componente(
    var id: String = "", // Valor padrão para id
    var temp_sensorQuarto: Double = 0.0, // Valor padrão para temp_sensorQuarto
    var temp_sensorBanheiro: Double = 0.0, // Valor padrão para temp_sensorBanheiro
    var humidity_sensorQuarto: Double = 0.0, // Valor padrão para humidity_sensorQuarto
    var humidity_sensorBanheiro: Double = 0.0, // Valor padrão para humidity_sensorBanheiro
    var sensor_Cozinha: Boolean = false, // Valor padrão para sensor_Cozinha
    var sensor_Sala: Boolean = false, // Valor padrão para sensor_Sala
    var sensor_Garagem: Boolean = false, // Valor padrão para sensor_Garagem
    var garagem_open: Boolean = false, // Valor padrão para garagem_open
    var quarto_open: Boolean = false, // Valor padrão para quarto_open
    var banheiro_open: Boolean = false, // Valor padrão para banheiro_open
    var led_sala: Boolean = false, // Valor padrão para led_sala
    var led_cozinha: Boolean = false, // Valor padrão para led_cozinha
    var led_quarto: Boolean = false, // Valor padrão para led_quarto
    var led_banheiro: Boolean = false, // Valor padrão para led_banheiro
    var auto_Quarto: Boolean = false,
    var auto_Banheiro: Boolean = false,
    var auto_Sala: Boolean = false,
    var auto_Garagem: Boolean = false,
    var irrigacao_jardim: Boolean = false,
    var created_at: Date = Date() // Valor padrão para created_at (data atual)
): Serializable {
    constructor() : this(
        "",
        0.0,
        0.0,
        0.0,
        0.0,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        Date()
    )
    fun copyWith(
        id: String? = null,
        temp_sensorQuarto: Double? = null,
        temp_sensorBanheiro: Double? = null,
        humidity_sensorQuarto: Double? = null,
        humidity_sensorBanheiro: Double? = null,
        sensor_Cozinha: Boolean? = null,
        sensor_Sala: Boolean? = null,
        sensor_Garagem: Boolean? = null,
        garagem_open: Boolean? = null,
        quarto_open: Boolean? = null,
        banheiro_open: Boolean? = null,
        led_sala: Boolean? = null,
        led_cozinha: Boolean? = null,
        led_quarto: Boolean? = null,
        led_banheiro: Boolean? = null,
        auto_Quarto: Boolean? = null,
        auto_Banheiro: Boolean? = null,
        auto_Sala: Boolean? = null,
        auto_Garagem: Boolean? = null,
        irrigacao_jardim: Boolean? = null,
        created_at: Date? = null
    ): Componente {
        return Componente(
            id = id ?: this.id,
            temp_sensorQuarto = temp_sensorQuarto ?: this.temp_sensorQuarto,
            temp_sensorBanheiro = temp_sensorBanheiro ?: this.temp_sensorBanheiro,
            humidity_sensorQuarto = humidity_sensorQuarto ?: this.humidity_sensorQuarto,
            humidity_sensorBanheiro = humidity_sensorBanheiro ?: this.humidity_sensorBanheiro,
            sensor_Cozinha = sensor_Cozinha ?: this.sensor_Cozinha,
            sensor_Sala = sensor_Sala ?: this.sensor_Sala,
            sensor_Garagem = sensor_Garagem ?: this.sensor_Garagem,
            garagem_open = garagem_open ?: this.garagem_open,
            quarto_open = quarto_open ?: this.quarto_open,
            banheiro_open = banheiro_open ?: this.banheiro_open,
            led_sala = led_sala ?: this.led_sala,
            led_cozinha = led_cozinha ?: this.led_cozinha,
            led_quarto = led_quarto ?: this.led_quarto,
            led_banheiro = led_banheiro ?: this.led_banheiro,
            auto_Quarto = auto_Quarto ?: this.auto_Quarto,
            auto_Banheiro = auto_Banheiro ?: this.auto_Banheiro,
            auto_Sala = auto_Sala ?: this.auto_Sala,
            auto_Garagem = auto_Garagem ?: this.auto_Garagem,
            irrigacao_jardim = irrigacao_jardim ?: this.irrigacao_jardim,
            created_at = created_at ?: this.created_at
        )
    }

}


class ComponenteViewModel : ViewModel() {

    private val _componentes = MutableStateFlow(Componente())
    val componente: StateFlow<Componente> = _componentes

    private val api = Api()

    private suspend fun sendDataBlocking() {
        withContext(Dispatchers.IO) {
            try {
                api.setComponente(_componentes.value)
            } catch (e: Exception) {
                Log.e("ViewModel", "Erro ao enviar componente", e)
            }
        }
    }

    private suspend fun fetchDataBlocking() {
        withContext(Dispatchers.IO) {
            try {
                val result = api.getComponente()
                result?.let {
                    _componentes.value = it
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Erro ao buscar componente", e)
            }
        }
    }


    fun setComponente(componente: Componente) {
        viewModelScope.launch {
            _componentes.value = componente
            Log.i("teste", componente.toString()
            )

            withContext(Dispatchers.IO){
                api.setComponente(componente)

            }
            withContext(Dispatchers.IO){
                val result = api.getComponente()
                result?.let {
                    _componentes.value = it
                }
            }





//            sendDataBlocking()
//            fetchDataBlocking()
        }
    }
}
