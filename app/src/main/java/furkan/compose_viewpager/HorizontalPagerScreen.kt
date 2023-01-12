package furkan.compose_viewpager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import furkan.compose_viewpager.ui.theme.App_bg
import furkan.compose_viewpager.ui.theme.Tab_bg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerScreen() {

    val items = getItems()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(App_bg)
            .fillMaxSize()
    ) {
        HorizontalTabs(items = items, pagerState = pagerState, scope = coroutineScope)
        Column(Modifier.padding(16.dp)) {
            HorizontalPager(
                count = items.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { currentPage ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Image(
                        painter = painterResource(id = items[currentPage].flag),
                        modifier = Modifier
                            .width(350.dp)
                            .height(250.dp),
                        contentDescription = "county_flag"
                    )

                    Text(
                        text = items[currentPage].capital,
                        style = MaterialTheme.typography.h2
                    )

                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                    )
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.Main) {
                                pagerState.animateScrollToPage(page = 0)
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Tab_bg)
                    ) {
                        Text(text = "Scroll to First", color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalTabs(
    items: List<Countries>,
    pagerState: PagerState,
    scope: CoroutineScope
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        items.forEachIndexed { index, item ->
            Tab(
                modifier = Modifier
                    .background(Tab_bg)
                    .height(50.dp)
                    .clip(CircleShape),
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch(Dispatchers.Main) {
                        pagerState.animateScrollToPage(page = index)
                    }
                }
            ) {
                Text(text = item.code, style = MaterialTheme.typography.body1, color = Color.White)
            }
        }
    }
}
