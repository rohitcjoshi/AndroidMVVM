package com.application.coroutinetestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.application.coroutinetestapp.ui.theme.CoroutineTestAppTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Data model for the API response
data class ImageResponse(val message: String, val status: String)

// Retrofit API Service
interface ApiService {
    @GET("breeds/image/random")
    suspend fun getRandomImage(): ImageResponse
}

// Retrofit Instance
object RetrofitClient {
    private const val BASE_URL = "https://dog.ceo/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

class MainActivity : ComponentActivity() {

    private val IMAGE_URL = "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineTestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        ImageDisplay(IMAGE_URL)
                    }
                }
            }
        }
    }
}

@Composable
fun ImageDisplay(imageDownloadUrl: String) {
    var imageUrl by remember { mutableStateOf<String?>(null) }

    // Fetch the image URL using Retrofit
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.apiService.getRandomImage()
            imageUrl = imageDownloadUrl//response.message
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Downloaded Image",
            modifier = Modifier.size(500.dp)
        )
    } else {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoroutineTestAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                ImageDisplay("https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png")
            }
        }
    }
}
