package com.example.myapplication.view.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySignupBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.data.pref.UserModel
import com.example.myapplication.view.ViewModelFactory
import com.example.myapplication.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        viewModel.registrationResult.observe(this, Observer { result ->
            showLoading(false)
            if (result != null && !result.error) {
                viewModel.saveSession(UserModel(binding.emailEditText.text.toString(), "sample_token"))
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Akun dengan ${binding.emailEditText.text} sudah jadi nih.")
                    setPositiveButton("Lanjut") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Error")
                    setMessage(result?.message ?: "Registration failed. Please try again.")
                    setPositiveButton("OK", null)
                    create()
                    show()
                }
            }
        })
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (isInputValid(name, email, password)) {
                showLoading(true)
                viewModel.register(name, email, password)
            }
        }
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun isInputValid(name: String, email: String, password: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.nameEditTextLayout.error = getString(R.string.error)
            isValid = false
        } else {
            binding.nameEditTextLayout.error = null
        }

        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            binding.emailEditTextLayout.error = getString(R.string.error_email)
            isValid = false
        } else {
            binding.emailEditTextLayout.error = null
        }

        if (password.isEmpty() || password.length < 8) {
            binding.passwordEditTextLayout.error = getString(R.string.error_password)
            isValid = false
        } else {
            binding.passwordEditTextLayout.error = null
        }

        return isValid
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
