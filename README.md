# Sticky Header Bottom Sheet
[![](https://jitpack.io/v/kshitijskumar/StickyHeaderBottomSheet.svg)](https://jitpack.io/#kshitijskumar/StickyHeaderBottomSheet)

A simple library to create a Bottom Sheet with a sticky header and a content recycler view.  <br>
The bottom sheet expands on scrolling the content recyclerview, and once it is fully expanded, the header sticks on the top while the recyclerview is still scrolling.

## Adding dependencies: 
if your gradle version is 7.0 or above :
Add this to your settings.gradle (Project level):
```kotlin
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		....
		maven { url 'https://jitpack.io' }
	}
}
```
If your gradle version is below 7.0 : Add this to your build.gradle (Project level):
```kotlin
allprojects {
	repositories {
		....
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependencies in your app/build.gradle:
```kotlin
dependencies {
    ....
    implementation 'com.github.kshitijskumar:StickyHeaderBottomSheet:1.0.1'
}
```
#### Note: Databinding or Viewbinding is not supported yet

## How to use the library
1. Extend FeatureBottomSheet class with StickyHeaderBottomSheetDialogFragment
```kotlin
class FeatureBottomSheet : StickyHeaderBottomSheetDialogFragment()
```
2. Implement abstract methods of the base class
```kotlin
    override fun getContentView(): Int {
        return R.layout.fragment_feature_bottom_sheet
    }

    override fun getHeaderView(): View {
        return view?.findViewById<ConstraintLayout>(R.id.header) ?: throw IllegalStateException("Header view not found")
    }

    override fun getContentRecyclerView(): RecyclerView {
        return view?.findViewById<RecyclerView>(R.id.recyclerView) ?: throw IllegalStateException("Recyclerview not found")
    }
 ```
 3. Make sure you don't override onCreateView to inflate the layout for the bottom sheet as StickyHeaderBottomSheetDialogFragment handles inflating the right view itself. You can get the view in onCreateView from super method, then return the same view
```kotlin
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  super.onCreateView(inflater, container, savedInstanceState)
        // do whatever you want to do
        return v
    }
  ```
 4. Your layout file should be primarily divided into 2 section, Header and Content (recycler view)
```kotlin
    <?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto">

// header
    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/header"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent">

        ....

    </androidx.constraintlayout.widget.ConstraintLayout>

// content recyclerview
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@id/header"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:paddingVertical="20dp"
        android:clipToPadding="false"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```
## Additionally
If in some use case you don't want the bottom sheet to expand on scrolling (suppose there are only 2-3 items in content recyclerview), in that case you can use this method, 
```kotlin
    ....
    // after updating the list in content
    updateBottomSheetHeight(shouldMakeScrollable = false)
    // true to make the bottom sheet expand
```
## Final result
<img src="https://github.com/kshitijskumar/Sticky-Header-Bottom-Sheet/blob/main/stickyheaderdemo.gif" height=400> &nbsp;&nbsp;

