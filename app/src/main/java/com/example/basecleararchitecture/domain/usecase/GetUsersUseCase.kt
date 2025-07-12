package com.example.basecleararchitecture.domain.usecase

import com.example.basecleararchitecture.domain.model.User
import com.example.basecleararchitecture.domain.repository.UserRepository
import com.example.basecleararchitecture.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<User>>> {
        return userRepository.getUsers()
    }
}