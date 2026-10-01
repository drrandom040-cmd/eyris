## 2025-03-22 - Jetpack Compose Emoji and Character Semantic Overrides
**Learning:** In Jetpack Compose, raw emojis and short-text indicators (like '📷', 'f', '🎵', '💬', '📞') are read literally by screen readers (e.g., "camera emoji", "f"). Using `Modifier.clearAndSetSemantics { contentDescription = label }` overrides default character readings with localized accessibility strings without altering visual presentation.
**Action:** Always wrap emoji/character indicator text nodes in `clearAndSetSemantics` with localized string resources resolved in the Composable scope.
