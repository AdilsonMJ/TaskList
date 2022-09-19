package com.adilson.tasklist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adilson.tasklist.databinding.ActivityMainBinding
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpAdapter()
        setUpLayout()


    }

    fun showGif(){
        val gif = binding.imgGifEmpty
        Glide.with(this).load(R.drawable.gif_empty).into(gif)
    }

    fun onDataUpdate() = if (adapter.isEmpty()) {
        binding.rvTasks.visibility = View.GONE
        binding.imgGifEmpty.visibility = View.VISIBLE
        showGif()
    } else {
        binding.rvTasks.visibility = View.VISIBLE
        binding.imgGifEmpty.visibility = View.GONE
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode != RESULT_OK) return@registerForActivityResult

        val task = result.data?.extras?.getSerializable(Constant.EXTRA_NEW_TASK) as Task
        adapter.addTask(task)
        onDataUpdate()

    }

    private fun setUpLayout() {
        binding.fabNewTask.setOnClickListener {

            resultLauncher.launch(Intent(this, NewTaskActivity::class.java))

        }
    }

    private fun setUpAdapter() {

        adapter = TaskAdapter(

            onDeleteClick = { taskToBeDeleted ->

                showDeleteConfirmation(taskToBeDeleted) { task ->
                    adapter.deleteTask(task)
                    onDataUpdate()
                }

            },

            onClick = { taskToBeShow ->

                showTaskDetails(taskToBeShow) { task ->
                    adapter.updateTask(task)
                }

            }

        )
        binding.rvTasks.adapter = adapter
        onDataUpdate()

    }

    private fun showTaskDetails(task: Task, onTaskStatusChanged: (Task) -> Unit) {

        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Task Details")
            setMessage(
                """
                    Title: ${task.title}
                    Details: ${task.description}
                    Status: ${if (task.done) "Done" else "Undone"}
                    
                """.trimIndent()
            )
            setCancelable(false)
            setPositiveButton(
                if (task.done) "Undone" else "Done"
            ) { _, _ ->
                task.done = !task.done
                onTaskStatusChanged(task)
            }
            setNegativeButton("Closer") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()

    }

    private fun showDeleteConfirmation(task: Task, onConfirm: (Task) -> Unit) {

        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Confirmation")
            setMessage("Do you Like delete the \"${task.title}\" ")
            setCancelable(false)

            setPositiveButton("Yes") { _, _ ->
                onConfirm(task)
            }

            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()

    }
}