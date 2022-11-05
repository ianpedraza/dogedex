package com.ianpedraza.dogedex.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.dogedex.R
import com.ianpedraza.dogedex.databinding.ItemListDogsBinding
import com.ianpedraza.dogedex.domain.models.Dog
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.fromUrl
import com.ianpedraza.dogedex.utils.ViewExtensions.Companion.showView

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
                if (item.inCollection) {
                    textViewDogItem.showView(false)

                    imageViewDogItem.fromUrl(item.imageUrl)
                    imageViewDogItem.showView()

                    constraintLayoutDogItem.background =
                        ContextCompat.getDrawable(root.context, R.drawable.dog_list_item_background)

                    root.setOnClickListener { onAction(Action.OnClick(item)) }
                } else {
                    imageViewDogItem.showView(false)

                    textViewDogItem.text =
                        textViewDogItem.context.getString(R.string.format_index, item.index)
                    textViewDogItem.showView()

                    constraintLayoutDogItem.background =
                        ContextCompat.getDrawable(root.context, R.drawable.bg_dog_item_unknown)
                }
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
