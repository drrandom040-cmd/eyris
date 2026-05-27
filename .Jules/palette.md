## 2026-05-05 - [Enhanced Search Experience]
**Learning:** A reusable UX pattern for this app is to include `ImeAction.Next` or `ImeAction.Search` along with trailing 'clear' buttons for all search-intensive input fields.
**Action:** Use `ImeAction.Next` for multi-field forms and `ImeAction.Search` for the final field to trigger actions directly from the keyboard. Always provide a clear button for text inputs that may be frequently reset.
