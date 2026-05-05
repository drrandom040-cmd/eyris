# Eyris - Lead Discovery & CRM Tool

Eyris is a mobile lead discovery and CRM tool built for freelancers and agency owners who prospect local businesses without websites. It combines multi-source business search, automatic lead saving, and a lightweight offline-capable CRM pipeline for tracking outreach outcomes.

## Features

- **Multi-Source Business Search**: Simultaneously queries Google Maps, Foursquare, and OpenStreetMap
- **Intelligent Deduplication**: Fuzzy name matching and geographic distance thresholding to eliminate duplicates
- **Smart Ranking**: Bayesian Average formula ensures businesses with high review counts rank appropriately
- **Lead Management**: Automatic lead saving with offline-first architecture
- **CRM Pipeline**: Track business outreach with status (Answered, Accepted, Rejected, Ghosted)
- **Export Functionality**: Export leads as CSV or PDF
- **Firebase Sync**: Cloud synchronization with offline support
- **Google Sign-In**: Secure authentication via Firebase

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Local Database**: Room (SQLite)
- **Cloud Sync**: Firebase Firestore
- **Authentication**: Firebase Auth (Google + Anonymous)
- **HTTP Client**: Ktor
- **HTML Parser**: Jsoup
- **Dependency Injection**: Hilt
- **Background Sync**: WorkManager
- **Image Loading**: Coil
- **Async**: Kotlin Coroutines + StateFlow

## Project Structure

```
com.elsewhere.eyris/
├── ui/                          # Compose UI screens and components
│   ├── screens/
│   ├── components/
│   └── theme/
├── domain/                       # Business logic and models
│   ├── model/
│   ├── usecase/
│   └── repository/
├── data/                         # Data layer
│   ├── local/
│   │   ├── room/               # Room database entities and DAOs
│   │   └── preferences/        # DataStore preferences
│   └── remote/
│       ├── foursquare/         # Foursquare API client
│       ├── osm/                # OpenStreetMap API client
│       └── firebase/           # Firebase integration
├── di/                          # Dependency Injection (Hilt)
├── utils/                       # Utility classes
│   ├── MergeEngine.kt          # Business deduplication
│   └── RankingEngine.kt        # Business ranking
└── workers/                     # Background workers
    └── SyncWorker.kt           # Firestore sync worker
```

## Getting Started

### Prerequisites

- Android Studio Flamingo or later
- JDK 17
- Android SDK 26+ (minSdk), Target SDK 34

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/eyris.git
   cd eyris
   ```

2. **Configure local.properties**
   ```bash
   cp local.properties.template local.properties
   ```
   Edit `local.properties` and add:
   ```properties
   sdk.dir=/path/to/android/sdk
   FSQ_API_KEY=your_foursquare_api_key
   ```

3. **Add Firebase Configuration**
   - Download `google-services.json` from Firebase Console
   - Place it in `app/google-services.json`

4. **Build and Run**
   ```bash
   ./gradlew build
   ./gradlew installDebug
   ```

## API Keys & Credentials

### Foursquare API

1. Visit [Foursquare Developers](https://foursquare.com/developers)
2. Create a new app and get your API key
3. Add to `local.properties`: `FSQ_API_KEY=your_key`

### Firebase

1. Create a project at [Firebase Console](https://console.firebase.google.com)
2. Enable:
   - Authentication (Google Sign-In)
   - Firestore Database (europe-west2 region)
   - Storage (for images)
3. Download `google-services.json` and place in `app/`

### OpenStreetMap

- No API key required
- Uses Overpass API (free tier)
- Rate limited to ~1 request per second

## Database Schema

### Users Collection
```
userIdId, displayName, email, lastOnline, createdAt
```

### Leads Collection
```
leadId, userId, businessName, category, address, lat, lng,
phone, email, coverImageUrl, openingHours, instagram, facebook,
tiktok, whatsapp, hasWebsite, websiteUrl, rating, reviewCount,
weightedScore, savedAt, searchQuery, synced
```

### Contacted Collection
```
contactedId, userId, [all business fields above],
status (ANSWERED/ACCEPTED/REJECTED/GHOSTED),
contactedAt, lastUpdatedAt, notes, socialHandleTapped, synced
```

## Ranking Formula

Eyris uses a Bayesian Average formula to rank businesses fairly:

```
Score = (reviews / (reviews + 10)) × rating + (10 / (reviews + 10)) × 3.5
```

This ensures:
- A business with 4.2 stars and 340 reviews ranks higher than one with 5 stars and 1 review
- Low review counts are automatically pulled toward the global average (3.5)
- Same formula used by Google, Yelp, and IMDb

## Merge & Deduplication

The Merge Engine combines results from three sources:

1. **Fuzzy Name Matching**: Levenshtein distance with 85% similarity threshold
2. **Geographic Distance**: 50-meter threshold to identify duplicates
3. **Data Enrichment**: Merges missing data from multiple sources
4. **Final Ranking**: Top 20 businesses without websites

## Offline Support

- **Local Storage**: All data stored in Room database
- **Offline Edits**: Status and notes can be edited offline
- **Auto Sync**: WorkManager syncs unsynced records when online
- **Token Caching**: Google Sign-In token cached for 30 days
- **Fallback Auth**: Anonymous auth if Google Sign-In fails

## Build & Deployment

### Local Build

```bash
# Debug build
./gradlew assembleDebug

# Release build (requires signing key)
./gradlew assembleRelease
```

### GitHub Actions CI/CD

Builds run automatically on every push to `main`:
- Builds debug APK
- Runs tests
- Artifacts available in Actions tab

### Environment Variables

Set these in GitHub Secrets:
- `FSQ_API_KEY`: Foursquare API key
- `FIREBASE_CONFIG`: Base64-encoded google-services.json

## Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Run with coverage
./gradlew testDebugUnitTestCoverage
```

## Color Palette

| Color | Hex | Usage |
|-------|-----|-------|
| Primary (Deep Indigo) | #1A1A2E | Main background |
| Accent (Electric Violet) | #7C3AED | Buttons, highlights |
| Surface (Dark Slate) | #16213E | Cards, surfaces |
| Secondary Surface (Muted Navy) | #0F3460 | Secondary surfaces |
| Text Primary (Off White) | #F1F5F9 | Main text |
| Text Secondary (Cool Grey) | #94A3B8 | Secondary text |
| Accepted | #22C55E | Green status |
| Rejected | #EF4444 | Red status |
| Ghosted | #94A3B8 | Grey status |
| Answered | #7C3AED | Violet status |

## Design Principles

- Premium dark theme aesthetic
- Rounded corners throughout
- Thin/light weight geometric sans-serif typography
- One-handed mobile usage (portrait orientation)
- Offline-first architecture
- No yellow, amber, or gold colors

## Contributing

1. Create a feature branch (`git checkout -b feature/amazing-feature`)
2. Commit changes (`git commit -m 'Add amazing feature'`)
3. Push to branch (`git push origin feature/amazing-feature`)
4. Open a Pull Request

## License

This project is proprietary software by Elsewhere Studios.

## Support

For issues, feature requests, or questions, please open an issue on GitHub or contact support@elsewherestudios.com.

## Roadmap

- [ ] Phase 1: Project setup, Gradle config, folder structure
- [ ] Phase 2: Room entities, DAOs, AppDatabase
- [ ] Phase 3: Firebase Auth + Firestore sync + WorkManager
- [ ] Phase 4: Scrapers (Google Maps, Foursquare, Overpass)
- [ ] Phase 5: MergeEngine + RankingEngine
- [ ] Phase 6: Domain layer — models, usecases, repositories
- [ ] Phase 7: All Compose screens
- [ ] Phase 8: Navigation, sidebar, bottom nav
- [ ] Phase 9: Export screen (CSV + PDF)
- [ ] Phase 10: Testing on physical Android device via APK sideload

---

**Eyris** — Spotting businesses others overlook.
