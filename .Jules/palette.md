## 2024-05-13 - Accessible Data Labels in Compose
**Learning:** In Jetpack Compose, applying `Modifier.semantics { contentDescription = "..." }` to a `Text` composable overrides the screen reader's announcement of the underlying text. Descriptions for data-bearing components must include the value (e.g., 'Phone: $number') to maintain accessibility and context.
**Action:** Use localized string templates with placeholders (e.g., `R.string.phone_description`) to wrap data values in descriptive labels for screen readers.
