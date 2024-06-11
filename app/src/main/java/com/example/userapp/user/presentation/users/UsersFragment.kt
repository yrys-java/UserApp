package com.example.userapp.user.presentation.users

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userapp.R
import com.example.userapp.core.ui.mvp.BaseMvpFragment
import com.example.userapp.core.ui.navigation.addFragment
import com.example.userapp.core.ui.view.attachAdapter
import com.example.userapp.core.ui.view.binding.viewBinding
import com.example.userapp.databinding.FragmentUsersBinding
import com.example.userapp.user.domain.model.UserInfo
import com.example.userapp.user.presentation.details.UserDetailFragment
import com.example.userapp.user.presentation.users.adapter.UsersAdapter
import org.koin.android.ext.android.inject

typealias IV = UsersContract.View
typealias IP = UsersContract.Presenter

class UsersFragment : BaseMvpFragment<IV, IP>(R.layout.fragment_users), IV {

    companion object {
        fun create(): UsersFragment = UsersFragment()
    }

    override val presenter: UsersContract.Presenter by inject()
    private val binding: FragmentUsersBinding by viewBinding()

    private val adapter: UsersAdapter by lazy {
        UsersAdapter(
            onItemClickListener = { user ->
                addFragment(target = UserDetailFragment.create(user))
            },
        )
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.attachAdapter(adapter)

            swipeRefresh.setOnRefreshListener {
                presenter.loadUsers(isRefresh = true)
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun showUsers(users: List<UserInfo>) {
        adapter.setItems(new = users)
    }

    override fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.recyclerView.isVisible = !isLoading
    }
}