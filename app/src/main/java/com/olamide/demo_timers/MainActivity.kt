package com.olamide.demo_timers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.olamide.demo_timers.databinding.ActivityMainBinding
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    // Variable for Timer which will be initialize later
    private var countDownTimer : CountDownTimer? = null

    // Duration of the timer in milliseconds
    private var timerDuration : Long = 60000

    // Time between Timer starts to pausing timer.... pauseOffset = timerDuration - timeLeft
    private var pauseOffset : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvTimer?.text = (timerDuration/1000).toString()

        binding?.btnStart?.setOnClickListener {
            startTimer (pauseOffset)
        }

        binding?.btnPause?.setOnClickListener {
            pauseTimer()
        }

        binding?.btnReset?.setOnClickListener{
            resetTimer()
        }
    }

    private fun startTimer (pauseOffsetL: Long) {
        /*
        * @param millisInFuture The number of millis in the future from the countdown
        * to {start()} until the countdown is done and {#onFinish()}
        * is called

        * @param: countDownInterval The interval along the way to recieve
        * {#onTick(Long)} callbacks.
         */
        countDownTimer = object : CountDownTimer (timerDuration - pauseOffsetL, 1000) {

            override fun onTick(millisUntilFinished : Long) {
                pauseOffset = timerDuration - millisUntilFinished
                binding?.tvTimer?.text = (millisUntilFinished/1000).toString() // Current progress is set to text view
            }

            override fun onFinish () {
                Toast.makeText(this@MainActivity, "Timer is Finished", Toast.LENGTH_LONG)
            }
        }.start()
    }


    // Function is used to pause the countdown timer which is running
    private fun pauseTimer () {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    private fun resetTimer () {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            binding?.tvTimer?.text = "${(timerDuration/1000).toString()}"
            countDownTimer = null
            pauseOffset = 0
        }
    }
}