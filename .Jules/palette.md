## 2025-06-03 - [Input Clear Buttons and Keyboard Navigation]
**Learning:** Adding clear buttons to search-intensive fields significantly improves UX on mobile by reducing friction. Always use `ImeAction.Search` for the final field to allow triggering search directly from the keyboard, and ensure `contentDescription` uses string resources for better accessibility/localization.
**Action:** Include `ImeAction.Next` or `ImeAction.Search` along with trailing 'clear' buttons for all search-intensive input fields.
