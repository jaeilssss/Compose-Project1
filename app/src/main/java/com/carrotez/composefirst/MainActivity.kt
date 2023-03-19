package com.carrotez.composefirst

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carrotez.composefirst.component.InputField
import com.carrotez.composefirst.ui.theme.ComposeFirstTheme
import com.carrotez.composefirst.widget.RoundIconButton

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFirstTheme {
                // A surface container using the 'background' color from the theme
                MyApp{
                        TopHeader()
                    MainContent()

                }
            }
        }
    }
}

@Composable
fun MyApp(content : @Composable () -> Unit){
    
    Surface(color = MaterialTheme.colors.background) {

        content()
    }

}

@Preview
@Composable
fun TopHeader(totalPerPerson : Double = 134.0){

    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        //.clip(shape = RoundedCornerShape(coner = ConerSize(12.dp)))
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val total = "%.2f".format(totalPerPerson)

            Text(text = "Total Per Person",
                style = MaterialTheme.typography.h5)


            Text(text = "$$total",
                style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.ExtraBold)

        }
    }

}


@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContent(){

    BillForm(){ billAmt ->
        Log.d("TAG", "MainContent : $billAmt")

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeFirstTheme {

        MyApp{
        }


    }
}

@ExperimentalComposeUiApi
@Composable
fun BillForm(modifier: Modifier = Modifier,
            onValueChange : (String) -> Unit = {}){

    val totalBillState = remember {

        mutableStateOf("0")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()

    }

    val keyboardController  = LocalSoftwareKeyboardController.current


    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp , color = Color.LightGray)
    ) {

        Column(modifier = Modifier.padding(6.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start) {


            InputField(
                valueState = totalBillState,
                labelId ="Enter Bill" ,
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions{
                    if(!validState) return@KeyboardActions

                    onValueChange(totalBillState.value.trim())

                    keyboardController?.hide()

                })
            if(validState){

                Row(modifier = Modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start,
                ){

                    Text(text = "Split",
                    modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    ))

                    Spacer(modifier = Modifier.width(120.dp))
                    Row(modifier  = Modifier.padding(horizontal = 3.dp)) {


                        RoundIconButton(imageVector = Icons.Default.Remove ,
                            onClick = {
                                Log.d("tag", "BillForm: remove")
                            })
                        RoundIconButton(imageVector = Icons.Default.Add ,
                            onClick = {
                                Log.d("tag", "BillForm: add")

                            })
                    }

                }
            }else{
                Box(){


                }
            }

        }

    }
}