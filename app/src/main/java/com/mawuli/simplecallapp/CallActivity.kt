package com.mawuli.simplecallapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mawuli.simplecallapp.databinding.ActivityCallBinding
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.service.defines.ZegoUIKitUser


class CallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra(USER_ID)
        val username = intent.getStringExtra(USERNAME)

        startCallService(userId = userId, username = username)

        setupUserDetail(userId = userId, username = username)

        setupVoiceCall()

        setupVideoCall()

        setupExitButton()
    }


    private fun startCallService(userId: String?, username: String?) {
        val config = ZegoUIKitPrebuiltCallInvitationConfig()
        ZegoUIKitPrebuiltCallService.init(application, APP_ID, APP_SIGN, userId, username, config)
    }

    private fun setupVoiceCall() {
        binding.newAudioCall.setIsVideoCall(false)
        binding.newAudioCall.setOnClickListener { errorCode, errorMessage, errorInvitees ->
            val targetUserID = binding.targetUserIdInput.text.toString()
            val users = listOf(ZegoUIKitUser(targetUserID, "${targetUserID}_name"))
            binding.newAudioCall.setInvitees(users)
        }
    }

    private fun setupVideoCall() {
        binding.newVideoCall.setIsVideoCall(true)
        binding.newVideoCall.setOnClickListener { errorCode, errorMessage, errorInvitees ->
            val targetUserID = binding.targetUserIdInput.text.toString()
            val users = listOf(ZegoUIKitUser(targetUserID, "${targetUserID}_name"))
            binding.newVideoCall.setInvitees(users)
        }
    }

    private fun setupUserDetail(userId: String?, username: String?) {
        binding.userId.text = "ID: $userId"
        binding.username.text = "Name: $username"
    }

    private fun setupExitButton() {
        binding.exit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirm Exit")
                .setMessage("Are you sure you want logout?")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                    ZegoUIKitPrebuiltCallService.unInit()
                    finish()
                }

            builder.create().show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ZegoUIKitPrebuiltCallService.unInit()
    }
}