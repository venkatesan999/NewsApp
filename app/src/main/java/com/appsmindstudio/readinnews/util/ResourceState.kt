package com.appsmindstudio.readinnews.util

sealed class ResourceState<T> {
    class Loading<T> : ResourceState<T>()
    class Success<T>(val data: T) : ResourceState<T>()
    class Error<T>(val error: String) : ResourceState<T>()
}