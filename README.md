# AOne Permissions

## Dependency setup

Add jitpack in your projects settings.gradle.kts

Add the dependency
```java
dependencies {
    implementation("io.github.kapact:aonepermissions:1.0.1")
}
```

## Usage

Add permission in your manifest file
```java
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
```

Initialize permission requester.
```java
val permissionRequester = rememberPermissionRequester(
    Manifest.permission.POST_NOTIFICATIONS
)
```

Safely run your code with the required permission.
Library handles permission and permission rationale within these few lines of code.
```java
permissionRequester.safeExecute {
    // Do your work with required permission
    Toast.makeText(context, "Do your work now", Toast.LENGTH_SHORT).show()
}
```  

Request permission if you just want to request permission
```java
permissionRequester.request()
```

Show content only if permission is granted.
```java
SafeBox(permissionRequester) {
    Text("You have the permission")
}
```

## Example
Check out this [example](https://github.com/kapact/aonepermissions/blob/main/app/src/main/java/com/example/aonepermissions/MainActivity.kt) for library usage.  

## Contribution
Feel free to contribute to this repository. You are always welcome.

**THANK YOU**
