package com.example.projetos7

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//class MainActivity3 : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            Projetos7Theme {
//                Scaffold(
//                    modifier = Modifier.fillMaxSize(),
//                    bottomBar = {
//                        BottonBarLight(0)
//                    }
//                ) {
//                    Centro(it, navController, viewModel)
//                }
//
//            }
//        }
//    }
//}


@Composable
fun GradientBackgroundBrush(
    isVerticalGradient: Boolean,
    colors: List<Color>
): Brush {
    val endOffset = if (isVerticalGradient) {
        Offset(0f, Float.POSITIVE_INFINITY)
    } else {
        Offset(Float.POSITIVE_INFINITY, 0f)
    }
    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffset
    )
}



private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "channelId",
            "Nome do Canal",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Descrição do Canal"
        getSystemService(context, NotificationManager::class.java)?.createNotificationChannel(
            channel
        )
    }
}

@Composable
fun ContentPrincipal(
    modifier: Modifier = Modifier
) {
    val gradientColorList = listOf(
        Color(0xFF000000),
        Color(0xFF0F3153),
        Color(0xFF134B85),
        Color(0xFF0F3F70),
        Color(0xFF000000),
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = GradientBackgroundBrush(
                    isVerticalGradient = true,
                    colors = gradientColorList
                )
            ),
        contentAlignment = Alignment.Center
    ) {

    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Centro(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: ComponenteViewModel
) {
    var ledQuarto by remember { mutableStateOf(false) }
    var ledCozinha by remember { mutableStateOf(false) }
    var ledSala by remember { mutableStateOf(false) }
    var ledBanheiro by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Settings", "Info")
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val Lamp = LocalContext.current
    val componente by viewModel.componente.collectAsState()



    ContentPrincipal(modifier = Modifier.fillMaxSize())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Controle de Lâmpadas",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 35.sp
            ),
            modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val novoComponente = componente.copy(led_quarto = !componente.led_quarto)

                        viewModel.setComponente(novoComponente)
                    },
                    modifier = Modifier
                        .height(100.dp)
                        .width(120.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(if (componente.led_quarto) 0xFFE0BB2B else 0xFF000000),
                    ),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.lampada),
                            contentDescription = "Lâmpada",
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .height(50.dp),
                            colorFilter = ColorFilter.tint(if (componente.led_quarto) Color.Black else Color.White)
                        )
                        Text(
                            "Quarto",
                            color = if (componente.led_quarto) Color.Black else Color.White
                        )
                    }
                }

                Button(
                    onClick = {
                        val novoComponente = componente.copy(led_cozinha = !componente.led_cozinha)

                        viewModel.setComponente(novoComponente)
                    },
                    modifier = Modifier
                        .height(100.dp)
                        .width(120.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(if (componente.led_cozinha) 0xFFE0BB2B else 0xFF000000),
                    ),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.lampada),
                            contentDescription = "Lâmpada",
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .height(50.dp),
                            colorFilter = ColorFilter.tint(if (componente.led_cozinha) Color.Black else Color.White)
                        )
                        Text(
                            "Cozinha",
                            color = if (componente.led_cozinha) Color.Black else Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(70.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val novoComponente = componente.copy(led_sala = !componente.led_sala)

                        viewModel.setComponente(novoComponente)
                    },
                    modifier = Modifier
                        .height(100.dp)
                        .width(120.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(if (componente.led_sala) 0xFFE0BB2B else 0xFF000000),
                    ),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.lampada),
                            contentDescription = "Lâmpada",
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .height(50.dp),
                            colorFilter = ColorFilter.tint(if (componente.led_sala) Color.Black else Color.White)
                        )
                        Text(
                            "Sala",
                            color = if (componente.led_sala) Color.Black else Color.White
                        )
                    }
                }

                Button(
                    onClick = {
                        val novoComponente = componente.copy(led_banheiro = !componente.led_banheiro)

                        viewModel.setComponente(novoComponente)
                    },
                    modifier = Modifier
                        .height(100.dp)
                        .width(120.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(if (componente.led_banheiro) 0xFFE0BB2B else 0xFF000000),
                    ),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.lampada),
                            contentDescription = "Lâmpada",
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .height(50.dp),
                            colorFilter = ColorFilter.tint(if (componente.led_banheiro) Color.Black else Color.White)
                        )
                        Text(
                            "Banheiro",
                            color = if (componente.led_banheiro) Color.Black else Color.White
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun BottonBarLight(index: Int, navController: NavHostController){
    var selectedItem by remember { mutableStateOf(index) }
    val coroutineScope = rememberCoroutineScope()
    val Lamp = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomEnd = 6.dp,
                    bottomStart = 6.dp
                )
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF061B31),
                        Color(0xFF073A74)
                    )
                )
            )
    ) {
        NavigationBar(
            containerColor = Color.Transparent
        ) {
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.lampprinci),
                        contentDescription = "Lâmpada",
                        modifier = Modifier.size(30.dp),
                        colorFilter = if (selectedItem == 0) ColorFilter.tint(Color.Black) else ColorFilter.tint(
                            Color.White
                        )
                    )
                },
                label = { Text("Lâmpada", color = Color.White, fontWeight = FontWeight.Bold) },
                selected = selectedItem == 0,
                onClick = {
                        navController.navigate("tela3")
//                    val intent = Intent(Lamp, MainActivity3::class.java)
//                    Lamp.startActivity(intent)
                    selectedItem = 0
                    coroutineScope.launch {
                    }
                }
            )
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.temp),
                        contentDescription = "Temperatura",
                        modifier = Modifier.size(30.dp),
                        colorFilter = if (selectedItem == 1) ColorFilter.tint(Color.Black) else ColorFilter.tint(
                            Color.White
                        )
                    )
                },
                label = { Text("Temperatura", color = Color.White, fontWeight = FontWeight.Bold) },
                selected = selectedItem == 1,
                onClick = {
                    navController.navigate("tela3")
//                    val intent = Intent(Lamp, Temperatura::class.java)
//                    Lamp.startActivity(intent)
                    selectedItem = 1
                    coroutineScope.launch {
                    }
                }
            )
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.sensorprox),
                        contentDescription = "Presença",
                        modifier = Modifier.size(30.dp),
                        colorFilter = if (selectedItem == 2) ColorFilter.tint(Color.Black) else ColorFilter.tint(
                            Color.White
                        )
                    )
                },
                label = { Text("Presença", color = Color.White, fontWeight = FontWeight.Bold) },
                selected = selectedItem == 2,
                onClick = {
                    navController.navigate("PresencaSensor")
//                    val intent = Intent(Lamp, PresencaSensor::class.java)
//                    Lamp.startActivity(intent)
                    selectedItem = 2
                    coroutineScope.launch {
                    }
                }
            )
        }
    }
}


//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Projetos7Theme {
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            bottomBar = {
//                BottonBarLight(0)
//            }
//        ) {
//            Centro(it, navController, viewModel)
//        }
//
//    }
//}