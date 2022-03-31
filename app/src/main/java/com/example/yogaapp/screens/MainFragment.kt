package com.example.yogaapp.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.yogaapp.R
import com.example.yogaapp.adapter.OnPoseClickListener
import com.example.yogaapp.adapter.PoseAdapter
import com.example.yogaapp.databinding.FragmentMainBinding
import com.example.yogaapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setRecyclerView()
        setUpClickListeners()

        return binding.root
    }

    private fun setUpClickListeners() {
        binding.addButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddPoseFragment())
        }
    }

    private fun setRecyclerView() {
        val poseAdapter = PoseAdapter(OnPoseClickListener { pose, popupView ->
            val popupMenu = PopupMenu(requireContext(), popupView, Gravity.NO_GRAVITY)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete -> {
                        val dialog = AlertDialog.Builder(requireContext())
                        dialog.setTitle("Delete")
                        dialog.setMessage("are you sure you want to delete this item?")
                        dialog.setPositiveButton("ok") { _, _ ->
                            viewModel.delete(pose)
                        }
                        dialog.setNegativeButton("cancel", null)
                        dialog.setIcon(R.drawable.ic_round_delete_24)
                        dialog.show()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

            popupMenu.inflate(R.menu.pose_list_click_menu_item)
            popupMenu.show()
        })
        binding.rv.adapter = poseAdapter
    }

}