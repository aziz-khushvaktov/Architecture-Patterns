package com.example.android_mvc.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mvc.activity.MainActivity
import com.example.android_mvc.databinding.ItemPostListBinding
import com.example.android_mvc.model.Post

class PostAdapter(var activity: MainActivity, var items: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(ItemPostListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = items[position]
        if (holder is PostViewHolder) {
            holder.binding.apply {
                tvTitle.text = post.title
                tvBold.text = post.body
                tvTitle.setTypeface(tvTitle.typeface, Typeface.BOLD_ITALIC)


//                    override fun onLongClick(p0: View?): Boolean {
//                        activity.deletePostDialog(post)
//                        return true
//                    }
//
//                })
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: Post, position: Int) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun getData(): ArrayList<Post> {
        return items
    }

    class PostViewHolder(var binding: ItemPostListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}