package com.archrahkshi.spotifine.ui.library

import com.archrahkshi.spotifine.ui.library.views.ToolBarImpl
import com.archrahkshi.spotifine.ui.library.views.TracksHeaderImpl
import kotlinx.android.synthetic.main.fragment_tracks.*
import kotlinx.android.synthetic.main.toolbar.*

internal class LibraryPresenter(private val activity: LibraryActivity) {

    //
    private val toolBarImpl
            by lazy { ToolBarImpl(activity.tvTitle, activity.imgBack) }

    fun showBackButton() = toolBarImpl.applyBackButton(false)
    fun hideBackButton() = toolBarImpl.applyBackButton(true)
    fun setToolbarTitle(title: String) = toolBarImpl.setTitle(title)
    //
    private val tracksHeaderImpl
            by lazy { TracksHeaderImpl(activity.textViewHeaderLine1,
                                       activity.textViewHeaderLine2,
                                       activity.textViewHeaderLine3,
                                       activity.imageViewHeader) }

    fun setHeaderText(text: String) = tracksHeaderImpl.setText(text)
    fun setHeaderSubtext(subtext: String?) = tracksHeaderImpl.setSubtext(subtext)
    fun setHeaderAdditionalText(additionalText: String) = tracksHeaderImpl.setAdditionalText(additionalText)
    fun setHeaderImage(uri: String) = tracksHeaderImpl.setImage(uri)

}