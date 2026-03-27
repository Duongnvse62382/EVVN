package com.king.evvn.presentation.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.king.evvn.R
import com.king.evvn.presentation.navigation.HomeTab

@Composable
fun HomeScreen(
    currentTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
) {
    Scaffold(
        bottomBar = {
            BottomBar(
                currentTab = currentTab,
                onTabSelected = onTabSelected
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            when (currentTab) {
                is HomeTab.Home -> {
                    HeaderSection(modifier = Modifier.weight(1f))
                    MapSection(modifier = Modifier.weight(3f)) {
                        // Handle station selection
                    }
                    SearchSection(modifier = Modifier.weight(1f))
                }
                is HomeTab.Favorite -> {
                    Text("Favorites")
                }
                is HomeTab.News -> {
                    Text("News")
                }
                is HomeTab.Map -> {
                    Text("Routes")
                }
                is HomeTab.More -> {
                    Text("More")
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    currentTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentTab is HomeTab.Home,
            onClick = { onTabSelected(HomeTab.Home) },
            icon = { Icon(Icons.Default.LocationOn, null) },
            label = { Text("Locate") }
        )

        NavigationBarItem(
            selected = currentTab is HomeTab.Favorite,
            onClick = { onTabSelected(HomeTab.Favorite) },
            icon = { Icon(Icons.Default.Favorite, null) },
            label = { Text("Favorites") }
        )

        NavigationBarItem(
            selected = currentTab is HomeTab.Map,
            onClick = { onTabSelected(HomeTab.Map) },
            icon = { Icon(Icons.Default.Map, null) },
            label = { Text("Routes") }
        )

        NavigationBarItem(
            selected = currentTab is HomeTab.News,
            onClick = { onTabSelected(HomeTab.News) },
            icon = { Icon(Icons.AutoMirrored.Filled.Article, null) },
            label = { Text("News") }
        )

        NavigationBarItem(
            selected = currentTab is HomeTab.More,
            onClick = { onTabSelected(HomeTab.More) },
            icon = { Icon(Icons.Default.MoreHoriz, null) },
            label = { Text("More") }
        )
    }
}

@Composable
fun MapSection(
    modifier: Modifier,
    onStationClick: (String) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(16.0, 108.0),
            5f
        )
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(
                position = LatLng(10.7769, 106.7009)
            ),
            title = "EV Charger",
            onClick = {
                onStationClick("station_001")
                true
            }
        )
    }
}

@Composable
fun HeaderSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.9f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_google),
                contentDescription = null,
                tint = Color(0xFF4CAF50)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "EVCS.VN",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            "Electric Vehicle Charging Stations",
            fontSize = 14.sp
        )
        Text(
            "Find charging stations in real-time",
            fontSize = 14.sp
        )
    }
}

@Composable
fun SearchSection(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Search by location...")
            },
            shape = RoundedCornerShape(30.dp)
        )
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            )
        ) {
            Text("OR FIND STATIONS NEARBY")
        }
    }
}
