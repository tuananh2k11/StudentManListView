package vn.edu.hust.activityexamples

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val studentList = mutableListOf<Student>()
    private lateinit var studentAdapter: ArrayAdapter<String>

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val hoten = result.data?.getStringExtra("hoten")
            val mssv = result.data?.getStringExtra("mssv")
            val position = result.data?.getIntExtra("position", -1)

            if (hoten != null && mssv != null) {
                if (position != null && position != -1) {
                    // Nếu có position, tức là đang sửa sinh viên
                    studentList[position] = Student(hoten, mssv)
                } else {
                    // Nếu không có position, tức là thêm mới sinh viên
                    studentList.add(Student(hoten, mssv))
                }
                updateStudentList() // Cập nhật lại ListView
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Student Management"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.gradient_background, null))

        val listView = findViewById<ListView>(R.id.listView)
        val items = mutableListOf<String>()

        studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = studentAdapter

        // Thêm dữ liệu mẫu
        studentList.add(Student("Nguyễn Văn An", "SV001"))
        studentList.add(Student("Trần Thị Bảo", "SV002"))
        studentList.add(Student("Lê Hoàng Cường", "SV003"))
        studentList.add(Student("Phạm Thị Dung", "SV004"))
        studentList.add(Student("Đỗ Minh Đức", "SV005"))
        studentList.add(Student("Vũ Thị Hoa", "SV006"))
        studentList.add(Student("Hoàng Văn Hải", "SV007"))
        studentList.add(Student("Bùi Thị Hạnh", "SV008"))
        studentList.add(Student("Đinh Văn Hùng", "SV009"))
        studentList.add(Student("Nguyễn Thị Linh", "SV010"))
        studentList.add(Student("Phạm Văn Long", "SV011"))
        studentList.add(Student("Trần Thị Mai", "SV012"))
        studentList.add(Student("Lê Thị Ngọc", "SV013"))
        studentList.add(Student("Vũ Văn Nam", "SV014"))
        studentList.add(Student("Hoàng Thị Phương", "SV015"))
        studentList.add(Student("Đỗ Văn Quân", "SV016"))
        studentList.add(Student("Nguyễn Thị Thu", "SV017"))
        studentList.add(Student("Trần Văn Tài", "SV018"))
        studentList.add(Student("Phạm Thị Tuyết", "SV019"))
        studentList.add(Student("Lê Văn Vũ", "SV020"))
        updateStudentList()

        findViewById<Button>(R.id.button_add_student).setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            launcher.launch(intent) // Mở AddStudentActivity
        }

        registerForContextMenu(listView) // Đăng ký Context Menu cho ListView
    }

    private fun updateStudentList() {
        val studentNames = studentList.map { "${it.hoten} - ${it.mssv}" }
        studentAdapter.clear()
        studentAdapter.addAll(studentNames)
        studentAdapter.notifyDataSetChanged() // Cập nhật lại ListView
    }

    // Thiết lập Context Menu
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu) // Inflate context menu
    }

    // Xử lý khi người dùng chọn một mục trong Context Menu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val pos = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val selectedStudent = studentList[pos] // Lấy sinh viên được chọn

        when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                intent.putExtra("hoten", selectedStudent.hoten)
                intent.putExtra("mssv", selectedStudent.mssv)
                // Gửi vị trí sinh viên cần chỉnh sửa
                intent.putExtra("position", pos)
                launcher.launch(intent) // Mở AddStudentActivity
            }
            R.id.action_remove -> {
                studentList.removeAt(pos) // Xóa sinh viên khỏi danh sách
                updateStudentList() // Cập nhật lại ListView
            }
        }
        return super.onContextItemSelected(item)
    }
}


