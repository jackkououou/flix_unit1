package com.example.flix_unit1

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MovieAdpater"
class MovieAdapter(private val context: Context, private val movies: List<Movie>)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder> () {
    val orientation = context.resources.configuration.orientation

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "OnCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "OnBindViewHolder position: $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)
        private val ivBackground = itemView.findViewById<ImageView>(R.id.ivBackground)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvLandTitle = itemView.findViewById<TextView>(R.id.tvLandTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val tvLandOverview = itemView.findViewById<TextView>(R.id.tvLandOverview)
        fun bind(movie: Movie) {
            val orientation = context.resources.configuration.orientation
            if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                tvTitle.text = movie.title
                tvOverview.text = movie.overview
                Glide.with(context).load(movie.posterImgUrl).into(ivPoster)
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                tvLandTitle.text = movie.title
                tvLandOverview.text = movie.overview
                Glide.with(context).load(movie.backgroundImgUrl).into(ivBackground)
            }
        }
    }

}
