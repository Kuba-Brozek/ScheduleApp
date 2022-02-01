package ayathe.project.scheduleapp.registerlogin.activityreglog

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ayathe.project.scheduleapp.R
import ayathe.project.scheduleapp.home.secondfragment.SecondFragment
import ayathe.project.scheduleapp.home.thirdfragment.ThirdFragment
import ayathe.project.scheduleapp.home.homefragment.HomeFragment
import ayathe.project.scheduleapp.registerlogin.register.RegisterFragment
import ayathe.project.scheduleapp.registerlogin.login.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class LogRegActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val secondFragment = SecondFragment()
    private val thirdFragment = ThirdFragment()
    private val loginFragment = LoginFragment()
    private val registerFragment = RegisterFragment()
    private lateinit var mainActivityVm: ViewModelMainActivity
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityVm = ViewModelProvider(this)[ViewModelMainActivity::class.java]
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentsReplacement(registerFragment)
        auth = FirebaseAuth.getInstance()
        bottom_nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> fragmentsReplacement(loginFragment)
                R.id.home2 -> fragmentsReplacement(registerFragment)
            }
            true
        }
    }

    private fun fragmentsReplacement(fragment: Fragment){
        val fragmentContainer = supportFragmentManager.beginTransaction()
        fragmentContainer.replace(R.id.container, fragment)
        fragmentContainer.commit()
    }

    fun userCreation(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    fragmentsReplacement(homeFragment)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed. $password, $password",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun userSignIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    fragmentsReplacement(homeFragment)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }







}