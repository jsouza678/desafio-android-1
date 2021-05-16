package com.picpay.agenda.presentation.home

import androidx.appcompat.app.AppCompatActivity
import com.picpay.agenda.R
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

@KoinApiExtension
class AgendaActivity : AppCompatActivity(R.layout.activity_agenda), KoinComponent {

    override fun onResume() {
        super.onResume()
    }
}
