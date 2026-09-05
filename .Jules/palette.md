## 2024-05-20 - Emoji and Short-Text Indicator Accessibility in Jetpack Compose
**Learning:** Raw emoji and single-letter text components (such as '📷', 'f', '🎵', '💬', '📞') used as icons in Jetpack Compose cards are pronounced literally or as raw characters by screen readers, degrading usability.
**Action:** Use `Modifier.clearAndSetSemantics { contentDescription = localizedString }` on icon-like text or emoji elements to override default character pronunciation with clear, localized descriptions.
