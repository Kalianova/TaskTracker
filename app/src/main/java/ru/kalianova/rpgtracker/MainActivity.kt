package ru.kalianova.rpgtracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ru.kalianova.rpgtracker.databinding.ActivityMainBinding

import com.snappydb.SnappydbException

import com.snappydb.DB
import ru.kalianova.rpgtracker.db.ObjectBox
import com.snappydb.DBFactory
import org.w3c.dom.Text


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var dbReference: DatabaseReference
    private val SETTINGS_NAME: String = "profile_settings"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser

        dbReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser!!.uid)


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    fun clickLogout(view: View) {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun clickAddTask(view: View) {
        startActivity(Intent(this, TaskActivity::class.java))
    }

    fun clickAddTaskType(view: View) {
        startActivity(Intent(this, TaskTypeActivity::class.java))
    }


}