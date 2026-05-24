## 2025-05-14 - Social Handle Accessibility
**Learning:** Social handle indicators represented by emojis or single characters in `BusinessCard` lack inherent meaning for screen readers. Using `Modifier.semantics` with `contentDescription` is essential for making these indicators accessible.
**Action:** Always provide explicit `contentDescription` via `Modifier.semantics` for emoji-based or non-textual indicators in component lists.
