📱 MyProfile App

A profile management Android application built with Jetpack Compose and Room Database, following the MVVM architecture pattern.

✨ Features

📋 Show profile list

✏️ Edit profile information

🗑 Delete profile

🖼 Add profile image from device gallery

💾 Local data persistence using Room Database

🛠 Tech Stack

Kotlin

Jetpack Compose

Room Database

MVVM Architecture

Coroutines

Coil (for image loading)

📖 Overview

MyProfile is a modern Android application that allows users to:

Create and manage personal profiles

Edit existing profile details

Delete profiles

Select and save a profile image from the device gallery

Persist all data locally using Room Database

Selected images are stored in internal storage, and their file paths are saved in the database.

All data remains available even after closing the app.

🗂 Database Implementation

The app uses Room Database for local storage.

Supported operations:

Insert user

Update user

Delete user

Retrieve all users

Database operations run inside Coroutines to prevent blocking the main thread.

🧠 Architecture

The project follows the MVVM (Model-View-ViewModel) pattern:

UI Layer → Jetpack Compose

ViewModel Layer → Handles business logic & state

Data Layer → Room (DAO + Entity)

This separation improves maintainability, scalability, and testability.

🚀 Getting Started

Clone the repository

Open it in Android Studio

Build and run the application

<table>
  <tr>
    <td align="center">
      <img src="https://s8.uupload.ir/files/list_profile_x1j7.png" width="300"/><br/>
      <b>Profile List</b>
    </td>
    <td align="center">
      <img src="https://s8.uupload.ir/files/add_profile_hi0.png" width="300"/><br/>
      <b>Add Profile</b>
    </td>
  </tr>
  <tr>
   <td align="center">
      <img src="https://s8.uupload.ir/files/edit_profile_avng.png" width="300"/><br/>
      <b>Edit Profile</b>
    </td>
    
  </tr>
</table>
