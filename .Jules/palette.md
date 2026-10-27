## 2025-05-18 - Clear Semantics for Emoji Social Indicators in Compose Cards

**Learning:** Raw emoji indicators and single-character icons (such as '📷', 'f', '🎵', '💬', '📞') in Jetpack Compose text components are read verbatim or by Unicode symbol names by TalkBack and screen readers, leading to confusing auditory output for visually impaired users.
**Action:** Use `Modifier.clearAndSetSemantics { contentDescription = localizedString }` on raw emoji or character text components inside cards to override default screen reader pronunciation with clear localized labels.
