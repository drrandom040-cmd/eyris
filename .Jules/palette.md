## 2025-05-18 - Clickable Settings Cards and Decorative Icons

**Learning:** In Jetpack Compose, setting a `contentDescription` on decorative icons inside labeled cards or buttons causes redundant screen reader announcements (e.g. reading both the icon description and the button text). Furthermore, passing `onClickLabel` to `Modifier.clickable` provides screen readers with explicit context on what action will be performed when activating a settings card.

**Action:** Set `contentDescription = null` on decorative icons that accompany visible text labels, and always provide an `onClickLabel` on `Modifier.clickable` for custom interactive cards or items.
