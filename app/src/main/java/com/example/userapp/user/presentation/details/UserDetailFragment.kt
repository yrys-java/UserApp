package com.example.userapp.user.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.userapp.R
import com.example.userapp.core.ui.bundle.args
import com.example.userapp.core.ui.bundle.withArgs
import com.example.userapp.core.ui.navigation.popBackStack
import com.example.userapp.core.ui.view.binding.viewBinding
import com.example.userapp.databinding.FragmentUserDetailsBinding
import com.example.userapp.user.domain.model.UserInfo

class UserDetailFragment : Fragment(R.layout.fragment_user_details) {

    companion object {
        const val USER_INFO_KEY = "USER_INFO_KEY"

        fun create(user: UserInfo): UserDetailFragment = UserDetailFragment()
            .withArgs(USER_INFO_KEY to user)
    }

    private val binding: FragmentUserDetailsBinding by viewBinding()
    private val userInfo: UserInfo by args(USER_INFO_KEY)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.setNavigationOnClickListener {
                popBackStack()
            }

            // Home address
            textViewName.text = userInfo.name
            textViewCity.text = userInfo.address.city
            textViewZipCode.text = userInfo.address.zipcode
            textViewStreet.text = userInfo.address.street

            // Contacts
            textViewEmail.text = userInfo.email
            textViewPhoneNumber.text = userInfo.phone
            textViewWebsite.text = userInfo.website

            // About company
            textViewCompanyName.text = userInfo.company.name
            textViewCatchPhrase.text = userInfo.company.catchPhrase
            textViewBS.text = userInfo.company.bs
        }
    }
}