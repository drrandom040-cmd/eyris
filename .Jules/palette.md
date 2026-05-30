## 2024-05-22 - [Jetpack Compose contentDescription Overrides]
**Learning:** In Jetpack Compose, applying `Modifier.semantics { contentDescription = "..." }` to a `Text` composable overrides the announcement of the actual text content. If the text itself contains important information (like a phone number), the `contentDescription` must include that information.
**Action:** When adding accessibility labels to `Text` components that include data, ensure the `contentDescription` either concatenates the label with the data or only labels the decorative parts (like emojis) separately.
