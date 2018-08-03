package com.welove520.timelineupload

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.welove520.timelineupload.app.MyApplication
import com.welove520.timelineupload.task.TimelineTask
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.content_upload.*
import kotlinx.android.synthetic.main.layout_item_task.view.*
import java.util.*

class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        initView()
    }

    private lateinit var taskAdapter: RVAdapter

    private fun initView() {
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var timelineTask = TimelineTask()
            timelineTask.name = "task: " + (MyApplication.get().daoSession.timelineTaskDao.queryBuilder().list().size + 1)
            timelineTask.sendTimeMS = System.currentTimeMillis()
            MyApplication.get().daoSession.timelineTaskDao.insert(timelineTask)
            taskAdapter.refreshTask()
        }
        taskAdapter = RVAdapter()

        rv_main.adapter = taskAdapter
        var lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_main.layoutManager = lm
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_upload, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    class RVAdapter : RecyclerView.Adapter<ViewHolder>() {
        private val taskList: ArrayList<TimelineTask>? = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var view = View.inflate(parent.context, R.layout.layout_item_task, null)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return taskList?.size ?: 0
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            taskList?.get(position)?.let { holder.bindView(it, position) }
        }

        fun refreshTask() {
            taskList?.let {
                taskList.clear()
                var taskInDAO = MyApplication.get().daoSession.timelineTaskDao.queryBuilder().list()
                taskList.addAll(taskInDAO)
            }
            notifyDataSetChanged()
        }
    }

    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        var btn = view.findViewById<TextView>(R.id.tv_task_name)

        fun bindView(task: TimelineTask, position: Int) {
            itemView.tv_task_name.text = task?.name

        }


    }
}
