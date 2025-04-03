package com.example.projetos7

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetos7.ui.theme.Projetos7Theme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

fun checkAudioPermission(context: Context) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
    }
}

class MainActivity : ComponentActivity() {

    private lateinit var speechHelper: SpeechHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projetos7Theme {
                Scaffold {
                    CentroPrincipal(it)
                }
            }
        }
        checkAudioPermission(this)
        speechHelper = SpeechHelper(this) { spokenText ->
            Toast.makeText(this, "Você disse: $spokenText", Toast.LENGTH_LONG).show()
            // Aqui você pode executar ações com base no texto reconhecido
        }

    }
    override fun onDestroy() {
        speechHelper.destroy()
        super.onDestroy()
    }
}
@Composable
fun CentroPrincipal(paddingValues: PaddingValues) {
    val contexto = LocalContext.current
    checkAudioPermission(LocalContext.current)
    var speechHelper = SpeechHelper(contexto as Activity) { spokenText ->
        Toast.makeText(contexto, "Você disse: $spokenText", Toast.LENGTH_LONG).show()
        // Aqui você pode executar ações com base no texto reconhecido
    }
    val gradientColorList = listOf(
        Color(0xFF071D33),
        Color(0xFF135192),
        Color(0xFF042C53),
    )

    var showDialog = remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(gradientColorList)
            )
            .padding(paddingValues)
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier.fillMaxHeight().padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Casa Automatizada",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White

            )

            Spacer(modifier = Modifier.height(140.dp))

            CustomButton(
                text = "Lâmpada",
                iconResource = R.drawable.lampprinci,
                onClick = {  val intent = Intent(contexto, MainActivity3::class.java)
                    contexto.startActivity(intent) }
            )
            CustomButton(
                text = "Temperatura",
                iconResource = R.drawable.temp,
                onClick = { val intent = Intent(contexto, Temperatura::class.java)
                    contexto.startActivity(intent) }
            )
            CustomButton(
                text = "Presença",
                iconResource = R.drawable.sensorprox,
                onClick = { val intent = Intent(contexto, PresencaSensor::class.java)
                    contexto.startActivity(intent) }
            )
            CustomButton(
                text = "Falar",
                iconResource = R.drawable.sensorprox,
                onClick = {speechHelper.startListening()}
            )

            Spacer(modifier = Modifier.weight(2f))

            SmallButton(
                text = "Sobre",
                onClick = { showDialog.value = true }
            )

            if (showDialog.value) {
                SobreDialog(
                    onDismiss = { showDialog.value = false }
                )
            }
        }
    }
}

@Composable
fun SobreDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Sobre o Projeto",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Este é um projeto de automação residencial, permitindo o controle de lâmpadas, temperatura e portão por meio de um aplicativo. O objetivo é tornar as residências mais seguras e eficientes.",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                IntegranteRow(
                    nome = "Bruno Hiago Xavier",
                    curso = "Engenharia de Computação",
                    imagem = R.drawable.bruno
                )

                IntegranteRow(
                    nome = "Iasmin Pieraço Rodrigues",
                    curso = "Engenharia de Computação",
                    imagem = R.drawable.iasmin
                )

                IntegranteRow(
                    nome = "José Ferreira Arantes Lopes",
                    curso = "Engenharia de Computação",
                    imagem = R.drawable.jose
                )

                IntegranteRow(
                    nome = "Peterson Leandro G. Batista",
                    curso = "Engenharia de Computação",
                    imagem = R.drawable.peterson
                )

                IntegranteRow(
                    nome = "Vinicius Patrick R. Rodrigues",
                    curso = "Engenharia de Computação",
                    imagem = R.drawable.patrick
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF114579),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 100.dp, height = 35.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text("OK", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            }
        },
        modifier = Modifier.clip(RoundedCornerShape(16.dp))
    )
}

@Composable
fun IntegranteRow(nome: String, curso: String, imagem: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imagem),
            contentDescription = nome,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = nome, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = curso, fontSize = 14.sp)
        }
    }
}

@Composable
fun SmallButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF114579),
            contentColor = Color.Gray
        ),
        modifier = Modifier
            .size(width = 150.dp, height = 35.dp)
            .clip(RoundedCornerShape(10.dp)),
        elevation = ButtonDefaults.elevatedButtonElevation(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CustomButton(
    text: String,
    iconResource: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1976D2),
            contentColor = Color.White
        ),
        modifier = Modifier
            .size(width = 250.dp, height = 75.dp)
            .shadow(10.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        elevation = ButtonDefaults.elevatedButtonElevation(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Image(
                painter = painterResource(id = iconResource),
                contentDescription = text,
                modifier = Modifier.size(35.dp)
            )
            Text(
                text = text,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreviewPrincipal() {
    Projetos7Theme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            CentroPrincipal(it)
        }
    }
}
