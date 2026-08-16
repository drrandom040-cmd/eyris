## 2024-05-18 - Jetpack Compose Card Click Labels and Emoji Semantics
**Learning:** Raw emoji characters ('📷', 'f', '🎵', '💬', '📞') inside card components are spoken literally by TalkBack/screen readers ("camera", "f", "eighth note"), and clickable container cards lack explicit click action descriptions.
**Action:** Use `Modifier.clearAndSetSemantics { contentDescription = stringResource(...) }` on emoji text elements and pass `onClickLabel` to `Modifier.clickable` to provide localized screen reader context.
