## 2026-05-29 - [UX Enhancements for Search and Accessibility]
**Learning:** Social handle indicators using only emojis or single letters (like 'f' for Facebook) are inaccessible to screen readers. Always provide a `contentDescription` via `Modifier.semantics`. For search-intensive input fields, providing `ImeAction.Next` or `ImeAction.Search` along with trailing 'clear' buttons significantly improves interaction efficiency.
**Action:** Apply `semantics { contentDescription = "..." }` to icon-only indicators and ensure appropriate `ImeAction` and clear buttons for all search inputs.
