package com.example.trackly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trackly.ui.theme.MainScreen
import com.example.trackly.ui.theme.TaskDatabase
import com.example.trackly.ui.theme.TaskViewModel
import com.example.trackly.ui.theme.TaskViewModelFactory
import com.example.trackly.ui.theme.TracklyTheme
import com.example.trackly.ui.theme.UserRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContent {
            TracklyTheme {

                // 1) Database
                val db = TaskDatabase.getDatabase(applicationContext)

                // 2) Repository  (UserRepository hi use kar rahe ho)
                val repository = UserRepository(db.taskDao())

                // 3) ViewModel with factory
                val taskViewModel: TaskViewModel = viewModel(
                    factory = TaskViewModelFactory(repository))
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = taskViewModel)

                }
            }
        }
    }
}

