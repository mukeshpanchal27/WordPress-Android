package org.wordpress.android.ui.jetpack.restore.progress

import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.wordpress.android.ui.jetpack.common.JetpackListItemState
import org.wordpress.android.ui.jetpack.common.ViewType
import org.wordpress.android.ui.jetpack.common.viewholders.JetpackButtonViewHolder
import org.wordpress.android.ui.jetpack.common.viewholders.JetpackDescriptionViewHolder
import org.wordpress.android.ui.jetpack.common.viewholders.JetpackHeaderViewHolder
import org.wordpress.android.ui.jetpack.common.viewholders.JetpackIconViewHolder
import org.wordpress.android.ui.jetpack.common.viewholders.JetpackViewHolder
import org.wordpress.android.ui.jetpack.restore.viewholders.RestoreAdditionalInformationViewHolder
import org.wordpress.android.ui.jetpack.restore.viewholders.RestoreProgressViewHolder
import org.wordpress.android.ui.utils.UiHelpers
import org.wordpress.android.util.image.ImageManager

class RestoreProgressAdapter(
    private val imageManager: ImageManager,
    private val uiHelpers: UiHelpers
) : RecyclerView.Adapter<JetpackViewHolder>() {
    private val items = mutableListOf<JetpackListItemState>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JetpackViewHolder {
        return when (viewType) {
            ViewType.ICON.id -> JetpackIconViewHolder(imageManager, parent)
            ViewType.HEADER.id -> JetpackHeaderViewHolder(uiHelpers, parent)
            ViewType.DESCRIPTION.id -> JetpackDescriptionViewHolder(uiHelpers, parent)
            ViewType.RESTORE_PROGRESS.id -> RestoreProgressViewHolder(uiHelpers, parent)
            ViewType.ACTION_BUTTON.id -> JetpackButtonViewHolder(uiHelpers, parent)
            ViewType.RESTORE_ADDITIONAL_INFORMATION.id ->
                RestoreAdditionalInformationViewHolder(uiHelpers, parent)
            else -> throw IllegalArgumentException("Unexpected view type in ${this::class.java.simpleName}")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: JetpackViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemViewType(position: Int) = items[position].type.id

    @MainThread
    fun update(newItems: List<JetpackListItemState>) {
        val diffResult = DiffUtil.calculateDiff(
                RestoreProgressListDiffUtils(
                        items.toList(),
                        newItems
                )
        )
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    private class RestoreProgressListDiffUtils(
        val oldItems: List<JetpackListItemState>,
        val newItems: List<JetpackListItemState>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            if (oldItem::class != newItem::class) {
                return false
            }
            return oldItem.longId() == newItem.longId()
        }

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }
}
