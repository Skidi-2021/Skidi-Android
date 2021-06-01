package com.skidi.skidi.view

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.skidi.skidi.databinding.ActivityImageBinding
import com.skidi.skidi.ml.Model
import org.tensorflow.lite.support.image.TensorImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class Image : AppCompatActivity() {
    private lateinit var activityImageBinding: ActivityImageBinding
    private lateinit var bitmapBuffer: Bitmap
    private lateinit var rotationMatrix: Matrix


    companion object {
        const val EXTRA_IMAGE_URL = "extra_image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityImageBinding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(activityImageBinding.root)

        val skidiModel = Model.newInstance(applicationContext)

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
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    applicationContext.getContentResolver(),
                    Uri.parse(savedUri)
                )
                val newBitmap = Bitmap.createScaledBitmap(bitmap, 160, 160, false)

                val tfImage = TensorImage.fromBitmap(newBitmap)
                val output = skidiModel.process(tfImage.tensorBuffer)
                Log.d("modelOutput", output.toString())
            } catch (e: Exception) {
                Log.d("modelOutput", e.message.toString())
            }
        }
        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
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