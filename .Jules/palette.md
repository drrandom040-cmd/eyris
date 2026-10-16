## 2024-05-20 - Accessible Icon & Emoji Indicators in Card Components
**Learning:** Overriding emoji/character indicators (e.g. '📞', '📷', 'f') in Compose list cards with `Modifier.clearAndSetSemantics` prevents screen readers from pronouncing raw characters and provides readable localized strings.
**Action:** Always wrap emoji or symbol text elements in `clearAndSetSemantics` with localized strings, and provide an explicit `onClickLabel` on clickable card containers.
