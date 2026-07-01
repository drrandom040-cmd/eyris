## 2025-05-14 - [Accessibility & Search UX]
**Learning:** In Jetpack Compose, icon-only buttons or emojis used for data (like social media icons) MUST have explicit `contentDescription` via `Modifier.semantics` to be accessible. For search flows, combining `ImeAction` with `KeyboardActions` and trailing clear buttons significantly improves the "flow" and usability of multi-field forms.
**Action:** Always include `semantics` for informative icons and implement `ImeAction.Next/Search` for all search-intensive screens.
