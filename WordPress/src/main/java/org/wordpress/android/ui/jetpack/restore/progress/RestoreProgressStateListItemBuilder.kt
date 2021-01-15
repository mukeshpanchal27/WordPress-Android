package org.wordpress.android.ui.jetpack.restore.progress

import dagger.Reusable
import org.wordpress.android.R
import org.wordpress.android.ui.jetpack.common.JetpackListItemState
import org.wordpress.android.ui.jetpack.common.JetpackListItemState.ActionButtonState
import org.wordpress.android.ui.jetpack.common.JetpackListItemState.DescriptionState
import org.wordpress.android.ui.jetpack.common.JetpackListItemState.HeaderState
import org.wordpress.android.ui.jetpack.common.JetpackListItemState.IconState
import org.wordpress.android.ui.jetpack.restore.RestoreListItemState.AdditionalInformationState
import org.wordpress.android.ui.jetpack.restore.RestoreListItemState.ProgressState
import org.wordpress.android.ui.utils.UiString.UiStringRes
import org.wordpress.android.ui.utils.UiString.UiStringResWithParams
import org.wordpress.android.ui.utils.UiString.UiStringText
import org.wordpress.android.util.toFormattedDateString
import org.wordpress.android.util.toFormattedTimeString
import java.util.Date
import javax.inject.Inject

@Reusable
class RestoreProgressStateListItemBuilder @Inject constructor() {
    fun buildProgressListStateItems(
        progress: Int = 0,
        published: Date,
        onNotifyMeClick: () -> Unit
    ): List<JetpackListItemState> {
        return mutableListOf(
                buildIconState(),
                buildHeaderState(),
                buildDescriptionState(published),
                buildProgressState(progress),
                buildButtonActionState(onNotifyMeClick),
                buildAdditionalInformationState()
        )
    }

    private fun buildIconState() = IconState(
            icon = R.drawable.ic_history_white_24dp, // todo: annmarie replace with correct icon
            contentDescription = UiStringRes(R.string.restore_progress_icon_content_description),
            colorResId = R.color.success_50 // todo: annmarie make correct when doing design cleanup
    )

    private fun buildHeaderState() = HeaderState(
            UiStringRes(R.string.restore_progress_header)
    )

    private fun buildDescriptionState(published: Date) = DescriptionState(
            UiStringResWithParams(
                    R.string.restore_progress_description_with_two_parameters,
                    listOf(
                            UiStringText(published.toFormattedDateString()),
                            UiStringText(published.toFormattedTimeString())
                    )
            )
    )

    private fun buildButtonActionState(onClick: () -> Unit) = ActionButtonState(
            text = UiStringRes(R.string.restore_progress_action_button),
            contentDescription = UiStringRes(R.string.restore_progress_action_button_content_description),
            onClick = onClick
    )

    private fun buildProgressState(progress: Int) = ProgressState(
            progress = progress,
            label = UiStringResWithParams(
                    R.string.restore_progress_label,
                    listOf(UiStringText(progress.toString()))
            )
    )

    private fun buildAdditionalInformationState() = AdditionalInformationState(
            UiStringRes(R.string.restore_progress_additional_info)
    )
}
