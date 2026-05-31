package com.gmail.vorkka.dev.todolist

import TaskScreen
import TaskViewModel
import TaskViewModelFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.gmail.vorkka.dev.todolist.ui.theme.ToDoListTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = TaskDatabase.getDatabase(this)
        val taskDao = database.taskDao()

        setContent {
            ToDoListTheme {
                Surface {
                    val viewModel: TaskViewModel = viewModel(
                        factory = TaskViewModelFactory(taskDao)
                    )
                    TaskScreen(viewModel = viewModel)
                }
            }
        }
    }
}