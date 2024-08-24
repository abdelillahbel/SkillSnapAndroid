package dev.devunion.myportfolio.ui.home.components


import android.print.PrintAttributes.Margins
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.devunion.myportfolio.R

@Preview()
@Composable
fun AccountInfoSection() {

    Row {
        Row {
            Box(modifier = Modifier
                .size(80.dp)
                .padding(10.dp)) {

                Image(
                    painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
            }
            Column (modifier = Modifier  ) {
                Text(
                    text = "Bonjour,",
                    fontFamily = FontFamily(Font(R.font.inter_medium,FontWeight.Normal))
                )
                Text(text = "Kamel Boudalia",
                    fontFamily = FontFamily(Font(R.font.inter_medium,FontWeight.Normal))
                )
                Text(text = "0079999001963460238",
                    fontFamily = FontFamily(Font(R.font.inter_medium,FontWeight.Normal))
                )
                Row(modifier = Modifier.align(Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(10.dp)) {

                        Icon(modifier = Modifier,
                            imageVector = Icons.Default.Circle,
                            tint = Color.Green,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "Compte Actif", color = Color.Green)
                }
            }
        }
        Column {

        }
    }

}
