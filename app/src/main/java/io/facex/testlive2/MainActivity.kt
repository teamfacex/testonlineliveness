package io.facex.testlive2

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.facex.liveness.Liveness
import io.facex.liveness.LivenessListener


class MainActivity : AppCompatActivity(), LivenessListener {
    private lateinit var eyeSwitch: Switch
    private lateinit var mouthSwitch: Switch
    private lateinit var yawSwitch: Switch
    private lateinit var randSwitch: Switch
    private lateinit var liveness: Liveness
    private lateinit var imageView: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var matchButton: Button
    private lateinit var bitmapFrame: Bitmap
    override fun livenessError(p0: Boolean?, p1: String?) {


    }

    override fun livenessSuccess(p0: Boolean?, p1: Bitmap?) {
        if(p0!!){
            Toast.makeText(this,getString(R.string.live_success),Toast.LENGTH_SHORT).show()
            imageView.setImageBitmap(p1)
        }else{
            Toast.makeText(this,getString(R.string.live_fail),Toast.LENGTH_SHORT).show()

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        liveness= Liveness(this, R.id.helo)

        eyeSwitch=findViewById(R.id.eyeButton)

        mouthSwitch=findViewById(R.id.mouthButton)

        yawSwitch=findViewById(R.id.yawButton)

        randSwitch=findViewById(R.id.randButton)

        imageView = findViewById(R.id.sendbackImageview)



        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {


            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    1
                )
            }

        } else {

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    liveness.startLiveness()
                }
                return
            }

        }
    }

    fun onTestButtonClicked(view: View){
        Log.e("Clcik",view.toString())
        when(eyeSwitch.isChecked){
            true -> liveness.Eyes=true
            false -> liveness.Eyes=false
        }
        when(mouthSwitch.isChecked){
            true -> liveness.Smile=true
            false -> liveness.Smile=false
        }
        when(yawSwitch.isChecked){
            true -> liveness.Yaw=true
            false -> liveness.Yaw=false
        }
        when(randSwitch.isChecked){
            true -> liveness.Random=true
            false -> liveness.Random=false
        }
        imageView.setImageDrawable(null)
        liveness.startLiveness()
    }


}

