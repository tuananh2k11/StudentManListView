package vn.edu.hust.activityexamples

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
  private var studentPosition: Int = -1 // Biến lưu vị trí sinh viên (khi sửa)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_student)

    val editHoten = findViewById<EditText>(R.id.edit_hoten)
    val editMssv = findViewById<EditText>(R.id.edit_mssv)

    // Lấy thông tin sinh viên nếu có (cho trường hợp sửa)
    studentPosition = intent.getIntExtra("position", -1)
    val hoten = intent.getStringExtra("hoten")
    val mssv = intent.getStringExtra("mssv")

    if (hoten != null && mssv != null) {
      editHoten.setText(hoten)
      editMssv.setText(mssv)
    }

    setResult(Activity.RESULT_CANCELED)

    findViewById<Button>(R.id.button_ok).setOnClickListener {
      val updatedHoten = editHoten.text.toString()
      val updatedMssv = editMssv.text.toString()

      val resultIntent = Intent()

      // Nếu đang sửa, cập nhật sinh viên cũ
      if (studentPosition != -1) {
        resultIntent.putExtra("position", studentPosition)
        resultIntent.putExtra("hoten", updatedHoten)
        resultIntent.putExtra("mssv", updatedMssv)
      } else {
        // Nếu là thêm mới, tạo sinh viên mới
        resultIntent.putExtra("hoten", updatedHoten)
        resultIntent.putExtra("mssv", updatedMssv)
      }

      setResult(Activity.RESULT_OK, resultIntent) // Trả lại kết quả
      finish() // Đóng activity
    }

    findViewById<Button>(R.id.button_cancel).setOnClickListener {
      setResult(Activity.RESULT_CANCELED)
      finish()
    }
  }
}


