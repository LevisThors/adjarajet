package com.example.adjarajet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieRepository: MovieRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        movieRepository = MovieRepository(requireContext())

        movieAdapter = MovieAdapter(emptyList(), movieRepository)
        recyclerView.adapter = movieAdapter

        fetchMovies()

        return view
    }

    private fun fetchMovies() {
        val apiService = RetrofitInstance.api
        val call = apiService.getPopularMovies(apiKey = "dcebbcb31ea57c153cb5316bff10649b")

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    if (movies.isEmpty()) {
                        Log.d("HomeFragment", "No movies found.")
                    }
                    movieAdapter.updateMovies(movies)
                } else {
                    Log.e("HomeFragment", "Response Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("HomeFragment", "API Call Failed: ${t.message}")
            }
        })
    }
}
