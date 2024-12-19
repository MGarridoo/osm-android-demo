package com.example.osmdemo.shared

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.osmdemo.databinding.FragmentDateTimePickerBinding
import com.example.osmdemo.features.trip_planner.presentation.TripPlannerViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FragmentDateTimePicker : DialogFragment() {

    private var _binding: FragmentDateTimePickerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TripPlannerViewModel by activityViewModels()

    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Bogota"))
    private var isDeparture = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDateTimePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()
        setupTabLayout()
        setupActionButtons()
        setupDateNavigation()
        initAllPickers()
        updateDateDisplay()

        // Botón "OK"
        binding.btnOk.setOnClickListener { onOkButtonClicked() }

        // Botón "Cancel"
        binding.btnCancel.setOnClickListener { dismiss() }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                state.selectedDateTime?.let {
                    calendar.time = it.time
                    updatePickersWithTime(calendar)
                }
                isDeparture = state.isDeparture
            }
        }
    }

    private fun setupTabLayout() {
        val selectedTab = if (isDeparture) 0 else 1

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Salida"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Llegada"))

        if (binding.tabLayout.selectedTabPosition != selectedTab) {
            binding.tabLayout.getTabAt(selectedTab)?.select()
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                isDeparture = tab?.position == 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupActionButtons() {
        binding.btnNow.setOnClickListener {
            calendar.timeInMillis = System.currentTimeMillis()
            updateDateDisplay()
            updatePickersWithTime(calendar)
        }

        binding.btnIn15Min.setOnClickListener {
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.MINUTE, 15)
            updateDateDisplay()
            updatePickersWithTime(calendar)
        }

        binding.btnIn1Hour.setOnClickListener {
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.HOUR, 1)
            updateDateDisplay()
            updatePickersWithTime(calendar)
        }
    }

    private fun setupDateNavigation() {
        // Botón para Día Anterior
        binding.btnPreviousDay.setOnClickListener {
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            updateDateDisplay()
            updatePickersWithTime(calendar)
        }

        // Botón para Día Siguiente
        binding.btnNextDay.setOnClickListener {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            updateDateDisplay()
            updatePickersWithTime(calendar)
        }

        // Al hacer clic en el TextView de la fecha, abrir el selector de fechas
        binding.tvDate.setOnClickListener {
            openDatePicker()
        }
    }

    private fun openDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            calendar.set(Calendar.YEAR, selectedYear)
            calendar.set(Calendar.MONTH, selectedMonth)
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
            updateDateDisplay()
        }, year, month, day)

        datePicker.show()
    }

    private fun onOkButtonClicked() {
        val hour = binding.hourPicker.value
        val minute = binding.minutePicker.value
        val amPm = binding.ampmPicker.value

        calendar.set(Calendar.HOUR, if (hour == 12) 0 else hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.AM_PM, amPm)

        viewModel.updateDateTime(calendar, isDeparture)
        dismiss()
    }

    private fun initAllPickers() {
        val amPmValues = arrayOf("AM", "PM")

        initPicker(1, 12, binding.hourPicker)
        initPicker(0, 59, binding.minutePicker)
        initPickerWithString(0, amPmValues.size - 1, binding.ampmPicker, amPmValues)

        updatePickersWithTime(calendar)
    }

    private fun initPicker(min: Int, max: Int, picker: NumberPicker) {
        picker.minValue = min
        picker.maxValue = max
        picker.wrapSelectorWheel = true
        picker.setFormatter { i -> String.format("%02d", i) }
    }

    private fun initPickerWithString(min: Int, max: Int, picker: NumberPicker, displayedValues: Array<String>) {
        picker.minValue = min
        picker.maxValue = max
        picker.displayedValues = displayedValues
        picker.wrapSelectorWheel = true
    }

    private fun updateDateDisplay() {
        val dateFormat = SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault())
        binding.tvDate.text = dateFormat.format(calendar.time)
    }

    private fun updatePickersWithTime(calendar: Calendar) {
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val amPm = calendar.get(Calendar.AM_PM)

        binding.hourPicker.value = if (hour == 0) 12 else hour
        binding.minutePicker.value = minute
        binding.ampmPicker.value = amPm
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
