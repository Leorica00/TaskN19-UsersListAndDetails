package com.example.taskn19.presentation.users

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskn19.data.common.Resource
import com.example.taskn19.databinding.FragmentUsersBinding
import com.example.taskn19.presentation.BaseFragment
import com.example.taskn19.presentation.model.User
import com.example.taskn19.presentation.users.adapter.UsersRecyclerViewAdapter
import com.example.taskn19.presentation.users.event.UsersEvent
import com.example.taskn19.presentation.users.listener.UserItemClickListener
import com.example.taskn19.presentation.users.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate),
    UserItemClickListener {

    private val usersViewModel: UsersViewModel by viewModels()
    private var usersAdapter = UsersRecyclerViewAdapter(this@UsersFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersViewModel.onEvent(UsersEvent.FetchData)
    }

    override fun setUp() {
        with(binding.usersRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }
    }

    override fun setUpListeners() {
        with(binding) {
            btnRetry.setOnClickListener {
                usersViewModel.onEvent(UsersEvent.FetchData)
                changeVisibility(btnRetry, tvErrorMessage)
            }
        }

        deleteSelectedUsers()

        onSwipeRefreshListener()
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            usersViewModel.usersStateFlow.collect {
                handleResource(it)
            }
        }
    }

    private fun handleResource(resource: Resource<List<User>>) {
        when (resource) {
            is Resource.Loading -> binding.progressBarUsers.isVisible = resource.loading
            is Resource.Success -> {
                with(binding) {
                    imageButtonDeleteUsers.isVisible = usersViewModel.onEvent(UsersEvent.CheckIfAnyUserIsChecked)
                    progressBarUsers.isVisible = false
                }
                usersAdapter.submitList(resource.successData)
                binding.swipeRefreshLayoutContainer.isRefreshing = false
            }
            is Resource.Error -> {
                with(binding) {
                    changeVisibility(tvErrorMessage, btnRetry)
                    tvErrorMessage.text = resource.errorMessage
                    binding.swipeRefreshLayoutContainer.isRefreshing = false
                }
            }
        }
    }

    private fun deleteSelectedUsers() {
        binding.imageButtonDeleteUsers.setOnClickListener {
            usersViewModel.onEvent(UsersEvent.DeleteSelectedUsers)
        }
    }

    private fun onSwipeRefreshListener() {
        binding.swipeRefreshLayoutContainer.setOnRefreshListener {
            usersViewModel.onEvent(UsersEvent.FetchData)
        }
    }

    override fun onUserItemClick(userId: Int) {
        findNavController().navigate(
            UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(
                userId = userId
            )
        )
    }

    override fun onUserItemLongClick(userId: Int) {
        usersViewModel.onEvent(UsersEvent.SelectUser(userId))
    }
}