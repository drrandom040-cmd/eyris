## 2025-05-14 - Optimized Search Keyboard Flow
**Learning:** In Jetpack Compose search fields, using `ImeAction.Next` for intermediate criteria and `ImeAction.Search` for the final field, combined with trailing clear buttons, significantly improves input efficiency and accessibility. Hiding the keyboard explicitly upon search initiation provides clear feedback that the action has started.
**Action:** Always implement `KeyboardActions` and `ImeAction` in multi-field search forms to guide the user through the flow and dismiss the keyboard on the final action.
