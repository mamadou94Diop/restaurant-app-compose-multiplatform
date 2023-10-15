import androidx.compose.runtime.Composable
import io.ktor.client.HttpClient
import navigation.AppScaffold
import theme.AppTheme

@Composable
fun App() {
    AppTheme {
        AppScaffold()
    }
}
expect fun getHttpClient() : HttpClient