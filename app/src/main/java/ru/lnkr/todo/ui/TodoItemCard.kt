package ru.lnkr.todo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ru.lnkr.todo.model.Importance
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.ui.theme.AppTheme

@Composable
fun TodoItemCard(
    item: TodoItem,
    onCompletedChange: (Boolean) -> Unit,
    onItemClick: (TodoItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onItemClick(item) },
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.backSecondary)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = onCompletedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = AppTheme.colors.colorGreen,
                    uncheckedColor = AppTheme.colors.supportSeparator,
                    checkmarkColor = AppTheme.colors.backSecondary
                )
            )
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = item.text,
                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else null,
                    color = if (item.isCompleted) AppTheme.colors.labelTertiary else AppTheme.colors.labelPrimary
                )
                val importanceText = when (item.importance) {
                    Importance.LOW -> "Низкая"
                    Importance.NONE -> "Обычная"
                    Importance.HIGH -> "Высокая"
                }
                Text(
                    text = "Важность: $importanceText",
                    color = AppTheme.colors.labelTertiary
                )
            }
        }
    }
}