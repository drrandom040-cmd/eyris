## 2026-10-31 - Explicit accessibility semantics for contact icons and cards
**Learning:** Screen readers like TalkBack announce raw emojis (📷, 🎵, 💬) or letter glyphs ('f') literally or inconsistently unless overridden with `Modifier.clearAndSetSemantics`. Providing localized strings for `contentDescription` and specifying `onClickLabel` on cards clarifies their interactive intent.
**Action:** Always provide explicit localized content descriptions using `clearAndSetSemantics` for decorative/emoji indicators and provide clear `onClickLabel` semantics on interactive card containers.
