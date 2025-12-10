package com.example.trackly.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.matchParentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.trackly.R


@Composable
fun MainScreen(viewModel: TaskViewModel){

    val tasks by viewModel.tasks.collectAsState()
    var isDone by remember{ mutableStateOf(false)}
    val totalTasks = tasks.size
    var taskDone = tasks.count{it.isDone}
    var taskRemaining = totalTasks - taskDone
    var isAlert by remember{ mutableStateOf(false)}
    var taskName by remember{ mutableStateOf("")}
    val progress = if (totalTasks > 0) taskDone.toFloat() / totalTasks else 0f
    val headerGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFB981FF).copy(alpha = 0.79f), // 27% opacity
            Color(0xFF9B57F1).copy(alpha = 0.27f)  // 79% opacity
        )
    )
    val fabGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF8A4FFF),  // brighter deep purple
            Color(0xFF5A2FFF)   // richer darker tone
        )
    )
if (isAlert)
    {
        AlertDialog(onDismissRequest = { isAlert = false },
            title = {
                Text(text = "Enter the task")
            },
            text = {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = taskName,
                        onValueChange = { taskName = it },
                        label = { Text(text = "Enter Task", fontWeight = FontWeight.ExtraBold) })

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (taskName.isNotBlank()) {
                            viewModel.addTask(taskName)
                            taskName = ""
                            isAlert = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF8A4FFF),
                                    Color(0xFF5A2FFF)
                                )
                            )
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "ADD TASKS",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }


        )
    }
   Scaffold(
       floatingActionButton = {
           FloatingActionButton(
               onClick = { isAlert=true },
               modifier = Modifier
                   .padding(end = 20.dp, bottom = 20.dp)  // distance from edges only
                   .size(60.dp),
               shape = CircleShape,
               containerColor = Color.Transparent,
               elevation = FloatingActionButtonDefaults.elevation(
                   defaultElevation = 10.dp,
                   pressedElevation = 12.dp
               )
           ) {
               Box(
                   modifier = Modifier
                       .fillMaxSize()
                       .background(fabGradient, shape = CircleShape),
                   contentAlignment = Alignment.Center
               ) {
                   Icon(
                       imageVector = Icons.Default.Add,
                       contentDescription = "Add",
                       tint = Color.White,
                       modifier = Modifier.size(28.dp)
                   )
               }
           }

       }
   ) {

       innerpadding->
       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(innerpadding)

               .padding(8.dp),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Card(
               modifier = Modifier
                   .fillMaxWidth()
                   .fillMaxWidth()
                   .padding(8.dp)
                   .height(201.dp)
                 //  .width(395.dp)
           ) {
               Column(modifier = Modifier
                   .background(
                       headerGradient


                   )
                   .fillMaxSize()
                   .padding(16.dp)) {
                   Row(modifier = Modifier
                       .fillMaxWidth()
                       .padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                       GradientCircularProgress(progress = progress)
                       Column(modifier = Modifier.padding(4.dp)) {
                           Text(text = "Total Task: $totalTasks", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                           Spacer(modifier = Modifier.height(4.dp))
                           Text(text = "Pending Task: $taskRemaining", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)

                       }

                   }
                   Spacer(modifier = Modifier.height(4.dp))
                   if (totalTasks !=0 && totalTasks == taskDone){
                       Text(text = "Today's tasks are completed",fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Color.White )
                   }


               }
               // yahan baad me summary card + percentage aayega
           }

           Spacer(modifier = Modifier.height(8.dp))

           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(8.dp),
               horizontalArrangement = Arrangement.End
           ) {
               Button(onClick = {
                                viewModel.resetAll()
                   // yahan resetAll() call karega ViewModel se
                   // viewModel.resetAll()
               },
                   modifier = Modifier
                       .clip(RoundedCornerShape(12.dp))
                       .background(
                           brush = Brush.horizontalGradient(
                               listOf(

                                   Color(0xFF8A4FFF),
                                   Color(0xFF5A2FFF)

                               )
                           )

                       ),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = Color.Transparent
                   )
                   ) {
                   Text(text = "RESET", fontStyle = FontStyle.Normal, color = Color.White)

               }
           }

           Spacer(modifier = Modifier.height(8.dp))


           LazyColumn(modifier = Modifier
               .fillMaxWidth()
               .weight(1f),
               contentPadding = PaddingValues(
                   bottom = 96.dp,    // leave room for the FAB
                   start = 6.dp,
                   end = 6.dp,
                   top = 8.dp
               )
               ) {
               // yahan tasks list dikhayenge
               items(
                   items = tasks,
                   key = {it.id}
               ){
                   task->
                   Card(modifier = Modifier
                       .fillMaxWidth()
                       .padding(8.dp)
                       .height(100.dp)
                      // .width(364.dp)
                   ) {
                       Row(modifier = Modifier
                           .fillMaxWidth()
                           .padding(6.dp),
                           horizontalArrangement = Arrangement.SpaceBetween,
                           verticalAlignment = Alignment.CenterVertically) {
                           Text(text = task.title, fontWeight = FontWeight.Bold, fontSize = 24.sp )
                          // Spacer(modifier = Modifier.width(4.dp))
                          Column(modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                               Switch(
                                   checked = task.isDone, onCheckedChange = { checked ->
                                       viewModel.toggleTask(task)
                                   },
                                   colors = SwitchDefaults.colors(
                                       uncheckedTrackColor = Color(0xFFE6E6E6),
                                       checkedTrackColor = Color(0xFF8A4FFF),
                                       checkedThumbColor = Color.White
                                   )
                               )
                              Spacer(modifier = Modifier.height(4.dp))
                              Icon(imageVector = Icons.Default.Delete, contentDescription = "", tint = Color.Gray, modifier = Modifier.clickable { viewModel.deleteTask(task ) }.size(36.dp) )


                           }

                           
                       }

                   }

               }
           }
       }

   }

   }