package com.dinodelivery.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dinodelivery.app.MainActivity
import com.dinodelivery.app.R
import com.dinodelivery.app.utils.ImageUtils
import com.dinodelivery.app.utils.UserCacheUtils
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarHeader.text = getString(R.string.profile)
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        setDefaultData()
        setInitialData()
    }

    private fun setDefaultData() {
        txtUserName.text = getString(R.string.name, "")
        txtPhone.text = getString(R.string.phone_template, "")
        txtCardNumber.text = getString(R.string.card, "")
    }

    private fun setInitialData() {
        UserCacheUtils.cachedUserData?.let {
            txtUserName.text = getString(R.string.name, it.username)
            txtPhone.text = getString(R.string.phone_template, it.phone)
            txtCardNumber.text = getString(R.string.card, it.card)
            it.photo?.let {photo ->
                val image = ImageUtils.decodeBase64(photo)
                imgUserPhoto.setImageBitmap(image)
            }
        }
    }

    private fun initListeners() {
        btnEditProfile.setOnClickListener {
            (requireActivity() as MainActivity).navigateToFragmentAndAddToStack(EditProfileFragment())
        }

        btnOrder.setOnClickListener {
            (requireActivity() as MainActivity).navigateToFragmentAndAddToStack(OrderFragment())
        }
    }
}