package com.example.taskn19.presentation.user_details

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.taskn19.data.common.Resource
import com.example.taskn19.databinding.FragmentUserDetailsBinding
import com.example.taskn19.domain.model.User
import com.example.taskn19.presentation.BaseFragment
import com.example.taskn19.presentation.user_details.event.UserDetailsEvent
import com.example.taskn19.presentation.user_details.viewmodel.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailsFragment :
    BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {

    private val userDetailsFragmentArgs: UserDetailsFragmentArgs by navArgs()
    private val userDetailsViewModel: UserDetailsViewModel by viewModels()

    override fun setUp() {
        userDetailsViewModel.onEvent(UserDetailsEvent.GetUserDetailsEvent(userDetailsFragmentArgs.userId))
    }

    override fun setUpListeners() {
        with(binding) {
            btnRetry.setOnClickListener {
                userDetailsViewModel.onEvent(UserDetailsEvent.GetUserDetailsEvent(userDetailsFragmentArgs.userId))
                changeVisibility(btnRetry, tvErrorMessage)
            }

            imageButtonGoBackToUsers.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDetailsViewModel.userDetailsStateFlow.collect {
                    handleResource(it)
                }
            }
        }
    }

    private fun handleResource(resource: Resource<User>) {
        when(resource) {
            is Resource.Loading -> binding.progressBarUserDetails.isVisible = resource.loading
            is Resource.Success -> {
                makeTextViewsVisible()
                resource.successData?.let { setUpUserDataOnTextViews(it) }
            }
            is Resource.Error -> {
                with(binding) {
                    changeVisibility(tvErrorMessage, btnRetry)
                    tvErrorMessage.text = resource.errorMessage
                }
            }
        }
    }

    private fun setUpUserDataOnTextViews(user: User) {
        with(binding) {
            tvNameUser.text = user.firstName
            tvLastNameUser.text = user.lastName
            tvEmailUser.text = user.email
            Glide.with(requireContext()).load(user.avatar).into(imageViewAvatar)
        }
    }

    private fun makeTextViewsVisible() {
        with(binding) {
            tvNameText.visibility = View.VISIBLE
            tvLastNameText.visibility = View.VISIBLE
            tvEmailText.visibility = View.VISIBLE

        }
    }
}