package com.example.myadmi.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.unit.dp


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import com.example.myadmi.ViewModel.ColorViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: ColorViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val colors by viewModel.colors.collectAsState(initial = emptyList())
    val pendingSync by viewModel.pendingSync.collectAsState()
    val scope = rememberCoroutineScope()

    // Animation states
    val buttonScale = remember { Animatable(1f) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Animated background gradient
        val gradient = Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Enhanced TopAppBar
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Palette",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Lab",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    // Animated sync button
                    IconButton(
                        onClick = {
                            scope.launch {
                                buttonScale.animateTo(0.8f, spring())
                                buttonScale.animateTo(1f, spring())
                                viewModel.syncColors()
                            }
                        },
                        modifier = Modifier.graphicsLayer {
                            scaleX = buttonScale.value
                            scaleY = buttonScale.value
                        }
                    ) {
                        BadgedBox(
                            badge = {
                                if (pendingSync > 0) {
                                    Badge {
                                        Text(pendingSync.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = "Sync",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )

            // Animated grid of colors
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(
                    items = colors,
                    key = { it.id }
                ) { colorEntry ->
                    AnimatedColorItem(colorEntry)
                }
            }

            // Enhanced floating action button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            buttonScale.animateTo(0.9f, spring())
                            buttonScale.animateTo(1f, spring())
                            viewModel.addRandomColor()
                        }
                    },
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = buttonScale.value
                            scaleY = buttonScale.value
                        }
                        .shadow(8.dp, CircleShape),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Icon(Icons.Default.Add, "Add Color")
                        Spacer(Modifier.width(8.dp))
                        Text("Add Color", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}

