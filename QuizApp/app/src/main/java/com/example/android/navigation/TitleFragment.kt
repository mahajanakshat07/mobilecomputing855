package com.example.android.navigation


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.FragmentTitleBinding

//Code to inflate the fragment onto the MainActivity
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //SetContentView do not exists in a fragment so we have to return the layout
        // Inflate the layout for this fragment

        val bindingFragment : FragmentTitleBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_title,container,false)
        bindingFragment.playButton.setOnClickListener() //get instance of playbutton using bindingFragment variable
        {

            //To find current instance of navigation graph and redirecting the user to the game fragment
            Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_gameFragment)
        }


        //Tell the compiler that the fragment will have menu associated with it
        setHasOptionsMenu(true)
        return bindingFragment.root
    }

    //Overrides the onCreateOptions menu to set our menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        //inflating the menu with game_menu
        inflater?.inflate(R.menu.game_menu, menu)
    }


    ////Overrides the onOptionsItemSelected function
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
                view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

}
