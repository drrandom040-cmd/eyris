## 2025-05-18 - Overriding Emoji Semantics in Component Cards
**Learning:** Using `Modifier.clearAndSetSemantics` on emoji or short text indicators (such as '📷', 'f', '🎵', '💬', '📞') inside cards overrides default text/character readings with localized descriptions (e.g., `R.string.social_instagram`), preventing screen readers from pronouncing raw characters or emojis.
**Action:** Always wrap emoji/character indicators with `clearAndSetSemantics` and provide localized string resources for screen reader users.
