# Palette's Journal

Critical learnings and insights regarding UX and accessibility.

## 2023-11-20 - [Jetpack Compose Screen Reader Semantics & Emojis]
**Learning:** Raw emojis (e.g., 📷, 🎵, 💬, 📞) and single-letter characters (e.g., f) are pronounced literally or awkwardly by screen readers, creating a highly disjointed user experience. In Jetpack Compose, setting parent containers to merge descendants hides all default children semantic elements, which requires the parent custom description to explicitly convey all merged contextual details to prevent data loss.
**Action:** Always use `Modifier.clearAndSetSemantics` to override raw text and emojis inside cards with fully translatable localized string resources. Ensure parent layouts with merged descendants specify a complete content description including category and dynamic numerical state.
