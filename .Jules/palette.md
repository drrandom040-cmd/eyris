## 2026-10-29 - Accessible Business Cards and Icon Semantics
**Learning:** In Jetpack Compose, emoji and short icon text (like '📷', 'f', '📞') inside clickable cards are pronounced literally by screen readers. Furthermore, clickable cards without an explicit `onClickLabel` announce generic click actions.
**Action:** Always resolve localized string resources prior to non-composable semantics lambdas and apply `Modifier.clearAndSetSemantics` to emoji/character indicators along with `onClickLabel` on `Modifier.clickable`.
