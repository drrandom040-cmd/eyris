## 2026-06-23 - Accessibility for emojis and formatted strings
**Learning:** In Jetpack Compose, screen readers may not correctly announce emojis or may provide confusing announcements for formatted strings (like "Phone: [number]"). Using `Modifier.semantics { contentDescription = ... }` allows developers to provide clear, localized descriptions that improve the experience for users relying on assistive technologies.
**Action:** Always wrap emojis and complex data strings in a `semantics` modifier with a clear `contentDescription` using string resources for localization.
