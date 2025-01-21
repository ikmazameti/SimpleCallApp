package com.example.zegoclouddemoapp

import android.Manifest
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.callback.ExplainReasonCallback
import com.permissionx.guolindev.callback.RequestCallback
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        addCallFragment()


    }

    private fun addCallFragment() {

        val callID: String = "123"
        val userID: String = "354aggdaDFGF"
        val userName: String = "Mawuli"

        // You can also use GroupVideo/GroupVoice/OneOnOneVoice to make more types of calls.
        val config = ZegoUIKitPrebuiltCallConfig.oneOnOneVideoCall()
        val fragment = ZegoUIKitPrebuiltCallFragment.newInstance(
            APP_ID, APP_SIGN, userID, userName, callID, config
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commitNow()
    }

    fun requestPermission() {

        // need a activityContext.
        PermissionX.init(this).permissions(Manifest.permission.SYSTEM_ALERT_WINDOW)
            .onExplainRequestReason(ExplainReasonCallback { scope, deniedList ->
                val message =
                    "We need your consent for the following permissions in order to use the offline call function properly"
                scope.showRequestReasonDialog(deniedList, message, "Allow", "Deny")
            }).request(RequestCallback { allGranted, grantedList, deniedList -> })

    }
}