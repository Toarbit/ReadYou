package me.ash.reader.ui.page.startup

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ireward.htmlcompose.HtmlText
import kotlinx.coroutines.launch
import me.ash.reader.R
import me.ash.reader.ui.component.DisplayText
import me.ash.reader.ui.component.DynamicSVGImage
import me.ash.reader.ui.component.Tips
import me.ash.reader.ui.ext.DataStoreKeys
import me.ash.reader.ui.ext.dataStore
import me.ash.reader.ui.ext.put
import me.ash.reader.ui.page.common.RouteName
import me.ash.reader.ui.svg.SVGString
import me.ash.reader.ui.svg.WELCOME

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartupPage(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {},
        content = {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(64.dp))
                    DisplayText(text = stringResource(R.string.welcome), desc = "")
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    DynamicSVGImage(
                        modifier = Modifier.padding(horizontal = 60.dp),
                        svgImageString = SVGString.WELCOME,
                        contentDescription = stringResource(R.string.color_and_style),
                    )
                }
                item {
                    Tips(
                        modifier = Modifier.padding(top = 40.dp),
                        text = stringResource(R.string.agree_terms),
                    )
                }
                item {
                    TextButton(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        onClick = {
                            context.let {
                                it.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(it.getString(R.string.terms_link))
                                    )
                                )
                            }
                        }
                    ) {
                        HtmlText(
                            text = stringResource(R.string.view_terms),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.outline,
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        },
        bottomBar = {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(24.dp),
//                horizontalArrangement = Arrangement.End,
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(RouteName.FEEDS) {
                        launchSingleTop = true
                    }
                    scope.launch {
                        context.dataStore.put(DataStoreKeys.IsFirstLaunch, false)
                    }
                },
                icon = {
                    Icon(
                        Icons.Rounded.CheckCircleOutline,
                        stringResource(R.string.agree_and_continue)
                    )
                },
                text = { Text(text = stringResource(R.string.agree_and_continue)) },
            )
        }
    )
}