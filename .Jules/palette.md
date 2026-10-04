## 2024-05-20 - Emoji and Contact Icon Accessibility Semantics
**Learning:** Raw emojis (📷, 🎵, 💬) and character labels (f) in Jetpack Compose components are pronounced literally by TalkBack/screen readers unless overridden. Using `Modifier.clearAndSetSemantics` with pre-resolved `stringResource` local variables cleanly overrides character readings with localized channel names without layout distortion.
**Action:** Always resolve `stringResource` to local variables outside non-composable semantics lambdas and apply `clearAndSetSemantics` to icon/emoji indicators.
