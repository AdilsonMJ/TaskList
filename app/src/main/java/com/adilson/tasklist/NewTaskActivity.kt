package com.adilson.tasklist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adilson.tasklist.databinding.ActivityNewTaskBinding

class NewTaskActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityNewTaskBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            onSubmit()
        }


    }

    private fun onSubmit() {

        if (binding.edtTaskTitle.text.isNullOrEmpty()) {
            binding.edtTaskTitle.error = "Please, Fill the Title"
            binding.edtTaskTitle.requestFocus()
            return
        }

        if (binding.edtDescriptionTask.text.isNullOrEmpty()) {
            binding.edtDescriptionTask.error = "Please put the task"
            binding.edtDescriptionTask.requestFocus()
            return
        }

        val newTask = Task(
            binding.edtTaskTitle.text.toString(),
            binding.edtDescriptionTask.text.toString()
        )

        val intentResult = Intent()
        intentResult.putExtra(Constant.EXTRA_NEW_TASK, newTask)
        setResult(RESULT_OK, intentResult)
        finish()

    }
}