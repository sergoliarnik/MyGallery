package com.example.mygallery.ui.photoList

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygallery.databinding.FragmentPhotoListBinding


class PhotoListFragment : Fragment() {
    companion object {
        const val LIST_STATE_KEY = "listStateKey"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var viewModel: PhotoListViewModel
    private lateinit var binding: FragmentPhotoListBinding
    private lateinit var adapter: PhotosAdapter
    private var listState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        recyclerView = binding.photoListRv
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY)
        }
        viewModel = ViewModelProvider(this)[PhotoListViewModel::class.java]
        setRecyclerView()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LIST_STATE_KEY, layoutManager.onSaveInstanceState())
    }


    override fun onResume() {
        super.onResume()

        if (listState != null && recyclerView.layoutManager != null) {
            layoutManager.onRestoreInstanceState(listState)
        }
    }

    private fun setRecyclerView() {
        setAdapter()
        setDataToAdapter()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    viewModel.getPhotos(++viewModel.page)
                    Log.e("test", viewModel.page.toString())
                }
            }
        })
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
    }

    private fun setAdapter() {
        adapter = PhotosAdapter {
            val action =
                PhotoListFragmentDirections.actionPhotoListFragmentToPhotoDetailFragment(
                    it.urls.regular
                )
            requireView().findNavController().navigate(action)
        }
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataToAdapter() {
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.listOfPhotos = it
            adapter.notifyDataSetChanged()
        }
        if (viewModel.page == 1) {
            viewModel.getPhotos(1)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.list.removeObservers(viewLifecycleOwner)
    }
}