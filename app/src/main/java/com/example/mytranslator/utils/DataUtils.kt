package com.example.mytranslator.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mytranslator.R

fun loadFragment(
    fragment: Fragment,
    fragmentTag: String,
    manager: FragmentManager,
    containerID: Int = R.id.main_activity_fragment_container,
    fragmentFlags: Int = Int.ZERO
) = run {
    val fragmentPopped = manager.popBackStackImmediate(fragmentTag, fragmentFlags)
    if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
        manager.apply {
            beginTransaction()
                .replace(containerID, fragment)
                .addToBackStack(fragmentTag)
                .commitAllowingStateLoss()
        }
    }
    true
}