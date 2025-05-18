package com.example.projetos7

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
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue



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
            val navController = rememberNavController()
            val viewModel: ComponenteViewModel = viewModel()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    Projetos7Theme {
                        Scaffold {
                            CentroPrincipal(it, navController, viewModel)
                        }
                    }
                }
                composable("tela2"){
                    Projetos7Theme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            CronometroScreen(navController, viewModel)
                        }

                    }
                }
                composable("tela3") {
                    Projetos7Theme {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            bottomBar = {
                                BottonBarLight(0, navController)
                            }
                        ) {
                            Centro(it, navController, viewModel)
                        }
                    }
                }
                composable("PresencaSensor"){
                    Projetos7Theme {
                        ComponentPresenca(navController, viewModel)
                    }
                }
                composable("Temperatura"){
                    Projetos7Theme {
                        ComponentTemperature(navController, viewModel)
                    }
                }
            }

        }
        checkAudioPermission(this)


    }
    override fun onDestroy() {
        speechHelper.destroy()
        super.onDestroy()
    }
}

fun verificaFala(fala: String, listaDeStrings: ArrayList<String>):Boolean{

    var falouCorreto: Boolean = false
    listaDeStrings.forEach{
        if(fala.contains(it, ignoreCase = true)){
            falouCorreto = true
        }
    }

    if(falouCorreto){
        return true
    }else{
        return false
    }
}

@Composable
fun CentroPrincipal(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: ComponenteViewModel
) {
    val contexto = LocalContext.current
    checkAudioPermission(LocalContext.current)
    val componente by viewModel.componente.collectAsState()

    val ligarLuzQuarto = arrayListOf("ligar luz do quarto","ligar a luz do quarto","acender luz do quarto","acender a luz do quarto","liga luz do quarto","liga a luz do quarto","acenda luz do quarto","acenda a luz do quarto","liga luz quarto","luz quarto","acende luz quarto")
    val desligarLuzQuarto = arrayListOf("desligar luz do quarto","desligar a luz do quarto","apagar luz do quarto","apagar a luz do quarto","desliga luz do quarto","desliga a luz do quarto","apague luz do quarto","apague a luz do quarto","desliga luz quarto","luz quarto off","apaga luz quarto")
    val ligarLuzCozinha = arrayListOf("ligar luz da cozinha","ligar a luz da cozinha","acender luz da cozinha","acender a luz da cozinha","liga luz da cozinha","liga a luz da cozinha","acenda luz da cozinha","acenda a luz da cozinha","liga luz cozinha","luz cozinha","acende luz cozinha")
    val desligarLuzCozinha = arrayListOf("desligar luz da cozinha","desligar a luz da cozinha","apagar luz da cozinha","apagar a luz da cozinha","desliga luz da cozinha","desliga a luz da cozinha","apague luz da cozinha","apague a luz da cozinha","desliga luz cozinha","luz cozinha off","apaga luz cozinha")
    val ligarLuzBanheiro = arrayListOf("ligar luz do banheiro","ligar a luz do banheiro","acender luz do banheiro","acender a luz do banheiro","liga luz do banheiro","liga a luz do banheiro","acenda luz do banheiro","acenda a luz do banheiro","liga luz banheiro","luz banheiro","acende luz banheiro")
    val desligarLuzBanheiro = arrayListOf("desligar luz do banheiro","desligar a luz do banheiro","apagar luz do banheiro","apagar a luz do banheiro","desliga luz do banheiro","desliga a luz do banheiro","apague luz do banheiro","apague a luz do banheiro","desliga luz banheiro","luz banheiro off","apaga luz banheiro")
    val ligarLuzSala = arrayListOf("ligar luz da sala","ligar a luz da sala","acender luz da sala","acender a luz da sala","liga luz da sala","liga a luz da sala","acenda luz da sala","acenda a luz da sala","liga luz sala","luz sala","acende luz sala")
    val desligarLuzSala = arrayListOf("desligar luz da sala","desligar a luz da sala","apagar luz da sala","apagar a luz da sala","desliga luz da sala","desliga a luz da sala","apague luz da sala","apague a luz da sala","desliga luz sala","luz sala off","apaga luz sala")
    val ligarLuzes = arrayListOf("ligar luzes","ligar as luzes","acender luzes","acender as luzes","liga luzes","acende luzes","liga luz","acende a luz")
    val desligarLuzes = arrayListOf("desligar luzes","desligar as luzes","apagar luzes","apagar as luzes","desliga luzes","apaga luzes","luz off","todas as luzes off")
    val abrirPortao = arrayListOf("abrir portão","abre o portão","abra o portão","abre portão","abrir o portão","portão abrir","portão abre","abre o portao")
    val fecharPortao = arrayListOf("fechar portão","fecha o portão","feche o portão","fecha portão","fechar o portão","portão fechar","portão fecha","fecha o portao")
    val abrirJanelas = arrayListOf("abrir janelas","abrir as janelas","abre as janelas","abra as janelas","abre janelas","janelas abrir","abrir janela","abre janela")
    val fecharJanelas = arrayListOf("fechar janelas","fechar as janelas","fecha as janelas","feche as janelas","fecha janelas","janelas fechar","fechar janela","fecha janela")
    val abrirJanelaQuarto = arrayListOf("abrir janela do quarto","abrir a janela do quarto","abre a janela do quarto","abre janela do quarto","abrir janela quarto","abre janela quarto","janela do quarto")
    val fecharJanelaQuarto = arrayListOf("fechar janela do quarto","fechar a janela do quarto","fecha a janela do quarto","fecha janela do quarto","fechar janela quarto","fecha janela quarto","janela quarto off")
    val abrirJanelaBanheiro = arrayListOf("abrir janela do banheiro","abrir a janela do banheiro","abre a janela do banheiro","abre janela do banheiro","abrir janela banheiro","abre janela banheiro","janela do banheiro")
    val fecharJanelaBanheiro = arrayListOf("fechar janela do banheiro","fechar a janela do banheiro","fecha a janela do banheiro","fecha janela do banheiro","fechar janela banheiro","fecha janela banheiro","janela banheiro off")
    val ligarIrrigacaoJardim = arrayListOf("ligar irrigação","ligar irrigação do jardim","ligar regador","ligar o regador do jardim","regar jardim","ligue a irrigação","irrigar jardim","rega jardim","liga irrigador")
    val desligarIrrigacaoJardim = arrayListOf("desligar irrigação","desligar irrigação do jardim","desligar regador","desliga o regador do jardim","parar irrigação","parar regar jardim","desliga irrigação","desliga jardim","irrigação off")



    val coroutineScope = rememberCoroutineScope()
    var speechHelper = SpeechHelper(contexto as Activity) { spokenText ->

        var novoComponente = componente

        if (verificaFala(spokenText, ligarLuzes)) {
            novoComponente = novoComponente.copy(
                led_sala = true,
                led_quarto = true,
                led_cozinha = true,
                led_banheiro = true
            )
        }
        if (verificaFala(spokenText, ligarLuzSala)) {
            novoComponente = novoComponente.copy(led_sala = true)
        }
        if (verificaFala(spokenText, ligarLuzQuarto)) {
            novoComponente = novoComponente.copy(led_quarto = true)
        }
        if (verificaFala(spokenText, ligarLuzCozinha)) {
            novoComponente = novoComponente.copy(led_cozinha = true)
        }
        if (verificaFala(spokenText, ligarLuzBanheiro)) {
            novoComponente = novoComponente.copy(led_banheiro = true)
        }
        if (verificaFala(spokenText, desligarLuzes)) {
            novoComponente = novoComponente.copy(
                led_sala = false,
                led_quarto = false,
                led_cozinha = false,
                led_banheiro = false
            )
        }
        if (verificaFala(spokenText, desligarLuzSala)) {
            novoComponente = novoComponente.copy(led_sala = false)
        }
        if (verificaFala(spokenText, desligarLuzQuarto)) {
            novoComponente = novoComponente.copy(led_quarto = false)
        }
        if (verificaFala(spokenText, desligarLuzCozinha)) {
            novoComponente = novoComponente.copy(led_cozinha = false)
        }
        if (verificaFala(spokenText, desligarLuzBanheiro)) {
            novoComponente = novoComponente.copy(led_banheiro = false)
        }
        if (verificaFala(spokenText, abrirJanelas)) {
            novoComponente = novoComponente.copy(
                banheiro_open = true,
                quarto_open = true
            )
        }
        if (verificaFala(spokenText, abrirJanelaBanheiro)) {
            novoComponente = novoComponente.copy(banheiro_open = true)
        }
        if (verificaFala(spokenText, abrirJanelaQuarto)) {
            novoComponente = novoComponente.copy(quarto_open = true)
        }
        if (verificaFala(spokenText, fecharJanelas)) {
            novoComponente = novoComponente.copy(
                banheiro_open = false,
                quarto_open = false
            )
        }
        if (verificaFala(spokenText, fecharJanelaBanheiro)) {
            novoComponente = novoComponente.copy(banheiro_open = false)
        }
        if (verificaFala(spokenText, fecharJanelaQuarto)) {
            novoComponente = novoComponente.copy(quarto_open = false)
        }
        if (verificaFala(spokenText, abrirPortao)) {
            novoComponente = novoComponente.copy(garagem_open = true)
        }
        if (verificaFala(spokenText, fecharPortao)) {
            novoComponente = novoComponente.copy(garagem_open = false)
        }
        if (verificaFala(spokenText, ligarIrrigacaoJardim)) {
            novoComponente = novoComponente.copy(irrigacao_jardim = true)
        }
//        if (verificaFala(spokenText, desligarIrrigacaoJardim)) {
//            novoComponente = novoComponente.copy(irrigacao_jardim = false)
//        }

        viewModel.setComponente(novoComponente)

        Toast.makeText(contexto, "Você falou: $spokenText", Toast.LENGTH_LONG).show()
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
                onClick = {
                    navController.navigate("tela3")
//                    val intent = Intent(contexto, MainActivity3::class.java)
//                    contexto.startActivity(intent)
                }
            )
            CustomButton(
                text = "Temperatura",
                iconResource = R.drawable.temp,
                onClick = {
                    navController.navigate("Temperatura")
//                    val intent = Intent(contexto, Temperatura::class.java)
//                    contexto.startActivity(intent)
                }
            )
            CustomButton(
                text = "Presença",
                iconResource = R.drawable.sensorprox,
                onClick = {
                    navController.navigate("PresencaSensor")
//                    val intent = Intent(contexto, PresencaSensor::class.java)
//                    contexto.startActivity(intent)
                }
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




//@Preview(showBackground = true)
//@Composable
//fun GreetingPreviewPrincipal() {
//    Projetos7Theme {
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            containerColor = MaterialTheme.colorScheme.background
//        ) {
//            CentroPrincipal(it, navController, viewModel)
//        }
//    }
//}
