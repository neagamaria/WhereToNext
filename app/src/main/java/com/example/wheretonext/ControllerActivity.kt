package com.example.wheretonext

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment

class ControllerActivity : AppCompatActivity() {


    private val navController by lazy {
        val host = supportFragmentManager
            .findFragmentById(R.id.fcv_main)
                as? NavHostFragment
            ?: error("NavHostFragment not found in layout")
        host.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
      //  setupSplashScreen()

        super.onCreate(savedInstanceState)
        Log.d("ControllerActivity","On create called!")
        enableEdgeToEdge()
        setContentView(R.layout.activity_controller)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       // tryToAuthUser()
    }

//    private fun setupSplashScreen() {
//        val splashScreen = installSplashScreen()
//        splashScreen.setKeepOnScreenCondition {
//            !isAppInitialized
//        }
//        splashScreen.setOnExitAnimationListener {
//            it.remove()
//        }
//    }
//
//    private fun tryToAuthUser() {
//        lifecycleScope.launch {
//            delay(3000)
//
//            when (SharedPrefsManager.isUserLoggedIn()) {
//                true -> initHome()
//                false -> initAuthentication()
//            }
//
//            isAppInitialized = true
//        }
//    }
//
//    private fun initAuthentication() {
//        navController.setGraph(R.navigation.navigation_authentication)
//    }
//
//    private fun initHome() {
//        navController.setGraph(R.navigation.navigation_home)
//    }
}