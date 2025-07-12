package com.example.basecleararchitecture.data.repository

import com.example.basecleararchitecture.data.remote.ApiService
import com.example.basecleararchitecture.domain.model.User
import com.example.basecleararchitecture.domain.repository.UserRepository
import com.example.basecleararchitecture.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    
    override suspend fun getUsers(): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                response.body()?.let { users ->
                    emit(Resource.Success(users))
                } ?: emit(Resource.Error("No data found"))
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }
    
    override suspend fun getUserById(id: Int): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.getUserById(id)
            if (response.isSuccessful) {
                response.body()?.let { user ->
                    emit(Resource.Success(user))
                } ?: emit(Resource.Error("User not found"))
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }
}