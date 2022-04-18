package ru.kalianova.rpgtracker.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.snappydb.DB
import com.snappydb.DBFactory
import com.snappydb.SnappydbException
import ru.kalianova.rpgtracker.R
import ru.kalianova.rpgtracker.databinding.FragmentHomeBinding
import ru.kalianova.rpgtracker.databinding.FragmentProfileBinding
import ru.kalianova.rpgtracker.ui.home.HomeViewModel

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textProfile
        profileViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        try {
            val snappyDB = DBFactory.open(context, "User")
            setValues(snappyDB)
        } catch (e: SnappydbException) {
            e.printStackTrace()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Throws(SnappydbException::class)
    private fun setValues(snappyDB: DB) {
        val textProfile = binding.textProfile
        val login = snappyDB["Login"]
        val email = snappyDB["Email"]
        profileViewModel.text.value = "Login: $login Email: $email"
        /* val name = snappyDB["Name"]
        val budget = snappyDB.getInt("budget")
        val isAdult = snappyDB.getBoolean("isAdult")
        val genres = snappyDB.getObjectArray(
            "genres",
            String::class.java
        ) */
    }

}