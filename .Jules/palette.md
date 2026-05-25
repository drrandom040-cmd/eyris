## 2025-05-15 - Improving Search and Social Accessibility

**Learning:** Emoji-based status or social indicators (e.g., 📷, 🎵) are invisible to screen readers without explicit semantic descriptions.
**Action:** Always wrap emoji-based indicators in `Modifier.semantics { contentDescription = "..." }` to ensure accessibility.

**Learning:** Complex search forms without proper IME actions and "Clear" buttons lead to high interaction friction on mobile.
**Action:** Implement `ImeAction.Next`/`ImeAction.Search` flow and trailing 'clear' icon buttons for all search-intensive interfaces.
