package com.example.myapplication.view.signup

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.view.login.LoginViewModel
import androidx.activity.viewModels
import com.example.myapplication.data.pref.UserModel
import com.example.myapplication.view.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
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

            if (name.isEmpty()) {
                binding.nameEditTextLayout.error = getString(R.string.error)
            } else {
                binding.nameEditTextLayout.error = null
            }

            if (email.isEmpty() ||!email.contains("@") ||!email.contains(".")) {
                binding.emailEditTextLayout.error = getString(R.string.error_email)
            } else {
                binding.emailEditTextLayout.error = null
            }

            if (password.isEmpty() || password.length < 8) {
                binding.passwordEditTextLayout.error = getString(R.string.error_password)
            } else {
                binding.passwordEditTextLayout.error = null
            }

            if (!name.isEmpty() &&!email.isEmpty() &&!password.isEmpty() && email.contains("@") && email.contains(".") && password.length >= 8) {
                showLoading(true)
                viewModel.saveSession(UserModel(email, "sample_token"))
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Akun dengan $email sudah jadi nih.")
                    setPositiveButton("Lanjut") { _, _ ->
                        showLoading(false)
                        finish()
                    }
                    create()
                    show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
