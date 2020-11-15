package com.bonsondave.android.vinylplayer

import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    private lateinit var playBtn: ImageButton
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.just_like_you_just_like_me)
    private var mp: MediaPlayer? = null
    private lateinit var seekbar:SeekBar
    private lateinit var vinylImage:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playBtn = findViewById(R.id.buttonPlay)
        seekbar = findViewById(R.id.playerSeekbar)
        vinylImage = findViewById(R.id.img_vinyl)


        controlSound(currentSong[0])
    }

    //Play button and seekbar
    private fun controlSound(id: Int) {

        playBtn.setOnClickListener {
            if (mp == null){
                mp = MediaPlayer.create(this, id)
                Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")

                initialiseSeekBar()
            }
            mp?.start()
            vinylAnimation()
            //XXX
            playBtn.setImageResource(R.drawable.ic_pause)
            Log.d("MainActivity", "Duration: ${mp!!.duration/1000} seconds")
        }

        //Seekbar
        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun initialiseSeekBar() {

        seekbar.max = mp!!.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e:Exception) {
                    seekbar.progress = 0
                }
            }
        }, 0)
    }

    //Animation to spin the vinyl image
    private fun vinylAnimation() {
        val spin = AnimationUtils.loadAnimation(this,R.anim.rotate_anim)

        vinylImage.startAnimation(spin)
    }

}