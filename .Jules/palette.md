## 2026-06-20 - Optimized Search Input Flow
**Learning:** In Jetpack Compose, combining `ImeAction.Next`/`Search` with trailing 'clear' buttons significantly reduces friction in search-heavy forms. Removing fixed `height(56.dp)` on `OutlinedTextField` prevents vertical clipping when labels or icons are present.
**Action:** Use `ImeAction.Next` for multi-field forms and always include a `contentDescription` for trailing interactive icons to maintain accessibility.
