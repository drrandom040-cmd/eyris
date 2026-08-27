## 2024-05-20 - Accessible Social Media Indicators & Card Semantics in Jetpack Compose
**Learning:** Using `clearAndSetSemantics` on emoji/short-character social media indicators (`📷`, `f`, `🎵`, `💬`) prevents screen readers from reading confusing literal characters, while providing explicit `onClickLabel` on clickable cards gives screen reader users actionable context on card interaction.
**Action:** Always wrap emoji or symbol indicators with `clearAndSetSemantics` and localized string descriptions, and supply an `onClickLabel` parameter when making card containers clickable in Compose.
