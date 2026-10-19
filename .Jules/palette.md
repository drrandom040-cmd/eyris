## 2025-05-18 - Business Card Accessibility with clearAndSetSemantics
**Learning:** Raw emojis and short character strings (like "📷", "f", "🎵", "💬", "📞") in Jetpack Compose are read verbatim by screen readers, creating confusing announcements. Overriding them with `Modifier.clearAndSetSemantics { contentDescription = ... }` and localizing descriptions provides a smooth screen reader experience.
**Action:** Always resolve `stringResource(...)` in composable scope and apply `clearAndSetSemantics` to emoji or single-character badges while providing `onClickLabel` on clickable cards.
