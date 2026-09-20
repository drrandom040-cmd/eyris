## 2024-05-18 - Jetpack Compose Emoji & Short Character Accessibility Semantics
**Learning:** Using `Modifier.clearAndSetSemantics` on emoji or short-text indicators (such as '📷', 'f', '🎵', '💬', '📞') inside cards overrides default text/character readings with localized descriptions (e.g., `R.string.social_instagram`), preventing screen readers from pronouncing the raw character or emoji.
**Action:** Always wrap emoji or symbol text elements in `Modifier.clearAndSetSemantics` with a localized `contentDescription` when used as visual indicators.
