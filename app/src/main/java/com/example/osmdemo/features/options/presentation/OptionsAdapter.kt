package com.example.osmdemo.features.options.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.osmdemo.R
import com.example.osmdemo.databinding.ItemSectionHeaderBinding
import com.example.osmdemo.databinding.ItemSectionOptionBinding
import com.example.osmdemo.features.options.domain.OptionItem
import com.example.osmdemo.features.options.domain.OptionType
import com.example.osmdemo.features.options.domain.SectionItem

class OptionsAdapter(
    private val onOptionUpdated: (sectionTitle: String, optionTitle: String, newValue: Any?) -> Unit
) : ListAdapter<OptionsAdapter.ListItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_OPTION = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return when {
                    oldItem is ListItem.Header && newItem is ListItem.Header ->
                        oldItem.section.title == newItem.section.title
                    oldItem is ListItem.Option && newItem is ListItem.Option ->
                        oldItem.sectionTitle == newItem.sectionTitle &&
                                oldItem.option.optionTitle == newItem.option.optionTitle
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return when {
                    oldItem is ListItem.Header && newItem is ListItem.Header ->
                        oldItem.section == newItem.section
                    oldItem is ListItem.Option && newItem is ListItem.Option ->
                        oldItem.option == newItem.option
                    else -> false
                }
            }
        }
    }

    private val sections = mutableListOf<SectionItem>()

    private fun buildItems(): List<ListItem> {
        val items = mutableListOf<ListItem>()
        for (section in sections) {
            items.add(ListItem.Header(section))
            if (section.isExpanded) {
                section.options.forEach { items.add(ListItem.Option(it, section.title)) }
            }
        }
        return items
    }

    fun updateSections(newSections: List<SectionItem>) {
        sections.clear()
        sections.addAll(newSections)
        submitList(buildItems())
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.Header -> VIEW_TYPE_HEADER
            is ListItem.Option -> VIEW_TYPE_OPTION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val binding = ItemSectionHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SectionHeaderViewHolder(binding)
        } else {
            val binding = ItemSectionOptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SectionOptionViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ListItem.Header -> (holder as SectionHeaderViewHolder).bind(item.section)
            is ListItem.Option -> (holder as SectionOptionViewHolder).bind(item.option, item.sectionTitle)
        }
    }

    private fun toggleSection(section: SectionItem) {
        section.isExpanded = !section.isExpanded
        submitList(buildItems())
    }

    sealed class ListItem {
        data class Header(val section: SectionItem) : ListItem()
        data class Option(val option: OptionItem, val sectionTitle: String) : ListItem()
    }

    inner class SectionHeaderViewHolder(private val binding: ItemSectionHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position) as ListItem.Header
                    toggleSection(item.section)
                }
            }
        }

        fun bind(section: SectionItem) {
            binding.sectionTitle.text = section.title
            binding.expandIcon.setImageResource(
                if (section.isExpanded) R.drawable.haf_ic_expand else R.drawable.haf_ic_collapse
            )
        }
    }

    inner class SectionOptionViewHolder(private val binding: ItemSectionOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(
            binding.root.context,
            android.R.layout.simple_spinner_item,
            mutableListOf<String>()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        init {
            binding.optionSpinner.adapter = spinnerAdapter
        }

        fun bind(option: OptionItem, sectionTitle: String) {
            binding.optionTitle.text = option.optionTitle

            when (option.optionType) {
                OptionType.SWITCH -> {
                    binding.optionSwitch.visibility = View.VISIBLE
                    binding.optionSpinner.visibility = View.GONE

                    binding.optionSwitch.setOnCheckedChangeListener(null)
                    binding.optionSwitch.isChecked = option.selectedOption as? Boolean ?: false
                    binding.optionSwitch.setOnCheckedChangeListener { _, isChecked ->
                        onOptionUpdated(sectionTitle, option.optionTitle, isChecked)
                    }
                }

                OptionType.SPINNER -> {
                    binding.optionSwitch.visibility = View.GONE
                    binding.optionSpinner.visibility = View.VISIBLE

                    spinnerAdapter.clear()
                    val optionsAsStrings = option.spinnerOptions.map { it.toString() }
                    spinnerAdapter.addAll(optionsAsStrings)
                    spinnerAdapter.notifyDataSetChanged()

                    binding.optionSpinner.onItemSelectedListener = null

                    val defaultIndex = option.spinnerOptions.indexOf(option.selectedOption)
                    if (defaultIndex >= 0) binding.optionSpinner.setSelection(defaultIndex)

                    binding.optionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                        ) {
                            onOptionUpdated(sectionTitle, option.optionTitle, option.spinnerOptions[position])
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {}
                    }
                }
            }
        }
    }
}
