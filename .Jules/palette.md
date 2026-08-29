## 2024-05-31 - Overriding Emoji Semantics in Jetpack Compose
**Learning:** Using `Modifier.clearAndSetSemantics` on emoji or single-character icon labels (such as '📷', 'f', '🎵', '💬') replaces raw emoji or letter readings with localized platform descriptions for screen readers.
**Action:** Always resolve localized string resources outside semantics lambdas and apply `clearAndSetSemantics { contentDescription = label }` to icon text composables.
