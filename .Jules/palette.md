## 2025-02-21 - Flexible Height for Input Composables

**Learning:** Hardcoding explicit height modifiers (such as `.height(56.dp)`) on Jetpack Compose `OutlinedTextField` or input composables prevents the component from dynamically expanding when users increase system font scaling or dynamic text size for accessibility, causing vertical text truncation and clipped floating labels.

**Action:** Avoid fixed height constraints on input composables in Jetpack Compose; rely on intrinsic padding and `Modifier.fillMaxWidth()` to allow vertical scaling for screen readers and accessible font sizes.
