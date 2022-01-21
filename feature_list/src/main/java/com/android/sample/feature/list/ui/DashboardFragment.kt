package com.android.sample.feature.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.sample.common.base.BaseFragment
import com.android.sample.common.util.ViewState
import com.android.sample.feature.list.BR
import com.android.sample.feature.list.R
import com.android.sample.feature.list.di.DaggerDashboardComponent
import com.android.sample.feature.list.di.DashboardModule
import com.android.sample.feature.list.ui.LinkAdapter.OnClickListener
import com.android.sample.feature.list.viewmodel.DashboardViewModel
import com.android.sample.app.Application.Companion.coreComponent
import com.android.sample.feature.list.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>
    (R.layout.fragment_dashboard, BR.vm) {

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerDashboardComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .dashboardModule(DashboardModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val viewModelAdapter =
            LinkAdapter(OnClickListener { link ->
                val destination =
                    DashboardFragmentDirections.actionDashboardFragmentToSectionFragment(link)
                with(findNavController()) {
                    currentDestination?.getAction(destination.actionId)
                        ?.let { navigate(destination) }
                }
            })

        viewModel.liveData.observe(viewLifecycleOwner, { resource ->
            if (resource is ViewState.Success) {
                viewModelAdapter.submitList(resource.data?.links?.sections)
            }
        })

        with(binding) {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = viewModelAdapter
        }

        return binding.root
    }
}