package com.example.projetos7

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//class Temperatura : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            Projetos7Theme {
//                ComponentTemperature(navController, viewModel)
//            }
//        }
//    }
//}

@Composable
fun ComponentTemperature(navController: NavHostController, viewModel: ComponenteViewModel) {

    Scaffold(
        bottomBar = {
            BottomBar(1, navController)
        }
    ) {
        ContentTemperatura(modifier = Modifier.fillMaxSize()) {
            ThermostatScreen(25, it, viewModel)
        }
    }
}

@Composable
fun ContentTemperatura(
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

@SuppressLint("DefaultLocale")
@Composable
fun ThermostatScreen(temperature: Int, paddingValues: PaddingValues, viewModel: ComponenteViewModel) {
    var selectedItem by remember { mutableStateOf(false) }

    val ok = Api()
    var componente = remember {
        mutableStateOf(Componente())
    }

    var counter by remember { mutableStateOf(0) }

     LaunchedEffect (counter){
            while (true) {
                delay(2000) // 2 seconds delay
                CoroutineScope(Dispatchers.IO).launch {
                    componente.value = ok.getComponente()!!
                }
                counter++ // Increment counter or perform any other task
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            ThermostatDisplay(temperature = 71)

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
        )
        {

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
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (!selectedItem) "${String.format("%.1f", componente.value.temp_sensorQuarto.toFloat())}°C" else "${String.format("%.1f", componente.value.temp_sensorBanheiro.toFloat())}°C",
                fontSize = 48.sp,
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Controle de Temperatura",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(15.dp))

            SelectInput(selected = selectedItem, onSelectedChange = { isSelected ->
                selectedItem = isSelected
            })
        }
        SleepMode(selectedItem, viewModel)

    }
    Row(
        modifier = Modifier.absoluteOffset(0.dp, -5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.grade),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color(0xFFD8D6DB))
        )
    }
}

@Composable
fun ThermostatDisplay(temperature: Int) {

    Box(
        modifier = Modifier
            .size(400.dp) // Define o tamanho da Box circular
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
fun SelectInput(
    selected: Boolean = false,
    onSelectedChange: (Boolean) -> Unit,
    selectedColor: Color = Color(0xFF000D8B), // Cor quando selecionado
    unselectedColor: Color = Color(0xFF000000), // Cor quando não selecionado
    selectedTextColor: Color = Color(0xFFFFFFFF), // Cor do texto quando selecionado
    unselectedTextColor: Color = Color(0xFFFFFFFF), // Cor do texto quando não selecionado
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            .background(unselectedColor), // Cor de fundo padrão
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .height(42.dp)
                .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
                .background(if (!selected) selectedColor else Color.Transparent)
                .clickable { onSelectedChange(false) },
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Quarto",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = if (!selected) selectedTextColor else unselectedTextColor
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .height(42.dp)
                .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
                .background(if (selected) selectedColor else Color.Transparent)
                .clickable { onSelectedChange(true) },
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Banheiro",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = if (selected) selectedTextColor else unselectedTextColor
            )
        }
    }
}

@Composable
fun SleepMode(selectedItem: Boolean, viewModel: ComponenteViewModel) {

    val componente by viewModel.componente.collectAsState()

    var isJanelaOpen by remember {
        mutableStateOf(if (!selectedItem) componente.quarto_open else componente.banheiro_open)
    }

    Column(
        Modifier.padding(vertical = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Janela",
                fontSize = 18.sp,
                color = Color.White
            )
            Switch(
                checked = isJanelaOpen,
                onCheckedChange = { isChecked ->
                    isJanelaOpen = isChecked  // Atualiza o switch

                    val novoComponente = if (!selectedItem) {
                        componente.copy(quarto_open = isChecked)
                    } else {
                        componente.copy(banheiro_open = isChecked)
                    }

                    viewModel.setComponente(novoComponente)

                },
                enabled = !((selectedItem && componente.auto_Banheiro) || (!selectedItem && componente.auto_Quarto)),
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
                text = "Automático",
                fontSize = 18.sp,
                color = Color.White
            )
            Switch(
                checked = if (!selectedItem) componente.auto_Quarto else componente.auto_Banheiro,
                onCheckedChange = { isChecked ->
                    val novoComponente = if (!selectedItem) {
                        componente.copy(auto_Quarto = isChecked)
                    } else {
                        componente.copy(auto_Banheiro = isChecked)
                    }

                    viewModel.setComponente(novoComponente)
                },
                colors = SwitchDefaults.colors(
                    checkedBorderColor = Color.White,
                    checkedThumbColor = Color.White,
                )
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewThermostatScreen() {
//    Projetos7Theme {
//        ComponentTemperature(navController, viewModel)
//    }
//}
//
//


