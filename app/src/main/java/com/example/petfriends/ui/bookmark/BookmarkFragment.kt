package com.example.petfriends.ui.bookmark

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.petfriends.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petfriends.data.local.model.ItemList
import com.example.petfriends.databinding.FragmentBookmarkBinding
import com.example.petfriends.ui.adapter.ListItemAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*

class BookmarkFragment : Fragment() {

    private var _binding : FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth : FirebaseAuth
    private lateinit var database : DatabaseReference

    private lateinit var itemList: ArrayList<ItemList>
    private lateinit var listItemAdapter: ListItemAdapter
//    private lateinit var listItemRecylerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val view: View = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        CalendarUtils.selectedDate = LocalDate.now()

        itemList = arrayListOf<ItemList>()

        mAuth = Firebase.auth
//
//        listItemRecylerView = binding.rvListFoodItem
//        listItemRecylerView.layoutManager = LinearLayoutManager(context)
//        listItemRecylerView.setHasFixedSize(true)


        actionRecylerList()

        listItemFoods()
//        listItemVaccine()
//        CalendarUtils.selectedDate = LocalDate.now()
//        setMonthView()

    }

    private fun actionRecylerList() {
        listItemAdapter = ListItemAdapter()
        binding.rvListFoodItem.apply {
            adapter = listItemAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        listItemAdapter.setOnItemClickCallback(object : ListItemAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemList) {
                showUpdateDialog(data)
            }

        })
    }

    private fun showUpdateDialog(data: ItemList) {
        val dialog = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val viewInflater = inflater.inflate(R.layout.update_food_dialog, null)

        dialog.setView(viewInflater)
        dialog.setTitle(getString(R.string.update))

        val edName = viewInflater.findViewById<EditText>(R.id.update_ed_food_name)
        val edWeight = viewInflater.findViewById<EditText>(R.id.update_ed_food_weight)
        val etDate = viewInflater.findViewById<TextView>(R.id.tv_date)

        edName.setText(data.name)
        edWeight.setText(data.weight)
        etDate.setText(data.date)

        dialog.setPositiveButton(getString(R.string.yes)){_, _ ->

        }
        dialog.setNegativeButton(getString(R.string.no)){_, _ ->

        }

        val alert = dialog.create()
        alert.show()
    }

    private fun listItemFoods() {
        val user = mAuth.currentUser
        database = FirebaseDatabase.getInstance().getReference("PetsFoods")
        database.child(user?.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val key: String = snapshot.key.toString()
                    database.child(key).addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                for (itemSnapshot in snapshot.children) {
                                    val item = itemSnapshot.getValue(ItemList::class.java)
                                    itemList.add(item!!)
                                }
                                listItemAdapter.setAllData(itemList)
                                Log.d(TAG, "Success")
                                Toast.makeText(context, "Data found", Toast.LENGTH_SHORT).show()
//                                listItemAdapter = ListItemAdapter(itemList)

                            }
                            else {
                                Log.d(TAG, "Failed")
                                Toast.makeText(context, "Data not found!, add activity first!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Log.d(TAG, error.message)
                        }
                    })
                }
                else {
                    Log.d(TAG, "Failed")
                    Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, error.message)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "BookmarkFragment"
    }
}