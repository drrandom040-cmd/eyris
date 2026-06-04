## 2025-05-15 - Optimizing Search Input UX in Compose
**Learning:** A reusable UX pattern for this app is to include `ImeAction.Next` or `ImeAction.Search` along with trailing 'clear' buttons for all search-intensive input fields to improve navigation and resetting capabilities.
**Action:** In `SearchScreen.kt`, search inputs are configured with `ImeAction.Next` for 'Business Type' and 'Location', and `ImeAction.Search` for 'Category' to optimize the software keyboard navigation and submission flow.
