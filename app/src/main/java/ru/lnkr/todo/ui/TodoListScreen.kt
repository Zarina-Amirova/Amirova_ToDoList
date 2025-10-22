package ru.lnkr.todo.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.lnkr.todo.VMCompositionLocal
import ru.lnkr.todo.model.TodoItem
import ru.lnkr.todo.repository.TodoViewModel
import ru.lnkr.todo.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onAddClick: () -> Unit = {},
    onItemClick: (TodoItem) -> Unit = {},
) {
    val vm = VMCompositionLocal.current
    val items by vm.items.collectAsStateWithLifecycle()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val itemsListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = topAppBarColors(
                    scrolledContainerColor = AppTheme.colors.backPrimary,
                    containerColor = AppTheme.colors.backPrimary,
                    titleContentColor = AppTheme.colors.labelPrimary,
                ),
                title = {
                    Text(
                        "Мои дела",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick() },
                shape = CircleShape,
                containerColor = AppTheme.colors.colorBlue,
                contentColor = AppTheme.colors.colorWhite
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        containerColor = AppTheme.colors.backPrimary,
    ) { innerPadding ->
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
                .padding(innerPadding)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.backSecondary,
                contentColor = AppTheme.colors.labelPrimary
            ),
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            LazyColumn(
                state = itemsListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 24.dp,
                        horizontal = 16.dp
                    ),
            ) {
                items(
                    items = items,
                    key = { item -> item.id }
                ) { item ->
                    TodoItemCard(
                        item = item,
                        onCompletedChange = { vm.completeItem(item.id) },
                        onItemClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TodoListScreenPreview() {
    val fakeViewModel = viewModel<TodoViewModel>()
    CompositionLocalProvider(VMCompositionLocal provides fakeViewModel) {
        AppTheme {
            TodoListScreen()
        }
    }
}