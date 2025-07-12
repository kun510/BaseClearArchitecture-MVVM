package com.example.basecleararchitecture.domain.usecase

import com.example.basecleararchitecture.domain.model.User
import com.example.basecleararchitecture.domain.model.Company
import com.example.basecleararchitecture.domain.model.Address
import com.example.basecleararchitecture.domain.model.Geo
import com.example.basecleararchitecture.domain.repository.UserRepository
import com.example.basecleararchitecture.utils.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class GetUsersUseCaseTest {
    
    @Mock
    private lateinit var userRepository: UserRepository
    
    private lateinit var getUsersUseCase: GetUsersUseCase
    
    private val sampleUsers = listOf(
        User(
            id = 1,
            name = "John Doe",
            username = "johndoe",
            email = "john@example.com",
            phone = "123-456-7890",
            website = "johndoe.com",
            company = Company("Acme Corp", "synergize", "business"),
            address = Address("123 Main St", "Suite 1", "Anytown", "12345", 
                Geo("40.7128", "-74.0060"))
        )
    )
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getUsersUseCase = GetUsersUseCase(userRepository)
    }
    
    @Test
    fun `invoke should return success when repository returns success`() = runBlocking {
        // Given
        val expectedResource = Resource.Success(sampleUsers)
        `when`(userRepository.getUsers()).thenReturn(flowOf(expectedResource))
        
        // When
        val result = getUsersUseCase().toList()
        
        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Success)
        assertEquals(sampleUsers, (result[0] as Resource.Success).data)
    }
    
    @Test
    fun `invoke should return error when repository returns error`() = runBlocking {
        // Given
        val expectedResource = Resource.Error<List<User>>("Network error")
        `when`(userRepository.getUsers()).thenReturn(flowOf(expectedResource))
        
        // When
        val result = getUsersUseCase().toList()
        
        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)
        assertEquals("Network error", (result[0] as Resource.Error).message)
    }
    
    @Test
    fun `invoke should return loading when repository returns loading`() = runBlocking {
        // Given
        val expectedResource = Resource.Loading<List<User>>()
        `when`(userRepository.getUsers()).thenReturn(flowOf(expectedResource))
        
        // When
        val result = getUsersUseCase().toList()
        
        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Loading)
    }
}