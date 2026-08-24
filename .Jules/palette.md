## 2024-05-20 - Jetpack Compose Emoji Semantics for Screen Readers
**Learning:** Raw emojis (e.g. 📷, 📞, 💬) in Jetpack Compose `Text` components are pronounced literally by TalkBack/screen readers (e.g., "camera emoji"). Using `Modifier.clearAndSetSemantics { contentDescription = localizedString }` replaces literal emoji readings with descriptive localized labels.
**Action:** Always wrap emoji indicators or short visual badges in `clearAndSetSemantics` with a clear, localized `contentDescription`.
