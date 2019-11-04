package com.a1tt.soundpool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BeatBoxFragment : Fragment() {
    private var beatBox: BeatBox? = null

    companion object {
        fun newInstance(): BeatBoxFragment {
            return BeatBoxFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beatBox = BeatBox(activity!!.applicationContext)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beat_box, container, false)
        val recyclerView = view.findViewById(R.id.fragment_beat_box_recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        beatBox?.getSounds()?.let {
            recyclerView.adapter = SoundAdapter(it)
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox?.release()
    }

    private inner class SoundAdapter(private val soundList: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            return SoundHolder(LayoutInflater.from(activity), parent)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            holder.bindSound(soundList[position])
        }

        override fun getItemCount(): Int {
            return soundList.size
        }
    }

    private inner class SoundHolder(layoutInflater: LayoutInflater, container: ViewGroup) :
        RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.list_item_sound, container, false)),
        View.OnClickListener {
        private val button: Button = itemView.findViewById(R.id.list_item_sound_button) as Button
        private var sound: Sound? = null

        init {
            button.setOnClickListener(this)
        }

        fun bindSound(sound: Sound) {
            this.sound = sound
            button.text = this.sound!!.getName()
        }

        override fun onClick(view: View) {
            sound?.let {
                beatBox?.play(it)
            }
        }
    }
}