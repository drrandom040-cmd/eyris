## 2025-05-18 - Accessibility Semantics for Emoji and Short-Text Indicators
**Learning:** In Jetpack Compose, raw character indicators (e.g., '📷', 'f', '🎵', '💬', '📞') inside components are read verbatim or incorrectly by screen readers. Using `Modifier.clearAndSetSemantics` with a localized string replaces the character pronunciation with a meaningful description (e.g., "Instagram", "Phone: +123456789") for accessibility tools.
**Action:** Always wrap emoji or symbol text badges with `Modifier.clearAndSetSemantics { contentDescription = ... }` using localized string resources.
