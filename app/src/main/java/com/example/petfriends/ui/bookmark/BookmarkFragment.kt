package com.example.petfriends.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petfriends.data.local.model.ItemList
import com.example.petfriends.databinding.FragmentBookmarkBinding
import com.example.petfriends.ui.adapter.ListItemAdapter
import com.example.petfriends.utils.CalendarUtils
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
    private lateinit var listItemRecylerView: RecyclerView


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

        listItemRecylerView = binding.rvListItem
        listItemRecylerView.layoutManager = LinearLayoutManager(context)
        listItemRecylerView.setHasFixedSize(true)

        listItem()
//        CalendarUtils.selectedDate = LocalDate.now()
//        setMonthView()

    }

    private fun listItem() {
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
                                Log.d(TAG, "Success")
//                                Toast.makeText(context, "Data found", Toast.LENGTH_SHORT).show()
                                binding.rvListItem.adapter = ListItemAdapter(itemList)
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
//                else {
//                    Log.d(TAG, "Failed")
//                    Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show()
//                }
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
        const val TAG = "BookmarkFragment"
    }
}