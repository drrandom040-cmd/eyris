## 2026-07-05 - Search Interface Accessibility & Flow
**Learning:** In multi-field search screens, combining trailing clear icons with proper ImeAction transitions (Next -> Search) significantly improves input velocity. Removing fixed heights on OutlinedTextFields ensures that labels and trailing icons don't cause vertical clipping, especially when accessibility font scaling is active.
**Action:** Always pair multi-input forms with ImeAction.Next and use Modifier.semantics to provide descriptive labels for icon/emoji-only data points (e.g., social handles).
