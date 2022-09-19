package com.adilson.tasklist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adilson.tasklist.databinding.ResItemTaskBinding

class TaskAdapter(
    private val onClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
    ) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    private val tasks = mutableListOf<Task>()

    inner class TaskViewHolder(
        itemView : ResItemTaskBinding
    ) : RecyclerView.ViewHolder(itemView.root){

        private val tvTitleTask : TextView
        private val imgBntDeleteTask : ImageButton
        private val clTask : ConstraintLayout


        init {
            tvTitleTask = itemView.tvTitleTask
            imgBntDeleteTask = itemView.imgBtnDeleteTask
            clTask = itemView.clTask
        }


        fun bind(
            task: Task,
            onDeleteClick: (Task) -> Unit,
            onClick: (Task) -> Unit,
        ) {
            tvTitleTask.text = task.title
            imgBntDeleteTask.setOnClickListener {
                onDeleteClick(task)
            }

            clTask.setOnClickListener{
                onClick(task)
            }

            if (task.done){
                tvTitleTask.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                imgBntDeleteTask.setImageResource(R.drawable.trash_white)
                clTask.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.medium_sea_green))

            }else{
                tvTitleTask.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                imgBntDeleteTask.setImageResource(R.drawable.trash_darker)
                clTask.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.silver_sand))
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val resItemUserBinding = ResItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )

        return TaskViewHolder(resItemUserBinding)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], onDeleteClick, onClick)
    }

    override fun getItemCount(): Int = tasks.size


    fun addTask(task: Task){
        tasks.add(task)
        notifyItemInserted(tasks.size -1)
    }

    fun deleteTask(task: Task){
        val deletePosition = tasks.indexOf(task)
        tasks.remove(task)
        notifyItemRemoved(deletePosition)
    }

    fun updateTask(task: Task) {

        val upDatePosition = tasks.indexOf(task)
        tasks[upDatePosition] = task
        notifyItemChanged(upDatePosition)

    }

    fun isEmpty() : Boolean = tasks.isEmpty()

}