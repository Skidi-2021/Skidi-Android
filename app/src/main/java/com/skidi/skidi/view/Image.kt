package com.skidi.skidi.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.skidi.skidi.databinding.ActivityImageBinding
import com.skidi.skidi.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit


class Image : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var activityImageBinding: ActivityImageBinding
    private lateinit var bitmapBuffer: Bitmap
    private lateinit var rotationMatrix: Matrix

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var longitude = 0.0
    private var latitude = 0.0


    companion object {
        const val EXTRA_IMAGE_URL = "extra_image_url"
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityImageBinding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(activityImageBinding.root)

        val skidiModel = Model.newInstance(applicationContext)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if(hasLocationPermission()){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                longitude = it.longitude
                latitude = it.latitude
                Log.d("location", "$longitude $latitude")
            }
            fusedLocationProviderClient.lastLocation.addOnFailureListener {
                Log.e("location", it.toString())
            }
        } else{
            requestLocationPermission()
        }

        val fileName = "label.txt"
        val inputString = application.assets.open(fileName).bufferedReader().use { it.readText() }
        val label = inputString.split("\n")

        val savedUri = intent.getStringExtra(EXTRA_IMAGE_URL)
//        Log.d("imageeee", savedUri.toString())
        val msg = "Photo capture succeeded: $savedUri"

        Glide
            .with(this)
            .load(savedUri)
            .into(activityImageBinding.ivImage)

//        val drawable = activityImageBinding.ivImage.drawable
//        val bitmapDrawable = convertImageViewToBitmap(activityImageBinding.ivImage)
//        val bitmapDrawable = drawable as BitmapDrawable
//        val drawableBitmap = bitmapDrawable.bitmap
//        val stream = ByteArrayOutputStream()
//        drawableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//        val imageInByte = stream.toByteArray()
//        val bis = ByteArrayInputStream(imageInByte)

//        Log.d("sdfsdf", bitmap)

        activityImageBinding.btnUpload.setOnClickListener {
            var tensorImage = TensorImage(DataType.UINT8)
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 160, 160, 3), DataType.FLOAT32)
            try {
                val source = ImageDecoder.createSource(this.contentResolver, Uri.parse(savedUri))
                var bitmap = ImageDecoder.decodeBitmap(source)
                bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

                val imageProcessor = ImageProcessor.Builder()
                    .add(ResizeOp(320, 320, ResizeOp.ResizeMethod.BILINEAR))
                    .build()
                tensorImage.load(bitmap)

                tensorImage = imageProcessor.process(tensorImage)
                inputFeature0.loadBuffer(tensorImage.buffer)

// Runs model inference and gets result.
                val outputs = skidiModel.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                val max = getMax(outputFeature0.floatArray)

//                val output = skidiModel.process(tensorImage.tensorBuffer)

//                val tfImage = TensorImage.fromBitmap(newBitmap)
//                val output = skidiModel.process(tfImage.tensorBuffer)
                Log.d("modelOutput", "index: " + max.toString())
                Log.d("modelOutput", outputFeature0.floatArray[max].toString())
                Log.d("modelOutput", "label: " + label[max])
                Toast.makeText(baseContext, label[max], Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Log.e("modelOutput", e.toString())
                Log.e("modelB", tensorImage.buffer.toString())
                Log.e("modelBI", inputFeature0.buffer.toString())
            }
        }
//        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
    }

    private fun hasLocationPermission()=
        EasyPermissions.hasPermissions(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )


    private fun requestLocationPermission(){
        EasyPermissions.requestPermissions(
            this,
            "We need you location to give you nearby clinic",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f

        for (i in 0..6) {
            if (arr[i] > min) {
                ind = i
                min = arr[i]
            }
        }
        return ind
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("location_granted", requestCode.toString())
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        requestLocationPermission()
        Log.e("location_denied", requestCode.toString())
    }

//    private fun convertImageViewToBitmap(v: ImageView): Bitmap? {
//        return (v.getDrawable() as BitmapDrawable).bitmap
//    }

//    private fun toBitmap(imageProxy: ImageProxy): Bitmap? {
//
//        val yuvToRgbConverter = YuvToRgbCConverter(applicationContext)
//
//        val image = imageProxy.image ?: return null
//
//        // Initialise Buffer
//        if (!::bitmapBuffer.isInitialized) {
//            // The image rotation and RGB image buffer are initialized only once
////            Log.d(TAG, "Initalise toBitmap()")
//            rotationMatrix = Matrix()
//            rotationMatrix.postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
//            bitmapBuffer = Bitmap.createBitmap(
//                imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888
//            )
//        }
//
//        // Pass image to an image analyser
//        yuvToRgbConverter.yuvToRgb(image, bitmapBuffer)
//
//        // Create the Bitmap in the correct orientation
//        return Bitmap.createBitmap(
//            bitmapBuffer,
//            0,
//            0,
//            bitmapBuffer.width,
//            bitmapBuffer.height,
//            rotationMatrix,
//            false
//        )
//    }
}