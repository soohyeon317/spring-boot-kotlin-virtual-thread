package com.vespexx.signal.serviceapi.util

data class CursorResult<T, Cursor>(
    val items: List<T>,
    val cursor: Cursor?,
    val isLast: Boolean,
)
