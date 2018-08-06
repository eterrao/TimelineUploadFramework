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
import com.welove520.timelineupload.task.Photo
import com.welove520.timelineupload.task.TimelineTask
import com.welove520.timelineupload.task.TimelineTaskDao
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.content_upload.*
import kotlinx.android.synthetic.main.layout_item_task.view.*

class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        initView()
    }

    private lateinit var taskAdapter: RVAdapter
    var offset = 0
    private fun initView() {
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            var timelineTask = TimelineTask()
            timelineTask.sendTimeMS = System.currentTimeMillis()
            timelineTask.name = "task: " + (timelineTask.sendTimeMS)
            var photoList: ArrayList<Photo> = java.util.ArrayList()
            for (i in 1..9) {
                var photo = Photo()
                photo.name = "No." + i
                photo.path = "path: " + i
                photo.sendTimeMS = timelineTask.sendTimeMS
                photoList.add(photo)
            }
            MyApplication.get().daoSession.photoDao.insertOrReplaceInTx(photoList)
            MyApplication.get().daoSession.timelineTaskDao.insert(timelineTask)
            timelineTask.resetPhotoList()
            taskAdapter.refreshTask()
        }
        taskAdapter = RVAdapter()

        rv_main.adapter = taskAdapter
        var lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_main.layoutManager = lm

        rv_main.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                offset += dy
//                Log.e("tag: ", "dx: $dx, dy: $dy, offset: $offset")
            }
        })
        taskAdapter.refreshTask()
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
            return ViewHolder(this, view)
        }

        override fun getItemCount(): Int {
            return taskList?.size ?: 0
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            taskList?.get(position)?.let { holder.bindView(it, position) }
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItemViewType(position: Int): Int {
            return super.getItemViewType(position)
        }

        fun refreshTask() {
            taskList?.let {
                taskList.clear()
                var taskInDAO = MyApplication.get().daoSession.timelineTaskDao.queryBuilder()
                        .where(TimelineTaskDao.Properties.Uploaded.eq(false))
                        .orderAsc(TimelineTaskDao.Properties.SendTimeMS)
                        .build()
                        .list()
                taskList.addAll(taskInDAO)
            }
            notifyDataSetChanged()
        }
    }

    open class ViewHolder(adapter: RVAdapter, view: View) : RecyclerView.ViewHolder(view), UploadEvents {

        var rvAdapter = adapter

        override fun uploadFailed(id: Long) {
            var task: TimelineTask = MyApplication.get().daoSession.timelineTaskDao.queryBuilder().where(TimelineTaskDao.Properties.Id.eq(id)).build().unique()
            task.isUploaded = false
            task.uploadStatus = TimelineTask.UploadStatus.FAILED
            MyApplication.get().daoSession.timelineTaskDao.insertOrReplace(task)
            rvAdapter.refreshTask()
        }

        override fun uploadDelete(id: Long) {
            MyApplication.get().daoSession.timelineTaskDao.queryBuilder().where(TimelineTaskDao.Properties.Id.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities()
            rvAdapter.refreshTask()
        }

        override fun uploadRetry(id: Long) {
        }

        override fun uploadCompleted(id: Long) {
            var task: TimelineTask = MyApplication.get().daoSession.timelineTaskDao.queryBuilder().where(TimelineTaskDao.Properties.Id.eq(id)).build().unique()
            task.isUploaded = true
            task.uploadStatus = TimelineTask.UploadStatus.COMPLETED
            MyApplication.get().daoSession.timelineTaskDao.insertOrReplace(task)
            rvAdapter.refreshTask()
        }

        fun bindView(task: TimelineTask, position: Int) {
            itemView.tv_task_name.text = task?.name + " , id: " + task.id
            itemView.tv_task_status.text = "status: " + statusToString(task.uploadStatus)
            itemView.tv_task_uploaded.text = "uploaded: " + task.isUploaded.toString()
            itemView.btn_operation_finish.setOnClickListener { uploadCompleted(task.id) }
            itemView.btn_operation_retry.setOnClickListener { uploadRetry(task.id) }
            itemView.btn_operation_delete.setOnClickListener { uploadDelete(task.id) }
            itemView.btn_operation_failed.setOnClickListener { uploadFailed(task.id) }
        }

        private fun statusToString(uploadStatus: Int?): String {
            return when (uploadStatus) {
                0 -> "COMPLETED"
                1 -> "FAILED"
                2 -> "UPLOADING"
                3 -> "DELETED"
                else -> "NULL"
            }
        }
    }

    interface UploadEvents {
        fun uploadFailed(id: Long)
        fun uploadDelete(id: Long)
        fun uploadRetry(id: Long)
        fun uploadCompleted(id: Long)
    }
}
