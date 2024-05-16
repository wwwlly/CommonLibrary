package com.github.wwwlly.navigation

import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment


class NavHostFragmentHideShow : NavHostFragment() {

    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination?> {
        return FragmentNavigatorHideShow(requireContext(), childFragmentManager, id)
    }

}
