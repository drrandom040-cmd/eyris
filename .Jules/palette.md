## 2025-05-18 - Clear Semantics for Emoji Social Indicators and Ratings
**Learning:** Overriding emoji/character indicators ('📷', 'f', '🎵', '💬') using `Modifier.clearAndSetSemantics { contentDescription = ... }` in Jetpack Compose prevents screen readers from mispronouncing uninformative raw glyphs and provides clear, localized platform titles.
**Action:** Always wrap icon-like text characters in `clearAndSetSemantics` with localized strings when representing social links or status indicators in card components.
