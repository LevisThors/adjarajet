package com.example.adjarajet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(private var movies: List<Movie>, private val movieRepository: MovieRepository) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateMovies(newMovies: List<Movie>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.movieImage)
        private val ratingTextView: TextView = itemView.findViewById(R.id.movieRating)
        private val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            ratingTextView.text = movie.rating.toString()

            val imageUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
            Glide.with(itemView.context).load(imageUrl).into(imageView)

            val isFavorite = movieRepository.isFavorite(movie)
            updateFavoriteIcon(isFavorite)

            favoriteIcon.setOnClickListener {
                val isFavoriteNow = movieRepository.toggleFavorite(movie)
                updateFavoriteIcon(isFavoriteNow)
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            if (isFavorite) {
                favoriteIcon.setImageResource(android.R.drawable.btn_star_big_on)
            } else {
                favoriteIcon.setImageResource(android.R.drawable.btn_star_big_off)
            }
        }
    }
}

