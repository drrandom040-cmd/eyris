## 2025-02-18 - Overriding Emoji Semantics in Compose Cards
**Learning:** Raw emoji and single-character icons (such as '📷', 'f', '🎵', '💬', '📞') used as social and contact indicators in Compose cards are read literal character names by screen readers. Using `Modifier.clearAndSetSemantics { contentDescription = ... }` replaces raw character pronunciations with clear localized platform descriptions without affecting visual display.
**Action:** Always wrap emoji or symbol indicators with `Modifier.clearAndSetSemantics` and localized string resources when rendering icon-like text elements.
