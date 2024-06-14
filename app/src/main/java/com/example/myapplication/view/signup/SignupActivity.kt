package com.example.myapplication.view.signup

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.viewmodel.UserViewModel
import com.example.storyapp.view.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: UserViewModel by viewModels {
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

            if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
                binding.emailEditTextLayout.error = getString(R.string.error_email)
            } else {
                binding.emailEditTextLayout.error = null
            }

            if (password.isEmpty() || password.length < 8) {
                binding.passwordEditTextLayout.error = getString(R.string.error_password)
            } else {
                binding.passwordEditTextLayout.error = null
            }

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && email.contains("@") && email.contains(".") && password.length >= 8) {
                viewModel.register(name, email, password, {
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Akun dengan $email sudah jadi nih.")
                        setPositiveButton("Lanjut") { _, _ ->
                            finish()
                        }
                        create()
                        show()
                    }
                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}
