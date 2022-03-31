package com.example.yogaapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.yogaapp.R
import com.example.yogaapp.model.Pose
import java.io.ByteArrayOutputStream

object Constants {

    const val IS_APP_OPENED_FIRST_TIME_PREF = "is_app_opened_first_time_pref"

    fun getInitialPoses(context: Context): List<Pose>{
        val list = ArrayList<Pose>()
        list.add(Pose(name = "Mountain Pose", image = encodeImage(bitmapFromDrawable(R.drawable.mountain_pose_tadasana_for_beginners, context)), description = "This simplest yoga pose teaches one to stand with majestic steadiness like a mountain. The word 'Tada' means a mountain, that's where the name comes from .Mountain Pose is the base for all standing poses; It involves the major groups of muscles and improves focus and concentration. Mountain pose may seem like \"simply standing,\" but there is a lot going on."))
        list.add(Pose(name = "Tree Pose", image = encodeImage(bitmapFromDrawable(R.drawable.tree_pose, context)), description = "Tree is an awesome standing balance for beginners to work on to gain focus and clarity, and learn to breathe while standing and keeping the body balanced on one foot. . It replicates the steady stance of a tree."))
        list.add(Pose(name = "Triangle Pose", image = encodeImage(bitmapFromDrawable(R.drawable.triangle_pose_or_trikonasana, context)), description = "Triangle is a wonderful standing posture to stretch the sides of the waist, open up the lungs, strengthen the legs and tone the entire body.Triangle Pose is the essential standing pose in many styles of yoga."))
        list.add(Pose(name = "Warrior I Pose", image = encodeImage(bitmapFromDrawable(R.drawable.warrior_i_or_virabhadrasana_1, context)), description = "Warrior pose is quintessential for building strength and stamina in a yoga practice. It give us confidence and stretch the hips and thighs while building strength in the entire lower body and core."))
        list.add(Pose(name = "Upward-Facing Dog Pose", image = encodeImage(bitmapFromDrawable(R.drawable.upword_facing_dog_pose, context)), description = "Urdhva mukha svanasana, or upward facing dog pose, is often practiced in sequence with adho mukha svanasana, downward facing dog pose. It is is a powerful pose that awakens upper-body strength and offers a wonderful stretch for the chest and abdomen."))
        list.add(Pose(name = "Seated Forward Fold Pose", image = encodeImage(bitmapFromDrawable(R.drawable.seated_forward_fold_or_paschimottanasana, context)), description = "It's important to incorporate a forward bend in yoga practice to stretch the hamstrings, lower and upper back and sides. Seated forward bend is the perfect yoga pose for beginners to start to open the body and learn to breathe through challenging positions."))
        list.add(Pose(name = "Bridge Pose", image = encodeImage(bitmapFromDrawable(R.drawable.bridge_pose, context)), description = "Bridge pose (Setu Bandhasana) as a way to bridge the gap between body and mind. A counter pose to a forward bend is a back bend. Bridge is a good beginner's back bend that stretches the front body and strengthens the back body."))
        list.add(Pose(name = "Child Pose", image = encodeImage(bitmapFromDrawable(R.drawable.balasana_or_childs_pose, context)), description = "Balasana also known as child's pose, is a gentle resting pose that stretches the hips, thighs, and legs while calming the mind and relieving stress and tension. Everyone needs a good resting pose and Child pose is an excellent one not just for beginners but for yoga practitioners of all levels."))
        list.add(Pose(name = "Savasana Pose", image = encodeImage(bitmapFromDrawable(R.drawable.savasana_or_corpse_pose, context)), description = "No yoga session is complete without a final relaxation posture. Even though Corpse Pose (Savasana) is a resting pose, Although it looks easy, Savasana (Corpse Pose) has been called the most difficult of the asanas.You should try to stay present and aware during the five to 10 minutes you spend in final relaxation."))

        return list
    }

    fun encodeImage(bitmap: Bitmap): String {
        val previewWidth = 150
        val previewHeight = bitmap.height * previewWidth / bitmap.width
        val previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
        val byteArrayOutputStream = ByteArrayOutputStream()
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun decodeImage(encodedImage: String): Bitmap {
        val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun bitmapFromDrawable(id: Int, context: Context): Bitmap = BitmapFactory.decodeResource(context.resources, id)
}