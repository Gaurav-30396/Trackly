package com.example.trackly.ui.theme


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: UserRepository
): ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks

    init {
        viewModelScope.launch {
            repository.getTasks().collect{
                list->
                _tasks.value= list
            }
        }
    }

    fun addTask(title: String){
        if(title.isBlank()) return
        viewModelScope.launch {
            repository.addTask(TaskEntity(title = title.trim()))
        }
    }
    fun toggleTask(task: TaskEntity){
        viewModelScope.launch {
            val updated = task.copy(isDone = !task.isDone)
            repository.updateTask(updated)
        }
    }
    fun resetAll() {
        viewModelScope.launch {
            repository.resetTasks()
        }
    }
    fun deleteTask(task: TaskEntity){
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

}