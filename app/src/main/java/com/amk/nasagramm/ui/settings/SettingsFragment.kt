package com.amk.nasagramm.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.amk.nasagramm.R
import com.amk.nasagramm.ui.SettingManager
import com.amk.nasagramm.ui.Theme
import com.google.android.material.bottomsheet.BottomSheetDialog

class SettingsFragment : Fragment() {

    private lateinit var settingManagers: SettingManager
    private lateinit var textViewThemeStatus: TextView
    private lateinit var textViewThemeHeader: TextView
    private lateinit var dialogSelectTheme: BottomSheetDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingManagers = SettingManager(view.context)
        textViewThemeStatus = view.findViewById(R.id.text_view_theme_status)
        textViewThemeHeader = view.findViewById(R.id.text_view_theme_header)
        when (settingManagers.getTheme()) {
            Theme.Day -> textViewThemeStatus.text = "Выключена"
            Theme.Night -> textViewThemeStatus.text = "Включена"
            Theme.System -> textViewThemeStatus.text = "Системная"
        }
        textViewThemeHeader.setOnClickListener {
            selectThemeDialog(view)
        }
        textViewThemeStatus.setOnClickListener {
            selectThemeDialog(view)
        }
    }

    private fun selectThemeDialog(view: View) {
        dialogSelectTheme = BottomSheetDialog(view.context)
        dialogSelectTheme.setContentView(R.layout.dialog_select_theme)
        dialogSelectTheme.show()

        val textViewSelectDay = dialogSelectTheme.findViewById<TextView>(R.id.text_view_off)
        val textViewSelectNight = dialogSelectTheme.findViewById<TextView>(R.id.text_view_on)
        val textViewSelectSystem = dialogSelectTheme.findViewById<TextView>(R.id.text_view_system)

        textViewSelectDay?.setOnClickListener {
            saveTheme(Theme.Day)
        }
        textViewSelectNight?.setOnClickListener {
            saveTheme(Theme.Night)
        }
        textViewSelectSystem?.setOnClickListener {
            saveTheme(Theme.System)
        }
    }

    private fun saveTheme(theme: Theme) {
        settingManagers.saveTheme(theme = theme)
        dialogSelectTheme.dismiss()
    }
}
