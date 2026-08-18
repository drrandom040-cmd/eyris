## 2025-05-18 - String Resource Resolution in Compose Semantics
**Learning:** `stringResource(...)` is a `@Composable` function and cannot be called inside `clearAndSetSemantics` or `semantics` property assignment lambdas since those lambdas are non-composable contexts.
**Action:** Always resolve `stringResource(...)` to a local variable within the Composable scope before assigning it to `contentDescription` inside a `semantics` block.
