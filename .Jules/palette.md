## 2024-05-20 - Emoji and Contact Semantics in Business Cards
**Learning:** Raw emoji and single-character icons (like 📷, f, 🎵, 💬, 📞) used as UI badges are read verbatim or incorrectly by screen readers. Wrapping them with `Modifier.clearAndSetSemantics` and assigning explicit, localized `contentDescription` text ensures reliable screen reader announcements without affecting visual rendering.
**Action:** Always apply `clearAndSetSemantics` with localized strings to emoji badges and icon indicators in Compose card lists.
