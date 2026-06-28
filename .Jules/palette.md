## 2025-05-15 - [Search Screen Keyboard & Input Flow]
**Learning:** Optimizing search forms with `ImeAction.Next` for focus transitions and `ImeAction.Search` for submission, combined with trailing clear buttons, significantly reduces friction in multi-field mobile search interfaces.
**Action:** Always implement explicit focus management (Next/Search) and provide one-tap clearing for search-intensive input fields in Jetpack Compose.

## 2025-05-15 - [Adaptive Text Field Sizing]
**Learning:** Removing fixed heights from `OutlinedTextField` allows components to scale naturally for accessibility (font scaling) and accommodate trailing icons without vertical clipping.
**Action:** Avoid hardcoded heights on text inputs to ensure Material Design components handle internal padding and accessories correctly across different screen densities and accessibility settings.
