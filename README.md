<div align="center">

# 🎨 Random Color Generator App

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Firebase](https://img.shields.io/badge/Firebase-039BE5?style=flat&logo=Firebase&logoColor=white)](https://firebase.google.com)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=flat&logo=android-studio&logoColor=white)](https://developer.android.com/studio)
[![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=flat&logo=Gradle&logoColor=white)](https://gradle.org)

<p align="center">
  <img src="https://github.com/user-attachments/assets/608cf648-8fc7-40eb-9627-d67e228a1226" alt="App Icon" width="150"/>
</p>

*A modern Android application for generating beautiful color palettes with real-time cloud synchronization*

[Features](#-features) • [Screenshots](#-screenshots) • [Installation](#%EF%B8%8F-installation) • [Usage](#-usage) • [Documentation](#-documentation) • [Contributing](#-contributing)

</div>

## ✨ Features

- **Intuitive Color Generation**
  - Generate vibrant colors with a single tap
  - Copy color codes with one click
  - Share colors directly to other apps

- **Advanced Functionality**
  - Real-time cloud synchronization
  - Offline support with local caching
  - Material You dynamic theming
  - Dark/Light mode support

- **Technical Highlights**
  - Clean Architecture with MVVM
  - Reactive programming with Kotlin Flow
  - Dependency injection with Hilt
  - Modern UI with Jetpack Compose

## 📱 Screenshots

### 1. Blank Screen on Start
The app starts with a blank screen, ready for the first color generation.

<img src="https://github.com/user-attachments/assets/efd5ff8c-d4c1-49a1-81db-7d97381554a2" alt="Blank Screen" width="300"/>

### 2. Random Color Generation
After tapping, the app generates and displays a random color.

<img src="https://github.com/user-attachments/assets/15e5c7e3-36e8-485a-9e46-b8fd48f77ce8" alt="Random Color" width="300"/>

### 3. Timestamp of Color Generation
Each color comes with a timestamp showing when it was generated.

<img src="https://github.com/user-attachments/assets/ddfe9cac-5f56-49c2-a5e8-9804a61a2f9e" alt="Timestamp" width="300"/>

### 4. Multiple Colors Generated
The app can generate and store multiple colors, each with a unique timestamp and color code.

<img src="https://github.com/user-attachments/assets/526dc2d6-5433-4735-8aa0-7f54787ab657" alt="Multiple Colors" width="300"/>

### 5. Night Mode
The app also supports a night mode theme for generating colors in a darker interface.

<img src="https://github.com/user-attachments/assets/0704ca16-522c-412d-a3d3-dea2665b2d61" alt="Night Mode" width="300"/>

</center>

## 🛠️ Tech Stack

<table>
  <tr>
    <td align="center"><strong>Category</strong></td>
    <td align="center"><strong>Technologies</strong></td>
  </tr>
  <tr>
    <td>📱 Frontend</td>
    <td>
      • Jetpack Compose<br>
      • Material Design 3<br>
      • Animation APIs
    </td>
  </tr>
  <tr>
    <td>💾 Backend</td>
    <td>
      • Room Database<br>
      • Firebase Realtime Database<br>
      • DataStore Preferences
    </td>
  </tr>
  <tr>
    <td>🏗️ Architecture</td>
    <td>
      • Clean Architecture<br>
      • MVVM Pattern<br>
      • Repository Pattern
    </td>
  </tr>
  <tr>
    <td>🧰 Tools</td>
    <td>
      • Hilt Dependency Injection<br>
      • Kotlin Coroutines<br>
      • Kotlin Flow
    </td>
  </tr>
</table>

## ⚙️ Installation

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 11 or later
- Android SDK API 21 or later
- Firebase Account

### Step-by-Step Setup

1. Clone the repository
```bash
git clone https://github.com/abhishekraj099/ColorAPP.git
```

2. Firebase Configuration
```bash
# Add your Firebase configuration
- Create new project in Firebase Console
- Add Android app with package name: com.abhishek.colorapp
- Download google-services.json
- Place in app/ directory
```

3. Build and Run
```bash
# Clean and build project
./gradlew clean build

# Install on device/emulator
./gradlew installDebug
```

## 📱 Usage

### Basic Operations

```kotlin
// Generate a random color
colorGeneratorButton.setOnClickListener {
    viewModel.generateRandomColor()
}

// Save color to favorites
FloatingActionButton(
    onClick = { viewModel.saveColor(color) }
) {
    Icon(Icons.Filled.Favorite)
}
```

### Advanced Features

- **Long press** on any color to open sharing options
- **Swipe left** on history items to delete
- **Double tap** to copy color code
- **Shake device** to generate new color

## 📖 Documentation

Detailed documentation is available in the following sections:

- [Architecture Overview](docs/ARCHITECTURE.md)
- [Firebase Setup Guide](docs/FIREBASE_SETUP.md)
- [Contribution Guidelines](docs/CONTRIBUTING.md)
- [Release Notes](docs/CHANGELOG.md)

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

```
MIT License

Copyright (c) 2024 Abhishek Raj

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files...
```

## 👨‍💻 Author

<div align="center">
  <img src="https://github.com/abhishekraj099.png" width="100px" style="border-radius: 50%"/>
  <br>
  <strong>Abhishek Raj</strong>
  <p>Software Engineer & Android Developer</p>
  
  [![Twitter](https://img.shields.io/badge/Twitter-%231DA1F2.svg?style=for-the-badge&logo=Twitter&logoColor=white)](https://twitter.com/abhishekraj099)
  [![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/abhishekraj099)
</div>

---

<div align="center">
  
⭐️ Star this repo if you find it helpful!

Need help? [Open an issue](https://github.com/abhishekraj099/ColorAPP/issues/new)

</div>
