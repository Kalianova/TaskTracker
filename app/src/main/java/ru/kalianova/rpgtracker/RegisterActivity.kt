package ru.kalianova.rpgtracker

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.snappydb.DBFactory
import com.snappydb.SnappydbException

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextLogin: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPasswordRepeat: EditText
    private lateinit var buttonRegister: Button
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        buttonRegister = findViewById(R.id.buttonRegisterLogin)
        editTextLogin = findViewById(R.id.editTextLogin)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextPasswordRepeat = findViewById(R.id.editTextPasswordRepeat)

        buttonRegister.setOnClickListener {
            buttonRegisterClicked()
        }
    }

    private fun buttonRegisterClicked() {
        val toast = Toast(this)
        val loginText: String = editTextLogin.text.trim().toString()
        val emailText: String = editTextEmail.text.trim().toString()
        val passwordText: String = editTextPassword.text.trim().toString()
        val passwordRepeatText: String = editTextPasswordRepeat.text.trim().toString()
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        if (loginText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || passwordRepeatText.isEmpty()) {
            toast.setText("???? ?????? ???????? ??????????????????")
            toast.show()
            return
        }
        if (passwordText != passwordRepeatText) {
            toast.setText("???????????? ???? ??????????????????")
            toast.show()
            return
        }
        auth.createUserWithEmailAndPassword(emailText, passwordText)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    database = FirebaseFirestore.getInstance()
                    database.collection("users").document(emailText)
                        .set(hashMapOf("name" to loginText))
                        .addOnSuccessListener {

                            toast.setText("?????????????????????? ???????????? ??????????????")
                            toast.show()
                            finish()
                        }


                } else {
                    var textToast: String
                    textToast = when (task.exception) {
                        is FirebaseAuthWeakPasswordException -> "???????????? ????????????. ?????????? 6 ????????????????"
                        is FirebaseAuthUserCollisionException -> "???????????????????? ???????????????????????? ?? ???????????? email ??????????????"
                        is FirebaseAuthInvalidCredentialsException -> "Email ?????????? ????????????????"
                        else -> "?????????????????????????????????? ???? ????????????????????"
                    }
                    toast.setText(textToast)
                    toast.show()
                }
            }
    }

    fun backClicked(view: View) {
        finish()
    }
}