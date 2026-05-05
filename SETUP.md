# Eyris - Setup & Deployment Guide

## Quick Start

### Prerequisites
- Android Studio Flamingo or later
- JDK 17
- Android SDK 26+ (minSdk), Target SDK 34
- Git

### Local Setup

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
   FSQ_API_KEY=EJMZFLPCLH1GAKFTEY3JEXH0UK4XGGL5ZIPBYEUXYVITUAMW
   ```

3. **Add Firebase Configuration**
   - Place your `google-services.json` in `app/google-services.json`
   - This file is already included in the project

4. **Build the project**
   ```bash
   ./gradlew clean build
   ```

5. **Run on emulator or device**
   ```bash
   ./gradlew installDebug
   ```

## GitHub Setup

### Option 1: Push to Your Own Repository

1. **Create a new repository on GitHub**
   - Go to https://github.com/new
   - Name it "eyris"
   - Choose public or private
   - Do NOT initialize with README, .gitignore, or license

2. **Push the code**
   ```bash
   cd /home/ubuntu/eyris
   git remote add origin https://github.com/yourusername/eyris.git
   git branch -M main
   git push -u origin main
   ```

3. **Configure GitHub Secrets for CI/CD**
   - Go to Settings → Secrets and variables → Actions
   - Add `FSQ_API_KEY`: `EJMZFLPCLH1GAKFTEY3JEXH0UK4XGGL5ZIPBYEUXYVITUAMW`
   - Add `FIREBASE_CONFIG`: Base64-encoded `google-services.json`
     ```bash
     base64 app/google-services.json
     ```

### Option 2: Push to Existing Repository

```bash
cd /home/ubuntu/eyris
git remote set-url origin https://github.com/yourusername/your-repo.git
git push -u origin main
```

## CI/CD Pipeline

The project includes GitHub Actions workflow (`.github/workflows/android-build.yml`) that:

- ✅ Builds debug APK on every push to `main`
- ✅ Runs unit tests
- ✅ Uploads APK to artifacts
- ✅ Comments on PRs with build status

### Accessing Build Artifacts

1. Go to your GitHub repository
2. Click "Actions" tab
3. Select the latest workflow run
4. Download `app-debug.apk` from artifacts

## Project Structure

```
eyris/
├── app/
│   ├── src/main/
│   │   ├── kotlin/com/elsewhere/eyris/
│   │   │   ├── ui/                 # Compose screens & components
│   │   │   ├── domain/             # Models & use cases
│   │   │   ├── data/               # Database & API clients
│   │   │   ├── di/                 # Dependency injection
│   │   │   ├── utils/              # Merge & ranking engines
│   │   │   └── workers/            # Background sync
│   │   └── res/                    # Resources
│   ├── build.gradle.kts
│   └── google-services.json
├── .github/workflows/
│   └── android-build.yml           # CI/CD pipeline
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── local.properties.template
└── README.md
```

## Key Files

| File | Purpose |
|------|---------|
| `app/build.gradle.kts` | Dependencies & build config |
| `app/src/main/AndroidManifest.xml` | App permissions & activities |
| `app/google-services.json` | Firebase configuration |
| `local.properties` | Local SDK path & API keys |
| `.github/workflows/android-build.yml` | CI/CD pipeline |

## Environment Variables

### Local Development
Set in `local.properties`:
- `FSQ_API_KEY`: Foursquare API key

### GitHub Actions
Set in repository Secrets:
- `FSQ_API_KEY`: Foursquare API key
- `FIREBASE_CONFIG`: Base64-encoded google-services.json

## Building APK

### Debug APK (for testing)
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release APK (for production)
```bash
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
# Note: Requires signing key configuration
```

## Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests on device/emulator
./gradlew connectedAndroidTest

# Run with coverage
./gradlew testDebugUnitTestCoverage
```

## Troubleshooting

### Build Issues

**Error: "google-services.json not found"**
- Ensure `app/google-services.json` exists
- Download from Firebase Console if missing

**Error: "FSQ_API_KEY not set"**
- Add to `local.properties`: `FSQ_API_KEY=your_key`

**Error: "SDK not found"**
- Set `sdk.dir` in `local.properties`
- Or set `ANDROID_HOME` environment variable

### Runtime Issues

**Firebase Auth fails**
- Check Firebase project is enabled for Google Sign-In
- Verify `google-services.json` matches your Firebase project

**Foursquare API returns 403**
- Verify API key in `local.properties`
- Check Foursquare account has API access

**No location data**
- Grant location permissions on device
- Enable location services

## Development Workflow

1. **Create feature branch**
   ```bash
   git checkout -b feature/your-feature
   ```

2. **Make changes and commit**
   ```bash
   git add .
   git commit -m "Add your feature"
   ```

3. **Push and create PR**
   ```bash
   git push origin feature/your-feature
   ```

4. **CI/CD runs automatically**
   - GitHub Actions builds APK
   - Tests run
   - Results posted to PR

5. **Merge when ready**
   ```bash
   git checkout main
   git pull origin main
   git merge feature/your-feature
   git push origin main
   ```

## API Documentation

### Foursquare
- **Endpoint**: `https://api.foursquare.com/v3/places/search`
- **Free Tier**: 1000 calls/day
- **Docs**: https://developer.foursquare.com/docs

### OpenStreetMap
- **Endpoint**: `https://overpass-api.de/api/interpreter`
- **Rate Limit**: ~1 request/second
- **Docs**: https://wiki.openstreetmap.org/wiki/Overpass_API

### Firebase
- **Console**: https://console.firebase.google.com
- **Services**: Auth, Firestore, Storage
- **Region**: europe-west2

## Performance Tips

1. **Reduce APK size**
   - Enable ProGuard/R8 in release builds
   - Use `minifyEnabled = true`

2. **Improve search performance**
   - Increase parallel request timeout
   - Cache results locally

3. **Optimize database**
   - Add indexes to frequently queried columns
   - Use pagination for large datasets

## Security Considerations

- ✅ Never commit `local.properties` or `google-services.json`
- ✅ Use GitHub Secrets for sensitive data
- ✅ Rotate API keys regularly
- ✅ Enable Firebase security rules
- ✅ Use HTTPS for all API calls

## Support & Resources

- **GitHub Issues**: Report bugs and feature requests
- **Firebase Docs**: https://firebase.google.com/docs
- **Jetpack Compose**: https://developer.android.com/jetpack/compose
- **Kotlin Docs**: https://kotlinlang.org/docs

---

**Eyris** — Built with Kotlin, Jetpack Compose, and Firebase
