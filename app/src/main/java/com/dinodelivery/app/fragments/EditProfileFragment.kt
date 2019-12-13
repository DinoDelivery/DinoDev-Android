package com.dinodelivery.app.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.StringSignature
import com.dinodelivery.app.viewmodels.EditProfileViewModel
import com.dinodelivery.app.R
import com.dinodelivery.app.entities.UserProfileData
import com.dinodelivery.app.utils.ImageUtils
import com.dinodelivery.app.utils.UserCacheUtils
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_profile.toolbarHeader
import java.io.File


class EditProfileFragment : Fragment() {

    private var fileUri: Uri? = null

    private var photo: String? = null

    private var photoUrl: String? = null

    private var firstStoragePermission = true
    private var firstCameraPermission = true

    private val editProfileViewModel: EditProfileViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(EditProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            isFocusableInTouchMode = true
            requestFocus()
            setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                    fragmentManager?.popBackStack()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        toolbarHeader.text = getString(R.string.profile)
        setInitialData()
        initListeners()
    }

    private fun setInitialData() {
        UserCacheUtils.cachedUserData?.let {
            fUserName.setText(it.username)
            fPhone.setText(it.phone)
            fCardNumber.setText(it.card)
            it.photo?.let { photoUrl ->
                val image = ImageUtils.decodeBase64(photoUrl)
                imgUserPhoto.setImageBitmap(image)
            }
        }
    }

    private fun initListeners() {
        btnSave.setOnClickListener {
            UserCacheUtils.cachedUserData =
                UserProfileData(
                    fUserName.text.toString(),
                    fPhone.text.toString(),
                    fCardNumber.text.toString(),
                    photoUrl
                )
            fragmentManager?.popBackStack()
        }
        imgUserPhoto.setOnClickListener { editPhoto() }
    }

    private fun editPhoto() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.adding_photo))
            setMessage(getString(R.string.choose_photo_source))
            setPositiveButton(getString(R.string.gallery)) { dialog, which -> selectGallery() }
            setNegativeButton(R.string.camera) { dialog, which ->
                selectCamera()
                dialog.dismiss()
            }
            show()
        }
    }


    private fun selectGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!firstStoragePermission && !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(
                    context,
                    R.string.report_external_storage_never_ask_again_issue,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE_PERMISSION
                )
            }
        } else {
            getPhotoFromGallery()
        }
    }

    private fun selectCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!firstCameraPermission && !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(
                    context,
                    R.string.report_camera_never_ask_again_issue,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
            }
        } else {
            getPhotoFromCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhotoFromGallery()
                } else {
                    Toast.makeText(
                        context,
                        R.string.report_external_storage_request_denied,
                        Toast.LENGTH_LONG
                    ).show()
                }
                firstStoragePermission = false
            }
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPhotoFromCamera()
                } else {
                    Toast.makeText(
                        context,
                        R.string.report_camera_request_denied,
                        Toast.LENGTH_LONG
                    ).show()
                }
                firstCameraPermission = false
            }
        }
    }

    private fun getPhotoFromGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        startActivityForResult(intent,
            REQUEST_STORAGE
        )
    }

    private fun getPhotoFromCamera() {
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        fileUri = editProfileViewModel.getCacheImagePath(fileName)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        startActivityForResult(intent,
            REQUEST_CAMERA
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_STORAGE -> {
                    if (data != null) {
                        val selectedImage = data.data
                        cropImage(selectedImage)
                    } else {
                        Toast.makeText(
                            context,
                            R.string.toast_error_please_try_again,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                REQUEST_CAMERA -> {
                    try {
                        cropImage(fileUri)
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            R.string.toast_error_please_try_again,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, e.message)
                    }

                }
                UCrop.REQUEST_CROP -> {
                    if (data != null) {
                        try {
                            photo = UCrop.getOutput(data)?.toString()
                            Glide.with(context)
                                .load(photo)
                                .placeholder(R.drawable.photo_placeholder)
                                .error(R.drawable.photo_placeholder)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .signature(StringSignature(photo))
                                .dontAnimate()
                                .into(imgUserPhoto)
                            photoUrl = ImageUtils.encodeToBase64(UCrop.getOutput(data)!!)
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                R.string.toast_error_please_try_again,
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d(TAG, e.message)
                        }

                    } else {
                        Toast.makeText(
                            context,
                            R.string.toast_error_please_try_again,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                UCrop.RESULT_ERROR -> {
                    val cropError = UCrop.getError(data!!)
                    Log.e(TAG, "Crop error: " + cropError!!)
                    Toast.makeText(
                        context,
                        R.string.toast_error_please_try_again,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(
                        context,
                        R.string.toast_error_please_try_again,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun cropImage(sourceUri: Uri?) {
        val destinationUri = Uri.fromFile(
            File(
                context!!.externalCacheDir,
                editProfileViewModel.queryName(context!!.contentResolver, sourceUri!!)
            )
        )
        val options = UCrop.Options()
        options.setCompressionQuality(50)
        options.withAspectRatio(1f, 1f)
        options.setToolbarTitle(getString(R.string.editing_photo))
        UCrop.of(sourceUri, destinationUri)
            .withOptions(options)
            .start(requireContext(), this@EditProfileFragment)
    }

    companion object {
        private val TAG = EditProfileFragment::class.java.simpleName
        private const val REQUEST_CAMERA = 111
        private const val REQUEST_STORAGE = 122
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 2
    }
}
