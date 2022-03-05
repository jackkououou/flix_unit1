package com.example.flix_unit1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers

private const val TAG = "DetailActivity"
private const val TRAILER_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val YOUTUB_API_KEY = "AIzaSyCRiRGq2X_NlqdEh2kYLzDO3nX1uXygG0c"
class DetailActivity : YouTubeBaseActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var ytPlaver: YouTubePlayerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        ratingBar = findViewById(R.id.rbVoteAverage)
        ytPlaver = findViewById(R.id.pTrailer)

        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        Log.i(TAG, "Movie is $movie")

        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        ratingBar.rating = movie.vote_average.toFloat()

        val client = AsyncHttpClient()
        client.get(TRAILER_URL.format(movie.movieId), object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess $statusCode")
                val results = json.jsonObject.getJSONArray("results")
                if (results.length() == 0) {
                    Log.w(TAG,"No movie trailer found")
                    return
                }
                val movieTrailerJson = results.getJSONObject(0)
                val youtubeKey = movieTrailerJson.getString("key")

                initializeYouTube(youtubeKey)
            }

        })


    }

    private fun initializeYouTube(youtubeKey: String) {
        ytPlaver.initialize(YOUTUB_API_KEY, object: OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                Log.i(TAG, "onInitializationSuccess")
                player?.cueVideo(youtubeKey)
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider?,
                player: YouTubeInitializationResult?
            ) {
                Log.i(TAG, "OnInitializationFailure")
            }

        })
    }
}