package ru.lnkr.todo.repository

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.lnkr.todo.model.Importance
import ru.lnkr.todo.model.TodoItem
import java.util.Calendar
import java.util.Date

open class TodoViewModel : ViewModel() {
    private var _items = MutableStateFlow<List<TodoItem>>(mutableListOf())
    val items: StateFlow<List<TodoItem>>
        get() = _items.asStateFlow()

    private var _isVisibleCompletedItems = MutableStateFlow(false)
    val isVisibleCompletedItems: StateFlow<Boolean>
        get() = _isVisibleCompletedItems

    init {
        saveItem(
            TodoItem(
                id = "1",
                text = "Помыть посуду",
                importance = Importance.HIGH,
                createdAt = Calendar.getInstance().apply {
                    set(2024, Calendar.JANUARY, 10)
                }.time,
                modifiedAt = Calendar.getInstance().apply {
                    set(2024, Calendar.JANUARY, 10)
                }.time
            )
        )

        saveItem(
            TodoItem(
                id = "2",
                text = "Погулять с собакой",
                importance = Importance.HIGH,
                createdAt = Calendar.getInstance().apply {
                    set(2024, Calendar.FEBRUARY, 14)
                }.time,
                isCompleted = false,
                modifiedAt = Calendar.getInstance().apply {
                    set(2024, Calendar.FEBRUARY, 15)
                }.time,
                deadline = Calendar.getInstance().apply {
                    set(2024, Calendar.FEBRUARY, 15)
                }.time
            )
        )
    }

    fun saveItem(item: TodoItem) {
        _items.value = _items.value.map {
            if (it.id == item.id) {
                item
            } else {
                it
            }
        } + if (_items.value.none { it.id == item.id }) listOf(item) else emptyList()
    }

    fun deleteItem(itemId: String) {
        _items.value = _items.value.filterNot { it.id == itemId }
    }

    fun completeItem(itemId: String) {
        _items.value = _items.value.map {
            if (it.id == itemId) {
                it.copy(
                    isCompleted = !it.isCompleted,
                    modifiedAt = Date()
                )
            } else {
                it
            }
        }
    }

    fun setVisibilityOfCompletedItems(isVisible: Boolean) {
        this._isVisibleCompletedItems.value = isVisible
    }
}