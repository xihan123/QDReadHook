@file:Suppress("unused")

package cn.xihan.qdds

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import kotlin.DeprecationLevel.ERROR

internal const val NO_GETTER: String = "Property does not have a getter"

internal fun noGetter(): Nothing = throw NotImplementedError(NO_GETTER)
typealias AlertBuilderFactory<D> = (Context) -> AlertBuilder<D>

val AppCompat: AlertBuilderFactory<DialogInterface> = { context ->
    object : AlertDialogBuilder() {
        override val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    }
}

fun Fragment.alert(
    message: CharSequence,
    title: CharSequence? = null,
    block: (AlertBuilder<*>.() -> Unit)? = null,
) =
    alert(AppCompat, message, title, block)

fun Context.alert(
    message: CharSequence,
    title: CharSequence? = null,
    block: (AlertBuilder<*>.() -> Unit)? = null,
) =
    alert(AppCompat, message, title, block)

inline fun <D : DialogInterface> Fragment.alert(
    factory: AlertBuilderFactory<D>,
    message: CharSequence,
    title: CharSequence? = null,
    noinline block: (AlertBuilder<D>.() -> Unit)? = null,
) =
    requireContext().alert(factory, message, title, block)

inline fun <D : DialogInterface> Context.alert(
    factory: AlertBuilderFactory<D>,
    message: CharSequence,
    title: CharSequence? = null,
    noinline block: (AlertBuilder<D>.() -> Unit)? = null,
) =
    alertDialog(factory) {
        title?.let { this.title = it }
        this.message = message
        block?.invoke(this)
    }.show()

fun Context.alertDialog(block: AlertBuilder<*>.() -> Unit) =
    alertDialog(AppCompat, block)

inline fun <D : DialogInterface> Context.alertDialog(
    factory: AlertBuilderFactory<D>,
    block: AlertBuilder<D>.() -> Unit,
) =
    factory(this).apply(block)

fun Context.multiChoiceSelector(
    items: List<CharSequence>,
    checkItems: BooleanArray,
    title: CharSequence? = null,
    onItemSelected: (DialogInterface, Int, Boolean) -> Unit,
) =
    multiChoiceSelector(AppCompat, items, checkItems, title, onItemSelected)

inline fun <D : DialogInterface> Context.multiChoiceSelector(
    factory: AlertBuilderFactory<D>,
    items: List<CharSequence>,
    checkItems: BooleanArray,
    title: CharSequence? = null,
    noinline onItemSelected: (DialogInterface, Int, Boolean) -> Unit,
) =
    alertDialog(factory) {
        title?.let { this.title = it }
        multiChoiceItems(items, checkItems, onItemSelected)
    }.show()

fun AlertBuilder<*>.okButton(onClicked: (dialog: DialogInterface) -> Unit) =
    positiveButton(android.R.string.ok, onClicked)

fun AlertBuilder<*>.cancelButton(onClicked: (dialog: DialogInterface) -> Unit = { it.dismiss() }) =
    negativeButton(android.R.string.cancel, onClicked)

inline fun <T> AlertBuilder<*>.multiChoiceItems(
    items: List<T>,
    checkItems: BooleanArray,
    crossinline onItemSelected: (DialogInterface, T, Int, Boolean) -> Unit,
) =
    multiChoiceItems(items.map { it.toString() }, checkItems) { dialog, which, isChecked ->
        onItemSelected(dialog, items[which], which, isChecked)
    }

fun Dialog.doOnCancel(block: (DialogInterface) -> Unit) = apply {
    setOnCancelListener(block)
}

fun Dialog.doOnDismiss(block: (DialogInterface) -> Unit) = apply {
    setOnDismissListener(block)
}

fun Dialog.doOnShow(block: (DialogInterface) -> Unit) = apply {
    setOnShowListener(block)
}

fun DialogInterface.doOnCancel(block: (DialogInterface) -> Unit) = apply {
    check(this is Dialog)
    setOnCancelListener(block)
}

fun DialogInterface.doOnDismiss(block: (DialogInterface) -> Unit) = apply {
    check(this is Dialog)
    setOnDismissListener(block)
}

fun DialogInterface.doOnShow(block: (DialogInterface) -> Unit) = apply {
    check(this is Dialog)
    setOnShowListener(block)
}

interface AlertBuilder<out D : DialogInterface> {
    val context: Context

    var title: CharSequence
        @Deprecated(NO_GETTER, level = ERROR) get
    var titleResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get

    var message: CharSequence
        @Deprecated(NO_GETTER, level = ERROR) get
    var messageResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get

    var icon: Drawable
        @Deprecated(NO_GETTER, level = ERROR) get
    var iconResource: Int
        @Deprecated(NO_GETTER, level = ERROR) get

    var customTitle: View
        @Deprecated(NO_GETTER, level = ERROR) get

    var customView: View
        @Deprecated(NO_GETTER, level = ERROR) get

    var isCancelable: Boolean
        @Deprecated(NO_GETTER, level = ERROR) get

    fun onCancelled(handler: (DialogInterface) -> Unit)

    fun onKeyPressed(handler: (DialogInterface, keyCode: Int, e: KeyEvent) -> Boolean)

    fun positiveButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit)

    fun positiveButton(
        @StringRes buttonTextResource: Int,
        onClicked: (dialog: DialogInterface) -> Unit,
    )

    fun negativeButton(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit)

    fun negativeButton(
        @StringRes buttonTextResource: Int,
        onClicked: (dialog: DialogInterface) -> Unit,
    )

    fun neutralPressed(buttonText: String, onClicked: (dialog: DialogInterface) -> Unit)

    fun neutralPressed(
        @StringRes buttonTextResource: Int,
        onClicked: (dialog: DialogInterface) -> Unit,
    )

    fun multiChoiceItems(
        items: List<CharSequence>,
        checkedItems: BooleanArray,
        onItemSelected: (dialog: DialogInterface, index: Int, isChecked: Boolean) -> Unit,
    )

    fun build(): D

    fun show(): D
}

abstract class AlertDialogBuilder : AlertBuilder<AlertDialog> {

    abstract val builder: AlertDialog.Builder
    override val context: Context get() = builder.context

    override var title: CharSequence
        @Deprecated(NO_GETTER, level = ERROR)
        get() = noGetter()
        set(value) {
            builder.setTitle(value)
        }

    override var titleResource: Int
        @Deprecated(NO_GETTER, level = ERROR)
        get() = noGetter()
        set(value) {
            builder.setTitle(value)
        }

    override var message: CharSequence
        @Deprecated(NO_GETTER, level = ERROR)
        get() = noGetter()
        set(value) {
            builder.setMessage(value)
        }

    override var messageResource: Int
        @Deprecated(NO_GETTER, level = ERROR)
        get() = noGetter()
        set(value) {
            builder.setMessage(value)
        }

    override var icon: Drawable
        @Deprecated(NO_GETTER, level = ERROR)
        get() = noGetter()
        set(value) {
            builder.setIcon(value)
        }

    override var iconResource: Int
        @Deprecated(NO_GETTER, level = ERROR)
        get() = noGetter()
        set(value) {
            builder.setIcon(value)
        }

    override var customTitle: View
        @Deprecated(NO_GETTER, level = ERROR)
        get() = noGetter()
        set(value) {
            builder.setCustomTitle(value)
        }

    override var customView: View
        @Deprecated(NO_GETTER, level = ERROR)
        get() = noGetter()
        set(value) {
            builder.setView(value)
        }

    override var isCancelable: Boolean
        @Deprecated(NO_GETTER, level = ERROR)
        get() = noGetter()
        set(value) {
            builder.setCancelable(value)
        }

    override fun onCancelled(handler: (DialogInterface) -> Unit) {
        builder.setOnCancelListener(handler)
    }

    override fun onKeyPressed(handler: (DialogInterface, keyCode: Int, e: KeyEvent) -> Boolean) {
        builder.setOnKeyListener(handler)
    }

    override fun positiveButton(buttonText: String, onClicked: (DialogInterface) -> Unit) {
        builder.setPositiveButton(buttonText) { dialog, _ -> onClicked(dialog) }
    }

    override fun positiveButton(buttonTextResource: Int, onClicked: (DialogInterface) -> Unit) {
        builder.setPositiveButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
    }

    override fun negativeButton(buttonText: String, onClicked: (DialogInterface) -> Unit) {
        builder.setNegativeButton(buttonText) { dialog, _ -> onClicked(dialog) }
    }

    override fun negativeButton(buttonTextResource: Int, onClicked: (DialogInterface) -> Unit) {
        builder.setNegativeButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
    }

    override fun neutralPressed(buttonText: String, onClicked: (DialogInterface) -> Unit) {
        builder.setNeutralButton(buttonText) { dialog, _ -> onClicked(dialog) }
    }

    override fun neutralPressed(buttonTextResource: Int, onClicked: (DialogInterface) -> Unit) {
        builder.setNeutralButton(buttonTextResource) { dialog, _ -> onClicked(dialog) }
    }

    override fun multiChoiceItems(
        items: List<CharSequence>,
        checkedItems: BooleanArray,
        onItemSelected: (DialogInterface, Int, Boolean) -> Unit,
    ) {
        builder.setMultiChoiceItems(
            items.toTypedArray(),
            checkedItems
        ) { dialog, which, isChecked ->
            onItemSelected(dialog, which, isChecked)
        }
    }

    override fun build(): AlertDialog = builder.create()

    override fun show(): AlertDialog = builder.show()
}
