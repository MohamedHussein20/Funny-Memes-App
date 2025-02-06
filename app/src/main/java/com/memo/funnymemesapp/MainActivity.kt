package com.memo.funnymemesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memo.funnymemesapp.models.Meme
import com.memo.funnymemesapp.screens.DetailScreen
import com.memo.funnymemesapp.screens.MainScreen
import com.memo.funnymemesapp.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var memesList by remember {
                mutableStateOf(listOf<Meme>())
            }
            val scope = rememberCoroutineScope()
            LaunchedEffect(key1 = true) {
                scope.launch(Dispatchers.IO) {
                    val response = try {
                        RetrofitInstance.api.getMemesList()
                    } catch (e: HttpException) {
                        Toast.makeText(
                            this@MainActivity,
                            "App Error: ${e.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    } catch (e: IOException) {
                        Toast.makeText(
                            this@MainActivity,
                            "IO Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }
                    if (response.isSuccessful && response.body() != null) {
                        withContext(Dispatchers.Main) {
                            memesList = response.body()!!.data.memes
                        }
                    }
                }
            }
            NavHost(
                navController = navController,
                startDestination = "Main Screen"
            ) {
                composable("Main Screen") {
                    MainScreen(
                        memesList = memesList,
                        navController = navController
                    )
                }
                composable("Detail Screen") {
                    DetailScreen()
                }
            }
        }
    }
}

