package com.wappster.fitbook.ui.tutorial


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.wappster.fitbook.R
import kotlinx.android.synthetic.main.fragment_tutorial.view.*

class TutorialFragment : Fragment() {

    private var title : String = ""
    private var image : Int = 0

    companion object {

        fun newInstance(title : String, image: Int): TutorialFragment {
            val fragment = TutorialFragment()
            fragment.image = image
            fragment.title = title
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tutorial, container, false)
        view.descriptionTextView.text = title
        view.imageView.setImageDrawable(ContextCompat.getDrawable(context!!, image))
        return view
    }


}
