# FilesLoading-Downloading
Files ( images and pdf) downloading from server into internal folder and Images Loading into app.


This is a sample project to loading the images from server and then You can download that image into your internal memory of the mobile . MVVM with LiveData is used in this project .


# Language: Kotlin : 

   implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
   

# There are some deependencies which is used in this application .

# DI(Dependency Injection) : Dagger2:
    implementation "com.google.dagger:dagger:$dagger_version"
    kapt "com.google.dagger:dagger-compiler:$dagger_version"
    
# Picasso:
    implementation "com.squareup.picasso:picasso:$picasso_version"
 
# Rx-java & Rx-android:
    implementation "io.reactivex.rxjava2:rxandroid:$rxandroid_version"
    implementation "io.reactivex.rxjava2:rxjava:$rxjava_version"
    implementation "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:$rxjava_adapter_version"
    
# Retrofit(Network Lib):     
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    
# Paging Lib(google): 
     implementation "android.arch.paging:runtime:$pagging_version"
     
# LiveData:
    implementation "android.arch.lifecycle:extensions:$live_data_version"
    implementation "android.arch.lifecycle:runtime:$live_data_version"
    kapt "android.arch.lifecycle:compiler:$live_data_version"
    
    
# Proguard-: for Debug Mode:
       
     minifyEnabled  true
     shrinkResources  true
     proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
       
  


# ScreenShots:

![Screenshot](https://github.com/er-akashgarg/FilesLoading-Downloading/blob/master/screenshots/device-2019-05-15-132303.png)
![Screenshot](https://github.com/er-akashgarg/FilesLoading-Downloading/blob/master/screenshots/device-2019-05-15-132343.png)
![Screenshot](https://github.com/er-akashgarg/FilesLoading-Downloading/blob/master/screenshots/device-2019-05-15-132407.png)
![Screenshot](https://github.com/er-akashgarg/FilesLoading-Downloading/blob/master/screenshots/device-2019-05-15-132424.png)
![Screenshot](https://github.com/er-akashgarg/FilesLoading-Downloading/blob/master/screenshots/device-2019-05-15-132509.png)
