package com.example.tycep_fe.ui

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class ReverseLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    init{
        this.reverseLayout=true
    }
}
