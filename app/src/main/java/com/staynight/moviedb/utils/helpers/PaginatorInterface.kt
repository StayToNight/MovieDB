package com.staynight.moviedb.utils.helpers

interface PaginatorInterface<Key, Item> {
    fun loadNextItems()
    fun reset()
}