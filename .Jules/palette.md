## 2026-09-25 - Jetpack Compose Emoji and Icon Character Accessibility

**Learning:** Raw emoji and single-letter characters (e.g. '📷', 'f', '🎵', '💬', '📞') used as icons in Jetpack Compose UI components are read verbatim or inappropriately by screen readers like TalkBack unless overridden. Using `Modifier.clearAndSetSemantics` with localized string resources provides clean, accessible text descriptions without altering visual layout.

**Action:** Whenever using emojis or single-character icon indicators, apply `Modifier.clearAndSetSemantics { contentDescription = stringResource(...) }` with pre-resolved string resources in Composable scope.
