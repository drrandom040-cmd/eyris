## 2025-05-14 - Enhanced search input UX with clear buttons and keyboard actions
**Learning:** In multi-field search forms on mobile, providing "clear" buttons for each input and optimizing keyboard flow (using `ImeAction.Next` and `ImeAction.Search`) significantly improves usability. Programmatically hiding the keyboard on search initiation also provides a smoother transition to the results.
**Action:** Always include trailing 'clear' buttons for search-intensive input fields and ensure proper `KeyboardOptions`/`KeyboardActions` are configured for better form flow.
