## 2025-05-18 - Compose Emoji Accessibility Semantics
**Learning:** In Jetpack Compose, raw emojis and single-letter characters used as contact or social icons (e.g., '📷', 'f', '🎵', '💬', '📞') are pronounced awkwardly or literally by screen readers unless explicitly overridden with clear, localized content descriptions.
**Action:** Use `Modifier.clearAndSetSemantics` for emoji/icon indicators or `Modifier.semantics` for dynamic data fields (resolving string resources outside non-composable semantics lambdas) to provide translatable, screen-reader-friendly descriptions.
