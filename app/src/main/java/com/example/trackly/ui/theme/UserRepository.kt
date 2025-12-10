package com.example.trackly.ui.theme

class UserRepository(private val dao: TaskDao) {
    suspend fun addTask(task:TaskEntity
    ){
        dao.addTask(task)
    }
    fun getTasks() = dao.getTasks()
    suspend fun updateTask(task: TaskEntity){
        dao.update(task)
    }
    suspend fun deleteAll(){
        dao.deleteAll()
    }
    suspend fun resetTasks(){
        dao.resetAllTasks()
    }
    //suspend fun updateTask(task: TaskEntity) = dao.updateTask(task)
    suspend fun deleteTask(task: TaskEntity){
        dao.deleteTask(task)
    }
}