## 2026-06-19 - Enhanced Search Experience and Accessibility
**Learning:** Optimizing the keyboard flow in search-intensive screens by using ImeAction.Next and ImeAction.Search significantly improves UX. Hiding the keyboard programmatically upon search initiation provides a smoother transition. Accessibility for data-bearing components (like social handles) must include both the label and value via semantics to be truly useful.

**Action:** Always implement trailing clear buttons and appropriate IME actions for form fields. Use Modifier.semantics to provide descriptive context for icon-only or emoji indicators.
