## 2023-11-20 - [TalkBack Semantics for Custom Emojis and Social Media Indicators]
**Learning:** Raw emojis (e.g., 📷, 📞, 🎵, 💬) and character indicators (e.g., 'f') within custom cards are typically spoken as literal character names or raw emoji descriptions by screen readers, creating a broken experience. Using `Modifier.clearAndSetSemantics` allows us to override these elements completely with localized string descriptions.
**Action:** When displaying icon-like emojis or character indicators, always apply `Modifier.clearAndSetSemantics` with a localized string resource.
