package com.example.mygallery.ui.photoList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mygallery.R
import com.example.mygallery.data.model.PhotosItem
import com.squareup.picasso.Picasso


class PhotosAdapter(
    val clickListener: (PhotosItem) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listOfPhotos = ArrayList<PhotosItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotosViewHolder(parent) { clickListener(listOfPhotos[it]) }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotosViewHolder).onBind(listOfPhotos[position])
    }

    override fun getItemCount() = listOfPhotos.size

    class PhotosViewHolder(parent: ViewGroup, onClickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_photo, parent, false)
        ) {
        init {
            itemView.setOnClickListener { onClickAtPosition(adapterPosition) }
        }

        private val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
        private val photoNameTextView: TextView = itemView.findViewById(R.id.photoNameTextView)
        private val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)

        fun onBind(photo: PhotosItem) {
            authorTextView.text = photo.user.name
            photoNameTextView.text = photo.description

            Picasso.with(itemView.context)
                .load(photo.urls.small)
                .into(photoImageView)
        }
    }


}