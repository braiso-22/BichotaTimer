import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Typography

@Composable
actual fun AppTheme(darkTheme: Boolean, dynamicColor: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkScheme else lightScheme,
        typography = Typography(),
        content = content
    )
}
