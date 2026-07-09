## 2025-05-14 - Optimized Search Keyboard Flow
**Learning:** In multi-field search forms, using `ImeAction.Next` for intermediate fields and `ImeAction.Search` for the final field, combined with `LocalFocusManager` to clear focus and `LocalSoftwareKeyboardController` to hide the keyboard, creates a much smoother user experience. Adding trailing clear icons also provides an easy way for users to reset individual inputs.
**Action:** Always implement coordinated IME actions and focus management in multi-input screens to ensure a polished mobile interaction.
