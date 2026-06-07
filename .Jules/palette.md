## 2025-05-14 - Accessibility for Non-Textual Indicators

**Learning:** Social handle indicators (emojis/single letters) and phone icons in `BusinessCard` must provide an explicit `contentDescription` via `Modifier.semantics`. Without this, screen readers may announce only the emoji name or a confusing single letter, failing to convey the actual platform or the fact that it's a contact method. For data-bearing components like the phone number, the description should include both the type (e.g., 'Phone') and the value to maintain context.

**Action:** Always wrap emoji or single-letter indicators in `Modifier.semantics { contentDescription = ... }` and use `stringResource` for localized labels. For fields displaying dynamic values (like phone numbers), include the value in the `contentDescription` for better screen reader flow.
