package com.example.todolist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TodoEntity)

    @Delete
    suspend fun delete(entity: TodoEntity)

    @Query("SELECT * FROM todos WHERE userId = :userId")
    fun getAll(userId: String): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE id = :id AND userId = :userId")
    suspend fun getBy(id: Long, userId: String): TodoEntity?
}
