## 2025-05-14 - Search Field Enhancements
**Learning:** Search-intensive screens benefit from reducing input friction. Combining trailing "Clear" buttons with optimized keyboard actions (IME Next/Search) creates a much smoother query-refinement loop.
**Action:** Always include `ImeAction.Next` for non-final fields and `ImeAction.Search` for the final field in search forms, and hide the keyboard programmatically when the search starts.
