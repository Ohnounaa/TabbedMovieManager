package com.example.tabbedmoviemanager.Repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.tabbedmoviemanager.Models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDataRetriever {
    private val baseUrl = "https://api.themoviedb.org/3/"
    fun retrieveMostPopularMoviesData(pageNumber:String) :  MutableLiveData<List<Movie>> {
        val popularMovies: MutableLiveData<List<Movie>>  = MutableLiveData()
        val retrofit: Retrofit = Retrofit.
        Builder().
        baseUrl(baseUrl).
        addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: MovieAPI = retrofit.create(MovieAPI::class.java)
        val retriever = api.retrieveMostPopularMovies(pageNumber)
        retriever.enqueue(object: Callback<Movies> {
            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                response.isSuccessful.let {
                    val resp = Movies(response.body()?.total_pages?:0,
                        response.body()?.results?: mutableListOf(),
                        response.body()?.total_pages?:0,
                        response.body()?.total_results?:0
                    )
                    popularMovies.value = resp.results
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d("Please try again.", t.message.toString())
            }
        })
        return popularMovies
    }

    fun retrieveNowPlayingMoviesData(pageNumber:String) : MutableLiveData<List<Movie>> {
        val currentMovies: MutableLiveData<List<Movie>>  = MutableLiveData()
        val retrofit: Retrofit = Retrofit.
        Builder().
        baseUrl(baseUrl).
        addConverterFactory(GsonConverterFactory.create())
            .build()
        val api: MovieAPI = retrofit.create(MovieAPI::class.java)
        val retriever = api.retrieveNowPlayingMovies()
        retriever.enqueue(object: Callback<Movies> {
            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                response.isSuccessful.let {
                    val resp = Movies(response.body()?.page?:0,
                        response.body()?.results?: mutableListOf(),
                        response.body()?.total_pages?:0,
                        response.body()?.total_results?:0)
                    currentMovies.value = resp.results
                }
            }
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d("OHNOUNA", t.message.toString())
            }
        })
            return currentMovies
    }

    fun retrieveConfigurationData() : MutableLiveData<Configuration> {
        val configurationData:  MutableLiveData<Configuration> = MutableLiveData()
        val retrofit: Retrofit = Retrofit.Builder().
        baseUrl(baseUrl).
        addConverterFactory(GsonConverterFactory.create()).build()
        val api: MovieAPI = retrofit.create(MovieAPI::class.java)
        val retriever = api.retrieveConfiguration()
        retriever.enqueue(object: Callback<Configuration> {
            override fun onResponse(
                call: Call<Configuration>,
                response: Response<Configuration>
            ) {
                response.isSuccessful.let {
                    Configuration(response.body()?.change_keys?: mutableListOf(),
                    response?.body()?.images?: Images(mutableListOf(),
                        "",
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf(),
                        "",
                    mutableListOf())
                    )
                    configurationData.value = response?.body()
                }
            }
            override fun onFailure(call: Call<Configuration>, t: Throwable) {
                Log.d("OHNOUNA", t.message.toString())
            }
        })
        return configurationData
    }
    fun retrieveGenreIdsAndNames() : MutableLiveData<Genres> {
        val genresMap:   MutableLiveData<Genres> = MutableLiveData()
        val retrofit: Retrofit = Retrofit.Builder().
        baseUrl(baseUrl).
        addConverterFactory(GsonConverterFactory.create()).build()
        val api: MovieAPI = retrofit.create(MovieAPI::class.java)
        val retriever = api.getGenreIdsToNamesMap()
        retriever.enqueue(object: Callback<Genres> {
            override fun onResponse(
                call: Call<Genres>,
                response: Response<Genres>
            ) {
                response.isSuccessful.let {
                    response.body()?.genres?.let { genres -> Genres(genres) }
                }
                genresMap.value = response.body()
            }
            override fun onFailure(call: Call<Genres>, t: Throwable) {
                Log.d("OHNOUNA", t.message.toString())
            }
        })
        return genresMap
    }
}