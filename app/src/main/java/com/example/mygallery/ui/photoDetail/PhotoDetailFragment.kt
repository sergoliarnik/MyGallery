package com.example.mygallery.ui.photoDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.mygallery.databinding.FragmentPhotoDetailBinding
import com.squareup.picasso.Picasso

class PhotoDetailFragment : Fragment() {
    companion object {
        const val PHOTO_URL_ARG = "photoUrl"
    }

    private lateinit var imageView: ImageView

    private lateinit var binding: FragmentPhotoDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        imageView = binding.photoImageView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            Picasso.with(context)
                .load(it.getString(PHOTO_URL_ARG).toString())
                .into(imageView)
        }
    }


}