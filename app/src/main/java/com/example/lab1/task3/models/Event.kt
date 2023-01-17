package com.example.lab1.task3.models

import android.app.Application

data class Event(val status: Status, val data: String?, val code: Int?) {

    companion object {
        fun loading(): Event {
            return Event(Status.LOADING, null, null)
        }

        fun success(message: String?): Event {
            return Event(Status.SUCCESS, message, null)
        }

        fun error(message: String?, statusCode: Int?): Event {
            return Event(Status.ERROR, message, statusCode)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}