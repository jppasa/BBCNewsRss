package dev.jpvillegas.bbcnewsrss.domain.repository

interface SourceRepository {
    fun sources() : List<String>
}