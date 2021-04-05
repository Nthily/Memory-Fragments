package com.example.fragmentsofmemory

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun NormalTab(name:String) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = Color(0xFF8EB1CF),
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
            .clickable {
                Log.d(ContentValues.TAG, "12345")
            },
        elevation = 8.dp
    ) {
        Text(
            text = "$name",
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.W800,
            style = MaterialTheme.typography.caption,
            color = Color.White,
            )
    }
}