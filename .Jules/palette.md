## 2025-05-14 - Compose Semantics and mergeDescendants
**Learning:** In Jetpack Compose, setting `contentDescription` on a node with `mergeDescendants = true` replaces all child announcements. If important info (like a category name) is in a child, it must be explicitly included in the parent's `contentDescription`.
**Action:** Always include all critical data from children when providing a consolidated `contentDescription` for a parent container.

## 2025-05-14 - Redundant Icon Announcements
**Learning:** For buttons with both an icon and a text label, providing a `contentDescription` for the icon results in redundant announcements (e.g., "Instagram, Instagram, Button") because Compose merges descendants for clickable elements.
**Action:** Set `contentDescription = null` on icons when they are adjacent to a descriptive text label within a merged semantic container.
