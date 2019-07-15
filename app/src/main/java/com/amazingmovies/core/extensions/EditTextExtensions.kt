package com.amazingmovies.core.extensions

import android.content.Context
import android.app.Activity
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun EditText.addOnTextChangedListeners(
    beforeTextChanged: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
    onTextChanged: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
    afterTextChanged: ((s: Editable?) -> Unit)? = null
) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged?.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged?.invoke(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged?.invoke(s, start, before, count)
        }

    })
}

val EditText.value:String
    get() = this.text.toString()


val SearchView.rxSearch:Observable<String>
get() {
    val subject:PublishSubject<String> = PublishSubject.create()
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            subject.onComplete()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            subject.onNext(newText ?: "")
            return true
        }

    })
    return subject
}