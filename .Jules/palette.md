## 2025-05-15 - [Accessibility for data-bearing Text components]
**Learning:** In Jetpack Compose, applying `Modifier.semantics { contentDescription = "..." }` to a `Text` composable overrides the screen reader's announcement of the underlying text. Descriptions for data-bearing components must include the value (e.g., 'Phone: $number') to maintain accessibility.
**Action:** Always include the dynamic value in the `contentDescription` when overriding semantics on a `Text` component that displays information.

## 2025-05-15 - [Reusable Search UX Pattern]
**Learning:** A smooth search experience in mobile apps involves providing clear buttons for all search fields and supporting IME actions (Next/Search) that automatically trigger the search and dismiss the keyboard.
**Action:** Implement `trailingIcon` with clear logic and `keyboardActions` with `LocalSoftwareKeyboardController.current?.hide()` in all search-intensive forms.
