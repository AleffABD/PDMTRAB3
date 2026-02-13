package com.example.todolist.data

import com.example.todolist.domain.Todo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {

    private val userId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw IllegalStateException("Usuário não autenticado")

    override suspend fun insert(title: String, description: String?, id: Long?) {
        val entity = id?.let {
            dao.getBy(it, userId)?.copy(
                title = title,
                description = description,
            )
        } ?: TodoEntity(
            userId = userId,
            title = title,
            description = description,
            isCompleted = false,
        )

        dao.insert(entity)
    }

    override suspend fun updateCompleted(id: Long, isCompleted: Boolean) {
        val existing = dao.getBy(id, userId) ?: return
        dao.insert(existing.copy(isCompleted = isCompleted))
    }

    override suspend fun delete(id: Long) {
        val existing = dao.getBy(id, userId) ?: return
        dao.delete(existing)
    }

    override fun getAll(): Flow<List<Todo>> {
        return dao.getAll(userId).map { entities ->
            entities.map {
                Todo(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isCompleted = it.isCompleted,
                )
            }
        }
    }

    override suspend fun getBy(id: Long): Todo? {
        return dao.getBy(id, userId)?.let {
            Todo(
                id = it.id,
                title = it.title,
                description = it.description,
                isCompleted = it.isCompleted,
            )
        }
    }
}
