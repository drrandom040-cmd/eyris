## 2025-05-18 - Clear Semantics for Emoji Text Indicators
**Learning:** Emoji text indicators (like "📷", "f", "🎵", "💬", "📞") in Jetpack Compose UI components are spoken verbatim or unhelpfully by screen readers. Overriding their semantics with `Modifier.clearAndSetSemantics` using localized descriptions ensures screen readers announce the exact intended meaning without character noise.
**Action:** When using emojis or abbreviated characters for status/social indicators, always clear default semantics and set explicit localized content descriptions.
