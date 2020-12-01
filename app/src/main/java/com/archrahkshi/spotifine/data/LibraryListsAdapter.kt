package com.archrahkshi.spotifine.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.archrahkshi.spotifine.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_library_list.view.imageViewListPic
import kotlinx.android.synthetic.main.item_library_list.view.layoutLibraryList
import kotlinx.android.synthetic.main.item_library_list.view.textViewListInfo
import kotlinx.android.synthetic.main.item_library_list.view.textViewListName

class LibraryListsAdapter<ListType>(
    private val libraryLists: List<ListType>,
    private val clickListener: (ListType) -> Unit
) : RecyclerView.Adapter<LibraryListsAdapter.ViewHolder<ListType>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder<ListType>(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_library_list, parent, false)
    )

    override fun getItemCount() = libraryLists.size

    override fun onBindViewHolder(holder: ViewHolder<ListType>, position: Int) {
        holder.bind(libraryLists[position], clickListener)
    }

    class ViewHolder<ListType>(view: View) : RecyclerView.ViewHolder(view) {
        private val imageViewListPic = view.imageViewListPic
        private val layoutItemList = view.layoutLibraryList
        private val textViewListInfo = view.textViewListInfo
        private val textViewListName = view.textViewListName
        private val resources = view.context.resources
        private val viewTest = view

        fun bind(listType: ListType, clickListener: (ListType) -> Unit) {
            textViewListName.text = when (listType) {
                is Playlist -> listType.name
                is Artist -> listType.name
                is Album -> listType.name
                else -> null
            }
            textViewListInfo.text = when (listType) {
                is Playlist -> {
                    val size = listType.size
                    "$size ${setWordTracks(size)}"
                }
                is Artist -> ""
                is Album -> listType.artists
                else -> null
            }
            Glide.with(viewTest).load(
                when (listType) {
                    is Playlist -> listType.image
                    is Artist -> listType.image
                    is Album -> listType.image
                    else -> null
                }
            ).into(imageViewListPic)
            layoutItemList.setOnClickListener { clickListener(listType) }
        }

        private fun setWordTracks(size: Int) = resources.getString(
            with(size.toString()) {
                when {
                    endsWith('1') -> R.string.tracks_singular
                    endsWith('2') || endsWith('3') || endsWith('4') ->
                        R.string.tracks_paucal
                    else -> R.string.tracks_plural
                }
            }
        )
    }
}