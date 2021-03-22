package com.example.dictionaryapp.ui.translate.addtranslate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dictionaryapp.R
import com.example.dictionaryapp.ui.communication.FragmentCommunicationInterface
import kotlinx.android.synthetic.main.dialog_add_new_translate.*

class AddNewTranslateDialogFragment : DialogFragment() {

    private var fragmentCommunicationInterface: FragmentCommunicationInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentCommunicationInterface) {
            fragmentCommunicationInterface = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_add_new_translate, container, false)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAddNewTranslate.setOnClickListener {

            if (newTranslate.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), R.string.input_translate, Toast.LENGTH_SHORT)
                    .show()
            } else {
                fragmentCommunicationInterface?.onAddNewTranslate(
                    translate = newTranslate.text.toString()
                )

                newTranslate.text.clear()

                dismiss()
            }
        }
    }
}