## 2025-06-02 - [Social Indicator Accessibility]
**Learning:** In Jetpack Compose, applying `Modifier.semantics { contentDescription = "..." }` to a `Text` composable overrides the screen reader's announcement of the underlying text; descriptions for data-bearing components must include the value (e.g., 'Phone: $number') to maintain accessibility.
**Action:** Always provide explicit `contentDescription` for emoji-based or icon-only indicators to ensure they are properly announced by screen readers.
