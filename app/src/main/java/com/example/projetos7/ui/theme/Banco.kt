package com.example.projetos7.ui.theme

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class Banco {

        // Configurações de conexão ao banco
        private val url = "jdbc:postgresql://localhost:5432/projeto8"  // URL do banco de dados
        private val user = "postgres"  // Usuário do banco de dados
        private val password = "123456789"  // Senha do banco de dados

        // Função para obter conexão com o banco de dados
        private fun connect(): Connection? {
            return try {
                DriverManager.getConnection(url, user, password)
            } catch (e: SQLException) {
                e.printStackTrace()
                null
            }
        }

        // Função de SELECT
        fun selectData(): List<Map<String, Any>> {
            val resultList = mutableListOf<Map<String, Any>>()
            val query = "SELECT id, name FROM componente"

            val connection = connect()
            connection?.use {
                val statement = it.createStatement()
                val resultSet: ResultSet = statement.executeQuery(query)

                while (resultSet.next()) {
                    val row = mapOf(
                        "id" to resultSet.getInt("id"),
                        "sensor_Cozinha" to resultSet.getString("sensor_Cozinha"),
                        "sensor_Sala" to resultSet.getString("sensor_Sala"),
                        "sensor_Garagem" to resultSet.getString("sensor_Garagem"),
                        "garagem_open" to resultSet.getString("garagem_open"),
                        "quarto_open" to resultSet.getString("quarto_open"),
                        "banheiro_open" to resultSet.getString("banheiro_open"),
                        "led_sala" to resultSet.getString("led_sala"),
                        "led_cozinha" to resultSet.getString("led_cozinha"),
                        "led_quarto" to resultSet.getString("led_quarto"),
                        "led_banheiro" to resultSet.getString("led_banheiro"),
                        "created_at" to resultSet.getString("created_at"),
                        "temp_sensorQuarto" to resultSet.getString("temp_sensorQuarto"),
                        "temp_sensorBanheiro" to resultSet.getString("temp_sensorBanheiro"),
                        "humidity_sensorQuarto" to resultSet.getString("humidity_sensorQuarto"),
                        "humidity_sensorBanheiro" to resultSet.getString("humidity_sensorBanheiro")

                    )
                    resultList.add(row)
                }
            }

            return resultList
        }

        // Função de INSERT
        fun insertData(id: Int, name: String): Boolean {
            val query = "INSERT INTO your_table (id, name) VALUES (?, ?)"

            val connection = connect()
            return connection?.use {
                try {
                    val preparedStatement = it.prepareStatement(query)
                    preparedStatement.setInt(1, id)
                    preparedStatement.setString(2, name)
                    preparedStatement.executeUpdate() > 0
                } catch (e: SQLException) {
                    e.printStackTrace()
                    false
                }
            } ?: false
        }
    }
