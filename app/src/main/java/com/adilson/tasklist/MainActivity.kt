package com.adilson.tasklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adilson.tasklist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpAdapter()
        setUpLayout()

    }

    private fun setUpLayout() {
        binding.fabNewTask.setOnClickListener {


            repeat(5){

                val rand = (1..1_000).random()
                adapter.addTask(
                    Task(
                        "Titulo $rand",
                        "Descricao"
                    )
                )
            }


        }
    }

    private fun setUpAdapter() {
        adapter = TaskAdapter{ task ->
            adapter.deleteTask(task)
        }
        binding.rvTasks.adapter = adapter
    }
}