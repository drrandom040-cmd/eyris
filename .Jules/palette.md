## 2024-05-22 - [Search Accessibility & Flow]
**Learning:** Avoiding explicit height constraints (e.g., `.height(56.dp)`) on `OutlinedTextField` components allows the UI to scale correctly when users increase system font sizes, preventing text clipping and ensuring readability.
**Action:** Remove hardcoded height constraints from input fields to support dynamic font scaling and improve accessibility.
