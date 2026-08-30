package com.tomfreestyle.panel

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MainActivity : Activity() {
    private lateinit var root: LinearLayout
    private var screen = Screen.LOGIN

    private enum class Screen { LOGIN, BLUE, SECRET, RED }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(7, 9, 13)
        window.navigationBarColor = Color.rgb(7, 9, 13)
        showLogin()
        requestNotificationPermissionIfNeeded()
    }

    override fun onResume() {
        super.onResume()
        if (screen == Screen.BLUE || screen == Screen.RED) updateFloatingButtonState()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 44)
        }
    }

    private fun base(): LinearLayout {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(14), dp(16), dp(18))
            setBackgroundColor(Color.rgb(7, 9, 13))
        }
        setContentView(root)
        return root
    }

    private fun showLogin() {
        screen = Screen.LOGIN
        val r = base()
        val space = Space(this)
        r.addView(space, LinearLayout.LayoutParams(1, 0, 0.22f))
        r.addView(avatar(118), centerParams())
        r.addView(text("TOM FREESTYLE", 24f, Color.WHITE, true), centerParams(top = 14))
        r.addView(text("SECURE ACCESS", 12f, Color.rgb(130, 143, 158), false), centerParams(top = 4))
        val pass = EditText(this).apply {
            hint = "Enter Access Code"
            hintTextColor = Color.rgb(125, 134, 146)
            setTextColor(Color.WHITE)
            textSize = 16f
            gravity = Gravity.CENTER
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            background = rounded(Color.rgb(18, 23, 31), 18, Color.rgb(37, 53, 70), 2)
            setPadding(dp(14), dp(14), dp(14), dp(14))
        }
        r.addView(pass, matchParams(top = 24, height = 58))
        val enter = button("ENTER", Color.rgb(21, 155, 255))
        r.addView(enter, matchParams(top = 12, height = 54))
        val status = text("", 12f, Color.rgb(255, 65, 78), true)
        r.addView(status, centerParams(top = 12))
        enter.setOnClickListener {
            if (pass.text.toString() == "98062") showBlue() else status.text = "Password Incorrect ❌"
        }
        r.addView(text("SIMULATION MODE • NO REAL FUNCTIONS", 10f, Color.rgb(75, 85, 98), false), centerParams(top = 18))
    }

    private fun showBlue() {
        screen = Screen.BLUE
        val r = base()
        val header = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL }
        header.addView(avatar(58))
        val titleBox = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(12), 0, 0, 0) }
        titleBox.addView(text("FREESTYLE", 20f, Color.WHITE, true))
        titleBox.addView(text("TOM CONTROL PANEL", 10f, Color.rgb(140, 175, 205), false))
        header.addView(titleBox, LinearLayout.LayoutParams(0, -2, 1f))
        val secret = button("↗", Color.rgb(21, 155, 255)).apply { textSize = 22f }
        header.addView(secret, LinearLayout.LayoutParams(dp(52), dp(52)))
        r.addView(header, matchParams(height = 64))

        val body = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.TOP }
        val menu = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        listOf("●  Main", "⚙  Settings", "◈  Ice", "✦  AI", "⚡  Optimize").forEachIndexed { i, label ->
            val b = button(label, if (i == 1) Color.rgb(17, 119, 205) else Color.rgb(19, 24, 31))
            menu.addView(b, matchParams(top = if (i == 0) 8 else 7, height = 52))
        }
        body.addView(menu, LinearLayout.LayoutParams(dp(128), -1))

        val panel = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(10), dp(8), 0, 0) }
        panel.addView(text("TRICK BUTTON", 15f, Color.rgb(120, 190, 255), true))
        val trick = switchRow("Enable Trick Button", true)
        panel.addView(trick, matchParams(top = 10, height = 56))
        panel.addView(text("STYLE", 13f, Color.WHITE, true), matchParams(top = 14))
        val styles = LinearLayout(this).apply { gravity = Gravity.CENTER }
        listOf("＋", "×", "◎", "◉", "◌").forEachIndexed { i, s ->
            val b = button(s, if (i == 0) Color.rgb(21, 155, 255) else Color.rgb(40, 45, 51))
            styles.addView(b, LinearLayout.LayoutParams(0, dp(48), 1f).apply { setMargins(dp(3), dp(8), dp(3), 0) })
        }
        panel.addView(styles)
        panel.addView(text("COLOR", 13f, Color.WHITE, true), matchParams(top = 14))
        val colors = LinearLayout(this).apply { gravity = Gravity.CENTER }
        listOf(Color.RED, Color.GREEN, Color.rgb(30, 150, 255), Color.MAGENTA, Color.YELLOW).forEach { c ->
            val b = View(this).apply { background = circle(c) }
            colors.addView(b, LinearLayout.LayoutParams(dp(38), dp(38)).apply { setMargins(dp(5), dp(8), dp(5), 0) })
        }
        panel.addView(colors)
        panel.addView(sliderRow("Size", 72), matchParams(top = 12, height = 48))
        panel.addView(sliderRow("Opacity", 96), matchParams(top = 2, height = 48))
        panel.addView(sliderRow("Stroke", 38), matchParams(top = 2, height = 48))
        val floatButton = button("⭕  FLOATING MODE", Color.rgb(21, 155, 255))
        panel.addView(floatButton, matchParams(top = 14, height = 52))
        r.addView(body, LinearLayout.LayoutParams(-1, 0, 1f))
        r.addView(text("SIMULATION MODE • VISUAL ONLY", 10f, Color.rgb(80, 90, 105), false), centerParams(top = 8))

        secret.setOnClickListener { showSecret() }
        floatButton.setOnClickListener { enableFloating() }
    }

    private fun showSecret() {
        screen = Screen.SECRET
        val r = base()
        r.addView(Space(this), LinearLayout.LayoutParams(1, 0, 0.25f))
        r.addView(avatar(94), centerParams())
        r.addView(text("TOM ACCESS", 23f, Color.WHITE, true), centerParams(top = 14))
        r.addView(text("ENTER SECRET CODE", 12f, Color.rgb(175, 92, 102), false), centerParams(top = 4))
        val input = EditText(this).apply {
            hint = "Secret code"
            hintTextColor = Color.rgb(130, 130, 140)
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            textSize = 18f
            background = rounded(Color.rgb(24, 12, 15), 18, Color.rgb(130, 25, 36), 2)
        }
        r.addView(input, matchParams(top = 24, height = 58))
        val enter = button("UNLOCK", Color.rgb(225, 39, 56))
        r.addView(enter, matchParams(top = 12, height = 54))
        val status = text("", 12f, Color.rgb(255, 65, 78), true)
        r.addView(status, centerParams(top = 12))
        enter.setOnClickListener { if (input.text.toString().equals("TOM", true)) showRed() else status.text = "Password Incorrect ❌" }
        r.addView(text("Back to panel", 12f, Color.rgb(110, 120, 130), false).apply { setOnClickListener { showBlue() } }, centerParams(top = 20))
    }

    private fun showRed() {
        screen = Screen.RED
        val r = base()
        val head = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL }
        head.addView(avatar(58))
        val tb = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(12), 0, 0, 0) }
        tb.addView(text("TOM HACK", 21f, Color.WHITE, true))
        tb.addView(text("DARK AURA PANEL", 10f, Color.rgb(230, 100, 110), false))
        head.addView(tb, LinearLayout.LayoutParams(0, -2, 1f))
        val back = button("×", Color.rgb(55, 18, 22)).apply { textSize = 22f }
        head.addView(back, LinearLayout.LayoutParams(dp(52), dp(52)))
        r.addView(head, matchParams(height = 64))
        r.addView(text("VISUAL CONTROLS", 12f, Color.rgb(210, 70, 82), true), matchParams(top = 16))
        val names = listOf("🎯  AIM", "⚡  BOLT", "◉  AIM LOOK", "🔌  USB", "🎚  SENSI")
        names.forEach { name ->
            val row = switchRow(name, false)
            r.addView(row, matchParams(top = 10, height = 64))
        }
        r.addView(text("SIMULATION MODE • NO REAL FUNCTIONS", 10f, Color.rgb(105, 70, 75), false), centerParams(top = 18))
        val float = button("⭕  FLOATING MODE", Color.rgb(185, 27, 42))
        r.addView(float, matchParams(top = 14, height = 52))
        back.setOnClickListener { showBlue() }
        float.setOnClickListener { enableFloating() }
    }

    private fun switchRow(label: String, initial: Boolean): LinearLayout {
        val row = LinearLayout(this).apply {
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(14), 0, dp(10), 0)
            background = rounded(Color.rgb(18, 22, 28), 16, Color.rgb(43, 49, 58), 1)
        }
        row.addView(text(label, 14f, Color.WHITE, true), LinearLayout.LayoutParams(0, -1, 1f).apply { gravity = Gravity.CENTER_VERTICAL })
        val sw = Switch(this).apply { isChecked = initial; buttonTintList = android.content.res.ColorStateList.valueOf(Color.WHITE) }
        row.addView(sw, LinearLayout.LayoutParams(dp(60), dp(48)))
        sw.setOnCheckedChangeListener { _, checked ->
            if (checked) Toast.makeText(this, "$label Activated ✓", Toast.LENGTH_SHORT).show()
        }
        return row
    }

    private fun sliderRow(label: String, value: Int): LinearLayout {
        val row = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL }
        row.addView(text(label, 11f, Color.rgb(185, 195, 207), true), LinearLayout.LayoutParams(dp(62), -1))
        val seek = SeekBar(this).apply { progress = value; max = 100 }
        row.addView(seek, LinearLayout.LayoutParams(0, dp(40), 1f))
        return row
    }

    private fun avatar(size: Int): ImageView = ImageView(this).apply {
        setImageResource(com.tomfreestyle.panel.R.drawable.tom_avatar)
        scaleType = ImageView.ScaleType.CENTER_CROP
        background = circle(Color.BLACK)
        clipToOutline = true
        outlineProvider = android.view.ViewOutlineProvider.BACKGROUND
        elevation = dp(6).toFloat()
        layoutParams = LinearLayout.LayoutParams(dp(size), dp(size))
    }

    private fun enableFloating() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "Allow 'Display over other apps' first", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivity(intent)
            return
        }
        val intent = Intent(this, FloatingBubbleService::class.java)
        if (Build.VERSION.SDK_INT >= 26) startForegroundService(intent) else startService(intent)
        Toast.makeText(this, "Floating bubble enabled", Toast.LENGTH_SHORT).show()
    }

    private fun updateFloatingButtonState() {}

    private fun text(s: String, size: Float, color: Int, bold: Boolean): TextView = TextView(this).apply {
        text = s; textSize = size; setTextColor(color); typeface = if (bold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT; gravity = Gravity.CENTER_VERTICAL
    }

    private fun button(label: String, color: Int): TextView = TextView(this).apply {
        text = label; textSize = 13f; setTextColor(Color.WHITE); gravity = Gravity.CENTER; typeface = Typeface.DEFAULT_BOLD
        background = rounded(color, 16, Color.TRANSPARENT, 0); isClickable = true; isFocusable = true
        setPadding(dp(8), dp(4), dp(8), dp(4))
    }

    private fun rounded(fill: Int, radius: Int, stroke: Int, strokeWidth: Int): GradientDrawable = GradientDrawable().apply {
        setColor(fill); cornerRadius = dp(radius).toFloat(); if (strokeWidth > 0) setStroke(dp(strokeWidth), stroke)
    }
    private fun circle(fill: Int): GradientDrawable = rounded(fill, 100, Color.rgb(220, 220, 220), 1)
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun matchParams(top: Int = 0, height: Int = -2) = LinearLayout.LayoutParams(-1, if (height == -2) -2 else dp(height)).apply { topMargin = dp(top) }
    private fun centerParams(top: Int = 0) = LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(top); gravity = Gravity.CENTER_HORIZONTAL }
}
