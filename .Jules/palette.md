## 2025-05-15 - [SearchScreen Keyboard Flow & Clear Buttons]
**Learning:** Optimizing search forms with `ImeAction.Next` and `ImeAction.Search` along with trailing clear buttons significantly improves the 'flow' of multi-field search screens, especially when keyboard dismissal is avoided until the final field.
**Action:** Always implement `ImeAction.Next` for intermediate fields and `ImeAction.Search` for the final field in multi-input forms, and ensure clear buttons have proper accessibility labels.
