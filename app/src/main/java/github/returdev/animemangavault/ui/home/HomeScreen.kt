package github.returdev.animemangavault.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.returdev.animemangavault.R
import github.returdev.animemangavault.domain.model.reduced.ReducedVisualMedia
import github.returdev.animemangavault.ui.core.ReducedItem
import github.returdev.animemangavault.ui.core.VisualSearchBar
import github.returdev.animemangavault.ui.theme.AnimeMangaVaultTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel){

    Column(Modifier.verticalScroll(rememberScrollState()))
    {
        HomeHeader()
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalListContainer(
            headerText = stringResource(R.string.top_anime),
            visualMediaList = emptyList(),
            homeUiState = HomeUiState.Loading,
        ) {/*TODO Insert retry method*/}
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalListContainer(
            headerText = stringResource(R.string.top_manga),
            visualMediaList = emptyList(),
            homeUiState = HomeUiState.Loading,
        ) {/*TODO Insert retry method*/}
        Spacer(modifier = Modifier.height(24.dp))
        HorizontalListContainer(
            headerText = stringResource(R.string.this_season),
            visualMediaList = emptyList(),
            homeUiState = HomeUiState.Loading
        ) {/*TODO Insert retry method*/}
    }
}


@Composable
fun HomeHeader(){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        VisualSearchBar {/*TODO Insert click method*/}
    }
}

@Composable
fun HorizontalListContainer(
    headerText : String,
    visualMediaList : List<ReducedVisualMedia>,
    homeUiState: HomeUiState,
    retry: () -> Unit
    ){
    Column {
        Text(text = headerText, modifier = Modifier.padding(start = 16.dp))
        when(homeUiState){
            is HomeUiState.Error -> ErrorLayout(errorState = homeUiState, retry = retry )
            HomeUiState.Loading -> LoadingHorizontalList()
            is HomeUiState.Success -> HorizontalList(visualMediaList = visualMediaList)
        }
    }

}

@Composable
private fun ErrorLayout(
    errorState : HomeUiState.Error,
    retry : () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {

            Icon(
                painter = painterResource(R.drawable.ic_error),
                contentDescription = "Error icon"
            )
            Text(text = stringResource(errorState.errorResource))

        }
        if (errorState.isRetryAvailable){
            Button(onClick = { retry() }) {
                Text(text = "Retry")
            }
        }

    }
}

@Composable
private fun HorizontalList(visualMediaList : List<ReducedVisualMedia>){
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(visualMediaList){
            ReducedItem(reducedVisualMedia = it){}
        }
    }
}

@Composable
private fun LoadingHorizontalList(){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
        LoadingItem(Modifier.padding(start = 16.dp, end = 8.dp))
        LoadingItem(Modifier.padding(horizontal = 8.dp))
        LoadingItem(Modifier.padding(start = 8.dp))
    }
}

@Composable
private fun LoadingItem(modifier: Modifier){
    Column(
        modifier = modifier.width(IntrinsicSize.Min)) {
        Box(modifier = Modifier
            .width(60.dp)
            .height(20.dp)
            .background(Color.Gray))
        Box(modifier = Modifier
            .width(125.dp)
            .height(187.5.dp)
            .padding(vertical = 8.dp)
            .background(Color.Gray))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.Gray))
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewHomeScreen(){
    AnimeMangaVaultTheme {
        HomeScreen(viewModel = HomeViewModel())
    }
}