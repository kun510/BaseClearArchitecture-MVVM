# Base Clean Architecture MVVM - Android

A comprehensive Android application showcasing **MVVM Clean Architecture** with **AdMob** and **Firebase** integration.

## 🏗️ Architecture Overview

This project follows **Clean Architecture** principles with **MVVM** pattern:

### 📂 Project Structure
```
com.example.basecleararchitecture
│
├── 📁 data
│   ├── 🌐 remote (API services)
│   └── 📦 repository (Repository implementations)
│
├── 📁 domain
│   ├── 📋 model (Domain entities)
│   ├── 📚 repository (Repository interfaces)
│   └── 🔄 usecase (Business logic)
│
├── 📁 presentation
│   ├── 🎨 ui (Activities, Screens, Components)
│   ├── 🎭 viewmodel (ViewModels)
│   └── 🎨 theme (UI theming)
│
├── 📁 di (Dependency Injection)
│   └── Hilt modules
│
└── 📁 utils
    ├── 📢 ads (AdMob management)
    └── 🔥 firebase (Firebase services)
```

## ✨ Features

### 🏛️ Architecture Components
- ✅ **MVVM Pattern** with Clean Architecture
- ✅ **Hilt** for Dependency Injection
- ✅ **Repository Pattern** for data management
- ✅ **Use Cases** for business logic separation
- ✅ **StateFlow/LiveData** for reactive programming
- ✅ **Coroutines** for asynchronous operations

### 🌐 Networking
- ✅ **Retrofit** for API communication
- ✅ **OkHttp** with logging interceptor
- ✅ **Gson** for JSON serialization
- ✅ **Error handling** with Resource wrapper

### 📱 UI/UX
- ✅ **Jetpack Compose** for modern UI
- ✅ **Material 3** design components
- ✅ **Dark/Light theme** support
- ✅ **Responsive layouts**

### 📢 AdMob Integration
- ✅ **App Open Ads** - Displayed on app launch
- ✅ **Banner Ads** - Shown at the top of screens
- ✅ **Interstitial Ads** - Full-screen ads between content
- ✅ **Native Ads** - Integrated into content (ready for implementation)

### 🔥 Firebase Integration
- ✅ **Firebase Analytics** - User behavior tracking
- ✅ **Firebase Crashlytics** - Crash reporting
- ✅ **Firebase Cloud Messaging** - Push notifications

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 24+ (API level 24)
- Kotlin 1.9.10+

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/kun510/BaseClearArchitecture-MVVM.git
   cd BaseClearArchitecture-MVVM
   ```

2. **Firebase Setup**
   - Create a new project in [Firebase Console](https://console.firebase.google.com/)
   - Add Android app with package name: `com.example.basecleararchitecture`
   - Download `google-services.json` and place it in `app/` directory
   - Enable Analytics and Crashlytics in Firebase Console

3. **AdMob Setup**
   - Create an account at [AdMob](https://admob.google.com/)
   - Create a new app and ad units
   - Replace test ad unit IDs in `Constants.kt` with your actual IDs:
     ```kotlin
     const val ADMOB_APP_ID = "your-app-id"
     const val ADMOB_BANNER_ID = "your-banner-id"
     const val ADMOB_INTERSTITIAL_ID = "your-interstitial-id"
     const val ADMOB_NATIVE_ID = "your-native-id"
     const val ADMOB_OPEN_APP_ID = "your-open-app-id"
     ```

4. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```

## 📦 Dependencies

### Core Dependencies
- **Kotlin**: 1.9.10
- **Hilt**: 2.48
- **Retrofit**: 2.9.0
- **Coroutines**: 1.7.3
- **Lifecycle**: 2.7.0
- **Room**: 2.5.0
- **Compose**: 2023.10.01
- **Navigation**: 2.7.4

### Firebase Dependencies
- **Firebase BOM**: 32.5.0
- **Analytics**: Included in BOM
- **Crashlytics**: Included in BOM
- **Messaging**: Included in BOM

### AdMob Dependencies
- **Play Services Ads**: 22.4.0

## 🛠️ Usage Examples

### Making API Calls
```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    
    fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect { resource ->
                _users.value = resource
            }
        }
    }
}
```

### AdMob Integration
```kotlin
// App Open Ad
val appOpenAdManager = AppOpenAdManager(this)
appOpenAdManager.loadAd(this)
appOpenAdManager.showAdIfAvailable(this, onShowAdCompleteListener)

// Interstitial Ad
val interstitialAdManager = InterstitialAdManager(this)
interstitialAdManager.loadAd()
interstitialAdManager.showAd(this) { 
    // Ad completion callback
}
```

### Firebase Analytics
```kotlin
// Log custom events
firebaseAnalytics.logEvent("user_action", bundleOf(
    "action_type" to "button_click",
    "screen_name" to "main_screen"
))
```

## 🔧 Configuration

### AdMob Configuration
Update `Constants.kt` with your AdMob IDs:
```kotlin
object Constants {
    const val ADMOB_APP_ID = "ca-app-pub-XXXXXXXXXXXXXXXX~XXXXXXXXXX"
    const val ADMOB_BANNER_ID = "ca-app-pub-XXXXXXXXXXXXXXXX/XXXXXXXXXX"
    // ... other IDs
}
```

### Firebase Configuration
Ensure `google-services.json` is properly configured in your Firebase project.

## 🧪 Testing

### Unit Tests
```bash
./gradlew test
```

### Integration Tests
```bash
./gradlew connectedAndroidTest
```

## 📝 Code Style

This project follows:
- **Kotlin Coding Conventions**
- **Clean Architecture principles**
- **SOLID principles**
- **Material Design Guidelines**

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🎯 Roadmap

- [ ] **Multi-module architecture**
- [ ] **Offline support with Room database**
- [ ] **Native ads implementation**
- [ ] **Advanced Firebase features**
- [ ] **Unit and Integration tests**
- [ ] **CI/CD pipeline**
- [ ] **Performance optimization**

## 📞 Support

For support, email kun510dev@gmail.com or create an issue in this repository.

## 🙏 Acknowledgments

- Android Architecture Components
- Firebase Team
- AdMob Team
- Jetpack Compose Team
- Clean Architecture by Uncle Bob

---

**Made with ❤️ by [kun510](https://github.com/kun510)**
