package au.com.machtech.ttf_stock_check

// (c) Copyright Skillage I.T.
// (c) This file is Skillage I.T. software and is made available under license.
// (c) This software is developed by: Joshua Panettieri
// (c) Date: 15th October 2022 Time: 08:00
// (c) File Name: TTF_Stock_Check Version: 1-0


import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import au.com.machtech.ttf_stock_check.R.*
import au.com.machtech.ttf_stock_check.database.ProductsDbHelper
import kotlin.math.roundToInt
import kotlin.system.exitProcess



class SettingsFragment : Fragment(layout.fragment_settings) {
    private lateinit var productsDbHelper: ProductsDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //init and set variables
        val view = inflater.inflate(layout.fragment_settings, container, false)

        //Getting Current screen brightness.
        val cBrightness =
            Settings.System.getInt(context?.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0).toFloat()
        // Set current screen brightness value in the text view.
        val setBrightness = ((cBrightness/255) * 100).roundToInt()
        view.findViewById<TextView>(R.id.brightnessCurrent).text = setBrightness.toString()
        // Set current screen brightness value to seekbar progress.
        view.findViewById<SeekBar>(R.id.brightnessBar).progress = setBrightness
        //Set Brightness seek bar
        view.findViewById<SeekBar>(R.id.brightnessBar).setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                brightnessSeekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean,
            ) {
                view.findViewById<TextView>(R.id.brightnessCurrent).text = progress.toString()
                val context = requireActivity().applicationContext
                val canWriteSettings = Settings.System.canWrite(context)
                if (canWriteSettings) {
                    // Convert screen brightness to percentage
                    val screenBrightnessValue = ((progress.toFloat()) * 255 / 100).roundToInt()

                    // Set seekbar adjust screen brightness value in the text view.
                    view.findViewById<TextView>(R.id.brightnessCurrent).text =
                        (screenBrightnessValue.toFloat() / 2.55).roundToInt().toString()
                    // Change the screen brightness change mode to manual.
                    Settings.System.putInt(
                        context.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                    )
                    // Apply the screen brightness value to the system
                    Settings.System.putInt(
                        context.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS,
                        screenBrightnessValue
                    )
                } else {
                    // Show Can modify system settings panel to request system permission
                    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }

            override fun onStartTrackingTouch(brightnessSeekBar: SeekBar?) {}
            override fun onStopTrackingTouch(brightnessSeekBar: SeekBar?) {}
        })

        //Font Size setting
        view.findViewById<Button>(R.id.defaultFontBtn).setOnClickListener {
            val size = 1
            fontChangeWarning(size)}
        view.findViewById<Button>(R.id.smallFontBtn).setOnClickListener {
            val size = 2
            fontChangeWarning(size)}
        view.findViewById<Button>(R.id.largeFontBtn).setOnClickListener {
            val size = 3
            fontChangeWarning(size)}

        //set button sounds
        view.findViewById<ToggleButton>(R.id.soundToggleBtn).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AudioManager.ADJUST_UNMUTE
            } else {
                AudioManager.ADJUST_MUTE
            }
        }

        view.findViewById<Button>(R.id.manageUsers).setOnClickListener {
            val editUser = arrayOf("", "","")
            val action = SettingsFragmentDirections.actionSettingsFragmentToUsersFragment(editUser)
            requireActivity().findNavController(R.id.manageUsers).navigate(action)
        }
        view.findViewById<Button>(R.id.addUsers).setOnClickListener {
            val editUser = arrayOf("", "","")
            val action = SettingsFragmentDirections.actionSettingsFragmentToManageUsersFragment(editUser)
            requireActivity().findNavController(R.id.addUsers).navigate(action)
        }

        view.findViewById<Button>(R.id.newDb).setOnClickListener {
            val context = requireActivity().applicationContext
            productsDbHelper = ProductsDbHelper(context)
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Are you sure you want to delete Database and Reload?")
            builder.setCancelable(true)
            builder.setPositiveButton("Yes") { dialog, _ ->
                productsDbHelper.clearDbAndRecreate()
                restartApp()
                dialog.dismiss()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alert = builder.create()
            alert.show()
        }

        return view
    }

    private fun changeFont(size: Int) {
        when (size) {
            1 -> {
                Settings.System.putFloat(
                    requireActivity().applicationContext.contentResolver,
                    Settings.System.FONT_SCALE, 1.0f
                )
            }
            2 -> {
                Settings.System.putFloat(
                    requireActivity().applicationContext.contentResolver,
                    Settings.System.FONT_SCALE, 0.85f
                )
            }
            3 -> {
                Settings.System.putFloat(
                    requireActivity().applicationContext.contentResolver,
                    Settings.System.FONT_SCALE, 1.15f
                )
            }
        }
    }

    private fun restartApp() {
        val intent = Intent(
            requireActivity().applicationContext,
            MainActivity::class.java
        )
        val mPendingIntentId = 1
        val mPendingIntent = PendingIntent.getActivity(
            requireActivity().applicationContext,
            mPendingIntentId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val mgr = requireActivity().applicationContext
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr[AlarmManager.RTC, System.currentTimeMillis() + 100] = mPendingIntent
        exitProcess(0)
    }

    private fun fontChangeWarning(size: Int) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Application will require restart, continue?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { _, _ ->
            changeFont(size)
            restartApp()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }
}





