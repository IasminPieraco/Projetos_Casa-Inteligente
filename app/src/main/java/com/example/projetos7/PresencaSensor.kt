
package com.example.projetos7

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetos7.ui.theme.Projetos7Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PresencaSensor : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projetos7Theme {
                ComponentPresenca()
            }
        }
    }
}
@Composable
fun ComponentPresenca(){
    Scaffold (
        bottomBar = {
            BottomBar(2)
        }
    ){
        ContentPresenca(modifier = Modifier.fillMaxSize()){
//        ThermostatScreen()
            PresencaScreen("Presença",it)
        }
    }

}


@Composable
fun ContentPresenca(
    modifier: Modifier = Modifier,
    function: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val gradientColorList = listOf(
        Color(0xFF000000),
        Color(0xFF071D33),
        Color(0xFF021529),
        Color(0xFF021C36),
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
        function()
    }
}

@Composable
fun PresencaScreen(presenca: String, paddingValues: PaddingValues) {
    var selectedItem by remember { mutableStateOf(false) }

    val ok = Api()
    var componente = remember {
        mutableStateOf(Componente())
    }

//    var counter by remember { mutableStateOf(0) }
//
//    LaunchedEffect (counter){
//        while (true) {
//            delay(2000) // 2 seconds delay
//            CoroutineScope(Dispatchers.IO).launch {
//                componente.value = ok.getComponente()!!
//            }
//            counter++ // Increment counter or perform any other task
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            PresencaDisplay(temperature = 71)

        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(200.dp)
                .align(Alignment.CenterStart)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF000000), Color(0xFF170485)),

                        )
                )
        ) {

        }
    }
    Row(
        Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color.Black)

            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (!selectedItem) {
                    if (componente.value.sensor_Sala) "Presença" else "Ausente"
                } else {
                    println(componente.value.sensor_Garagem)
                    if (componente.value.sensor_Garagem) "Presença" else "Ausente"
                },
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sensor de Presença",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            SelectInputPresenca(selected = selectedItem) {
                selectedItem = it
            }
        }

        SensorMode(selectedItem, componente)

    }
    Row(
        modifier = Modifier.absoluteOffset(0.dp, -5.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.grade),
            contentDescription ="",
            colorFilter = ColorFilter.tint(Color(0xFFD8D6DB))
        )
    }
}

@Composable
fun BottomBar(item: Int = 0){
    var selectedItem by remember { mutableStateOf(item) }
    val Presen = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                        Color(0xFF000D8B),
                        Color(0xFF021C36)
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
                    val intent = Intent(Presen, MainActivity3::class.java)
                    Presen.startActivity(intent)
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
                    val intent = Intent(Presen, Temperatura::class.java)
                    Presen.startActivity(intent)
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
                    val intent = Intent(Presen, PresencaSensor::class.java)
                    Presen.startActivity(intent)
                    coroutineScope.launch {
                    }
                }
            )
        }
    }
}

@Composable
fun SelectInputPresenca(
    selected: Boolean = false,
    onSelectedChange: (Boolean) -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            .background(Color.Black),
        verticalAlignment = Alignment.CenterVertically

    ) {



        Column(
            modifier = Modifier
                .weight(1f)
                .height(42.dp)
                .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
                .background(if (!selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable { onSelectedChange(false) }
            ,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = "Sala", modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = if (!selected) Color.White else Color.White
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .height(42.dp)
                .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
                .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable { onSelectedChange(true) }
            ,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = "Garagem", modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = if (selected) Color.White else Color.White
            )
        }
    }
}
@Composable
fun PresencaDisplay(temperature: Int) {

    Box(
        modifier = Modifier
            .size(400.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF000D8B)),
                    radius = 500f
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF000000), Color(0xFF000D8B)),
                        radius = 395f
                    )
                ),
            contentAlignment = Alignment.Center
        ) {

        }
    }

}


@Composable
fun SensorMode(selectedItem: Boolean, componente: MutableState<Componente>) {
    var lamp by remember { mutableStateOf(false) }
    var auto by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val ok = Api()

    var isLedOn by remember {
        mutableStateOf(if (!selectedItem) componente.value.led_sala else componente.value.led_cozinha)
    }
    Column (
        Modifier.padding(vertical = 16.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Lâmpada",
                fontSize = 18.sp,
                color = Color.White
            )
            Switch(
                checked = isLedOn,
                onCheckedChange = {
                    isLedOn = it

                    if (!selectedItem) {
                        componente.value = componente.value.copy(led_sala = it)
                    } else {
                        componente.value = componente.value.copy(led_cozinha = it)
                    }

                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            ok.setComponente(componente.value)
                        }
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedBorderColor = Color.White,
                    checkedThumbColor = Color.White,
                )
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Automatico",
                fontSize = 18.sp,
                color = Color.White
            )
            Switch(
                checked = if (!selectedItem) componente.value.auto_Sala else componente.value.auto_Garagem,
                onCheckedChange = {
                    if (!selectedItem) {
                        componente.value = componente.value.copy(auto_Sala = it)
                    } else {
                        componente.value = componente.value.copy(auto_Garagem = it)
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        ok.setComponente(componente.value)
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedBorderColor = Color.White,
                    checkedThumbColor = Color.White,
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSensorScreen() {
    Projetos7Theme {
        ComponentPresenca()
    }
}




