## 2025-02-15 - Enhancing Mobile Card Accessibility & Eliminating Redundant Semantic Announcements

**Learning:** When building list items (like Business Cards) in Jetpack Compose, using short character abbreviations or emojis (e.g., 'f', '📷') for social handles causes screen readers to recite raw/confusing characters. Additionally, utilizing custom semantics on container/parent rows (like a rating row) entirely hides its children's semantic content, which can omit crucial details (like the business category) if not explicitly bundled in the parent's content description.

**Action:** Always override emoji and short character indicators with explicit, localized string resources via `Modifier.clearAndSetSemantics`. Ensure parent container custom semantic descriptions explicitly aggregate all essential details of their children (such as business category, rating, and review count) to provide screen readers with a continuous and fully translatable announcement.
