package com.wappster.fitbook.ui.base

data class BaseResponse<T> (
        val status : Boolean,
        val data : T,
        val error : String
)