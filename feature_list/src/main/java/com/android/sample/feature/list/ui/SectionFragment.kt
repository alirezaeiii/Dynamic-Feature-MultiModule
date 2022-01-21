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
import com.android.sample.feature.list.databinding.FragmentSectionBinding
import com.android.sample.feature.list.di.DaggerSectionComponent
import com.android.sample.feature.list.di.SectionModule
import com.android.sample.feature.list.viewmodel.SectionViewModel
import com.android.sample.app.Application

class SectionFragment : BaseFragment<SectionViewModel, FragmentSectionBinding>
    (R.layout.fragment_section, BR.vm) {
    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerSectionComponent
            .builder()
            .coreComponent(Application.coreComponent(requireContext()))
            .sectionModule(SectionModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        with(binding) {

            viewModel.liveData.observe(viewLifecycleOwner, { resource ->
                if (resource is ViewState.Success) {
                    textTitle.text = resource.data?.title
                    textDescription.text = resource.data?.description
                }
            })

            toolbar.apply {
                setNavigationOnClickListener { findNavController().navigateUp() }
            }
        }

        return binding.root
    }
}