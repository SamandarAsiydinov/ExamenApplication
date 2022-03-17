package uz.context.examenapplication.activities.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CardTable")
data class CardEntity(

    @PrimaryKey
    var id: Int? = null,
    val cardNumber: String,
    val cardCvv: String,
    val cardHolder: String,
    val cardName: String,
    val cardDate: String
)