## 2025-05-14 - UX Pattern: Search Field Clear Buttons and Keyboard Actions
**Learning:** In a search-intensive interface, providing immediate clear actions for input fields and linking the software keyboard's "Search" action directly to the search submission significantly reduces friction and improves user flow.
**Action:** Always include `trailingIcon` for clearing text and configure `KeyboardOptions(imeAction = ImeAction.Search)` with corresponding `KeyboardActions` for primary search inputs in Jetpack Compose.

## 2025-05-14 - Accessibility: Semantic Content Descriptions for Data-Bearing Text
**Learning:** When using visual indicators like emojis or icons to represent data (e.g., social media handles), screen readers require explicit semantic labels via `Modifier.semantics { contentDescription = ... }` to convey meaning that isn't present in the raw text.
**Action:** Apply `Modifier.semantics` with descriptive strings to all icon-based or emoji-based data indicators.
