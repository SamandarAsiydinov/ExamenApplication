package uz.context.examenapplication.activities.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import uz.context.examenapplication.activities.model.Cards

interface ApiService {

    @GET("cards")
    fun getAllCards(): Call<ArrayList<Cards>>

    @POST("cards")
    fun postCard(@Body cards: Cards): Call<Cards>
}