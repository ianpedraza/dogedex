package com.ianpedraza.dogedex.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.dogedex.databinding.ItemListDogsBinding
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.fromUrl

class DogsListAdapter(
    private val onAction: (Action) -> Unit
) : ListAdapter<Dog, DogsListAdapter.ViewHolder>(DogDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) = holder.bind(getItem(position), onAction)

    class ViewHolder private constructor(
        private val binding: ItemListDogsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemListDogsBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Dog, onAction: (Action) -> Unit) {
            with(binding) {
                imageViewDogItem.fromUrl(item.imageUrl)
                root.setOnClickListener { onAction(Action.OnClick(item)) }
            }
        }
    }

    sealed class Action {
        data class OnClick(val dog: Dog) : Action()
    }
}

private object DogDiffUtil : DiffUtil.ItemCallback<Dog>() {
    override fun areItemsTheSame(
        oldItem: Dog,
        newItem: Dog
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Dog,
        newItem: Dog
    ): Boolean = oldItem == newItem
}
