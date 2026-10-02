## 2025-05-20 - Accessible Emoji and Short-Text Indicators in Compose Cards

**Learning:** Unlabeled text composables containing emojis or short symbol characters (e.g., '📷', 'f', '🎵', '💬', '📞') cause screen readers like TalkBack to read raw unicode character names or single letters without context.

**Action:** Wrap emoji or symbol indicator composables with `Modifier.clearAndSetSemantics { contentDescription = localizedLabel }` using pre-resolved string resources to provide clear screen reader announcements.
