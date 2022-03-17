package uz.context.examenapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.button.MaterialButton
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.context.examenapplication.R
import uz.context.examenapplication.activities.database.CardEntity
import uz.context.examenapplication.activities.database.MyDatabase
import uz.context.examenapplication.activities.model.Cards
import uz.context.examenapplication.activities.networking.RetrofitHttp

class AddNewCardActivity : AppCompatActivity() {

    private lateinit var textNumbers: TextView
    private lateinit var textHolderName: TextView
    private lateinit var textDate: TextView
    private lateinit var editCardNumbers: AppCompatEditText
    private lateinit var myDatabase: MyDatabase
    private lateinit var editDate1: AppCompatEditText
    private lateinit var editCvv: AppCompatEditText
    private lateinit var editDate2: AppCompatEditText
    private lateinit var editHolderName: AppCompatEditText
    private lateinit var btnAdd: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_card)

        initViews()

    }

    private fun initViews() {
        textNumbers = findViewById(R.id.textCardNumbers)
        textHolderName = findViewById(R.id.textCardHolderName)
        textDate = findViewById(R.id.textDate)
        editCardNumbers = findViewById(R.id.editCardNumbers)
        editDate1 = findViewById(R.id.editDate1)
        editDate2 = findViewById(R.id.editDate2)
        editHolderName = findViewById(R.id.editHolderName)
        btnAdd = findViewById(R.id.btnAddNewCard)
        editCvv = findViewById(R.id.editCvv)
        myDatabase = MyDatabase.getDatabase(this)!!

        editTexts()

        btnAdd.setOnClickListener {
            val cardNumbers = editCardNumbers.text.toString().trim()
            val editDate1 = editDate1.text.toString().trim()
            val editDate2 = editDate2.text.toString().trim()
            val editCvv = editCvv.text.toString().trim()
            val cardHolderName = editHolderName.text.toString()

            if (cardNumbers.isEmpty() ||
                editDate1.isEmpty() || editDate2.isEmpty()
                || editCvv.isEmpty() || cardHolderName.isEmpty() || editDate2.isEmpty()
            ) {
                Toasty.error(this, "Please enter some data!", Toasty.LENGTH_LONG).show()
            } else if (cardNumbers.length < 16) {
                Toasty.error(this, "Card number must be 16", Toasty.LENGTH_LONG).show()
            } else {
                postCard(cardNumbers, cardHolderName, editDate1, editDate2)
                Toasty.success(this, "Success", Toasty.LENGTH_LONG).show()
            }
        }
    }

    private fun postCard(
        cardNumbers: String,
        holderName: String,
        editDate1: String,
        editDate2: String
    ) {
        val card = Cards(cardNumbers, "", holderName, "Visa", editDate1, "")
        RetrofitHttp.postService.postCard(card).enqueue(object : Callback<Cards> {
            override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                if (response.isSuccessful) {
                    saveData()
                }
            }

            override fun onFailure(call: Call<Cards>, t: Throwable) {
                saveData()
            }
        })
    }

    private fun editTexts() {
        editCardNumbers.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textNumbers.text = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        editHolderName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textHolderName.text = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        editDate1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textDate.text = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun saveData() {
        val model = CardEntity(
            0,
            editCardNumbers.text.toString(),
            editCvv.text.toString(),
            textHolderName.text.toString(),
            "Visa",
            textDate.text.toString()
        )

        MyDatabase.getDatabase(applicationContext)!!.getDao().insertCard(model)

        Toasty.success(this, "Saved!", Toasty.LENGTH_LONG).show()
        finish()

    }
}
