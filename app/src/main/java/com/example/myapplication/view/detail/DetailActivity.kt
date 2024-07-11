package com.example.myapplication.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val date = intent.getStringExtra(EXTRA_DATE)
        val photoUrl = intent.getStringExtra(EXTRA_PHOTO_URL)

        binding.tvDetailName.text = name
        binding.tvDetailDescription.text = description
        binding.tvDetailDate.text = date
        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivDetailPhoto)


        binding.backButton.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_PHOTO_URL = "extra_photo_url"
    }
}
