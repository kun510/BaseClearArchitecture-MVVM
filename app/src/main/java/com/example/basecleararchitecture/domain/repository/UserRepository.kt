package com.example.basecleararchitecture.domain.repository

import com.example.basecleararchitecture.domain.model.User
import com.example.basecleararchitecture.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUsers(): Flow<Resource<List<User>>>
    suspend fun getUserById(id: Int): Flow<Resource<User>>
}