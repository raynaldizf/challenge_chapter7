package com.example.challenge_chapter6.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.asLiveData
import com.example.challenge_chapter6.PreferencesLogin
import com.example.challenge_chapter6.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityProfileBinding
    lateinit var sharedPrefs : PreferencesLogin
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }
    var imageUri: Uri? = Uri.EMPTY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPrefs = PreferencesLogin(this)

        var image = BitmapFactory.decodeFile(this.applicationContext.filesDir.path + File.separator +"files"+ File.separator +"profile.png")
        binding.btnImage.setImageBitmap(image)

        binding.btnLogout.setOnClickListener{
            GlobalScope.launch {
                PreferencesLogin(this@ProfileActivity).clearData()
                startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                finish()
            }
        }
        sharedPrefs.userName.asLiveData().observe(this,{
            binding.etUsername.setText(it).toString()
        })
        sharedPrefs.userEmail.asLiveData().observe(this,{
            binding.etEmail.setText(it).toString()
        })
        sharedPrefs.userAlamat.asLiveData().observe(this,{
            binding.etAlamat.setText(it).toString()
        })
        binding.btnUpdate.setOnClickListener{
            GlobalScope.launch {
                PreferencesLogin(this@ProfileActivity).updateProfile(
                    binding.etUsername.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.etAlamat.text.toString()
                )
                Toast.makeText(this@ProfileActivity,"Data Berhasil Diupdate",Toast.LENGTH_SHORT).show()
            }

            val contentResolver = this.applicationContext.contentResolver
            val picture = BitmapFactory.decodeStream(
                contentResolver.openInputStream(Uri.parse(imageUri.toString())))
            setImageToView(this,picture)
        }
        binding.btnImage.setOnClickListener{
            checkingPermissions()
        }
    }

    fun setImageToView(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = "profile.png"
        val outputDir = File(applicationContext.filesDir, "files")
        if (!outputDir.exists()) {
            outputDir.mkdirs() // should succeed
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (ignore: IOException) {
                }

            }
        }
        return Uri.fromFile(outputFile)
    }

    private fun checkingPermissions() {
        if (isGranted(
                this,
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                IMAGE_REQUEST_CODE,
            )
        ) {
            openGallery()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun openGallery() {
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            imageUri = result
            binding.btnImage.setImageURI(result)
        }
}