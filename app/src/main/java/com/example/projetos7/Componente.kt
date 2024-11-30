package com.example.projetos7

import java.util.Date

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
    var created_at: Date = Date() // Valor padrão para created_at (data atual)
) {
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
        Date()
    )
}