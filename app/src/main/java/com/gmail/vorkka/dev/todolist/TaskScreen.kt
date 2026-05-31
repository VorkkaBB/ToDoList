import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.vorkka.dev.todolist.Task

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val taskList by viewModel.allTasks.collectAsState()
    var taskText by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var taskToDelete by remember { mutableStateOf<Task?>(null) }

    if (showDialog && taskToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                taskToDelete = null
            },
            title = { Text(text = "Confirmar eliminación") },
            text = { Text(text = "¿Estás seguro de que deseas borrar la tarea \"${taskToDelete?.name}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        taskToDelete?.let { viewModel.deleteTask(it) }
                        showDialog = false
                        taskToDelete = null
                    }
                ) {
                    Text("Borrar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        taskToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Mis Tareas",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = taskText,
            onValueChange = { taskText = it },
            label = { Text("Escribe una tarea") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.addTask(taskText)
                taskText = ""
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text("Guardar Tarea")
        }

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
        ) {
            items(taskList) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .combinedClickable(
                            onClick = { },
                            onLongClick = {
                                taskToDelete = task
                                showDialog = true
                            }
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = task.name,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}