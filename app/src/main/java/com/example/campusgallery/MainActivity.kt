package com.example.campusgallery
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campusgallery.ui.theme.CampusGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusGalleryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CampusGalleryScreen()
                }
            }
        }
    }
}

data class GalleryItem(
    @DrawableRes val imageRes: Int,
    val title: String,
    val description: String
)

@Composable
fun CampusGalleryScreen() {
    val galleryItems = remember {
        listOf(
            GalleryItem(
                imageRes = R.drawable.campus_msu_1,
                title = "Susan A. Cole Hall",
                description = "MSU’s original 1908 building rededicated in 2021 to honor President Susan A. Cole now houses Red Hawk Central and Undergraduate Admissions after a full renovation."
            ),
            GalleryItem(
                imageRes = R.drawable.campus_msu_2,
                title = "University Hall",
                description = "A major academic hub with education-focused centers and IT services; its 7th-floor Conference Center offers panoramic views of the Manhattan skyline."
            ),
            GalleryItem(
                imageRes = R.drawable.campus_msu_3,
                title = "Center for Computing & Information Science (CCIS)",
                description = "Formerly Mallory Hall, CCIS reopened in 2018 after a full renovation with a new 4th floor. It’s home to Montclair’s School of Computing, with modern teaching and research spaces."
            ),
            GalleryItem(
                imageRes = R.drawable.campus_msu_4,
                title = "Red Hawk Statue",
                description = "Iconic campus landmark on the University Promenade. A popular photo spot students rub the beak for good luck."
            )
        )
    }

    var currentIndex by rememberSaveable { mutableStateOf(0) }
    val itemCount = galleryItems.size
    val configuration = LocalConfiguration.current
    val isLargeScreen = configuration.smallestScreenWidthDp >= 600
    val maxImageHeight = if (isLargeScreen) 480.dp else 360.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Image ${currentIndex + 1} of $itemCount",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(12.dp))
        Crossfade(targetState = currentIndex, label = "slide") { pageIndex ->
            val currentItem = galleryItems[pageIndex]
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(currentItem.imageRes),
                    contentDescription = currentItem.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = maxImageHeight),
                    contentScale = ContentScale.Fit   // <-- prevents cropping on all devices
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = currentItem.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = currentItem.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
        Spacer(Modifier.height(16.dp))


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {

                currentIndex = if (currentIndex == 0) itemCount - 1 else currentIndex - 1
            }) {
                Text("Previous")
            }
            Button(onClick = {

                currentIndex = (currentIndex + 1) % itemCount
            }) {
                Text("Next")
            }
        }
    }
}


