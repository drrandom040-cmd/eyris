## 2026-05-31 - [Improve BusinessCard Accessibility]
**Learning:** In Jetpack Compose, applying `Modifier.semantics { contentDescription = "..." }` to a `Text` composable overrides the screen reader's announcement of the underlying text; descriptions for data-bearing components must include the value (e.g., 'Phone: $number') to maintain accessibility. Social handle indicators using emojis or single letters need explicit descriptions.
**Action:** Always provide `contentDescription` via `Modifier.semantics` for accessibility in data-bearing or icon-only components.
