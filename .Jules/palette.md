## 2025-05-14 - [Semantic Text Overrides]
**Learning:** In Jetpack Compose, applying `Modifier.semantics { contentDescription = "..." }` to a `Text` composable overrides the screen reader's announcement of the underlying text. Descriptions for data-bearing components must include the value (e.g., 'Phone: $number') to maintain accessibility.
**Action:** Always include the dynamic value in `contentDescription` when overriding semantics on a `Text` composable.

## 2025-05-14 - [Compose UX Refactoring Pattern]
**Learning:** To adhere to the 50-line PR constraint while maintaining DRY principles in Jetpack Compose, repetitive input field logic (labels, trailing clear icons, keyboard actions) should be encapsulated into internal or private helper composables within the same screen file.
**Action:** Use private helper composables for repetitive UI patterns to keep PRs focused and readable.
