# TrickyNavigationSample
This repository contains some tricks about Android Navigation Component. 3rd party libraries not used.

### Outputs
Default Bottom Behaviour             |  Tricky Bottom Behaviour           
:-------------------------:|:-------------------------:|
<img height="500" src="https://user-images.githubusercontent.com/22769589/79693773-5c53c600-8275-11ea-95e5-e91945e67fde.gif"></img>  |  <img height="500" src="https://user-images.githubusercontent.com/22769589/79693821-96bd6300-8275-11ea-96bd-f4ac51ea2193.gif"></img>  

Android Navigation Component default behavior hasn't got bottom navigation back stack. If you wanna add back stack to your `bottomNavigationMenu` it's easy and simple. Just add:

```xml
android:menuCategory="secondary"
```
to your all menu items.

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/homeFragment"
        android:icon="@drawable/ic_home"
        android:menuCategory="secondary"
        android:title="Home" />

    <item
        android:id="@+id/searchFragment"
        android:icon="@drawable/ic_search"
        android:menuCategory="secondary"
        android:title="Search" />

    <item
        android:id="@+id/gamesFragment"
        android:icon="@drawable/ic_games"
        android:menuCategory="secondary"
        android:title="Games" />

    <item
        android:id="@+id/notificationsFragment"
        android:icon="@drawable/ic_notifications"
        android:menuCategory="secondary"
        android:title="Notifications" />
</menu>
```

menuCategory="secondary"             |  Tricky "secondary"
:-------------------------:|:-------------------------:|
<img height="500" src="https://user-images.githubusercontent.com/22769589/79694012-8e195c80-8276-11ea-9526-35701db37d14.gif"></img>  |  <img height="500" src="https://user-images.githubusercontent.com/22769589/79694026-9f626900-8276-11ea-869f-160b78bbe8c5.gif"></img>  

With adding secondary to your items you gonna have backstack but if you want a stack like in **Instagram** or **Youtube** make the following changes: 

First of all we need an extension for our `navController`.

```kotlin
fun NavController.popBackStackAllInstances(destination: Int, inclusive: Boolean): Boolean {
    var popped: Boolean
    while (true) {
        popped = popBackStack(destination, inclusive)
        if (!popped) {
            break
        }
    }
    return popped
}
```

Then we must add `onBackPressedDispatcher` to our bottom fragments.

```kotlin
  override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val navController = findNavController()
            if (navController.currentBackStackEntry?.destination?.id != null) {
                findNavController().popBackStackAllInstances(
                    navController.currentBackStackEntry?.destination?.id!!,
                    true
                )
            } else
                navController.popBackStack()
        }
    }
```

With this trick we have a back stack like **Instagram** and **Youtube** but we forgot something. As you know if you setup your `toolbar` with `navController`, your back press behaviour works with `navController` and `onBackPressedDispatcher` just affects your activiy's back press. If you wanna get the same bottom behavior with your toolbar navigate button. Add to following code to your activity:

```kotlin
 binding.toolbar.setNavigationOnClickListener {
            when (navController.currentDestination?.id) {
                R.id.searchFragment, R.id.gamesFragment, R.id.notificationsFragment -> {
                    if (onBackPressedDispatcher.hasEnabledCallbacks())
                        onBackPressedDispatcher.onBackPressed()
                    else
                        navController.navigateUp()
                }
                else -> navController.navigateUp()
            }
        }
```

Dynamic Label
:-------------------------:
<img height="500" src="https://user-images.githubusercontent.com/22769589/79694284-0df3f680-8278-11ea-8ac8-3ea585480982.gif"></img>

As you know, if you setup your toolbar with navController, your toolbar titles handling from navController. Navcontroller uses your fragment labels as title. For making this dynamically we need to use [Android Navigation Component - SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data)

Define your argument as string in nav_graph

```kotlin
   <fragment
        android:id="@+id/dynamicTitleFragment"
        android:name="com.faskn.trickynavigationsample.fragments.DynamicTitleFragment"
        android:label="{title}"
        tools:layout="@layout/fragment_dynamic_title" >
        <argument
            android:name="title"
            app:argType="string"
            android:defaultValue="Title" />
    </fragment>
```
don't forget to use your argument as label

```xml
  android:label="{title}"
```
and then pass data between destinations

```kotlin
  binding.buttonDynamicTitleNavigate.setOnClickListener {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDynamicTitleFragment(
                    binding.editTextTitle.text.toString()
                )
            )
        }
```


Default Replace             |  Tricky Replace
:-------------------------:|:-------------------------:|
<img height="500" src="https://user-images.githubusercontent.com/22769589/79694515-5eb81f00-8279-11ea-9d00-91035a331d9b.gif"></img>  |  <img height="500" src="https://user-images.githubusercontent.com/22769589/79694535-7abbc080-8279-11ea-816c-75d4d9e1943c.gif"></img> 

As you know, if you call `findNavController().navigate()` it replaces your current fragment with other and when you call back press maybe your state is not saved. The easiest solution is passing id to your all views. It works with ScrollView, Recyclerview, etc..

But the best solution is [Event.kt](https://gist.github.com/JoseAlcerreca/5b661f1800e1e654f07cc54fe87441af) or [EventObserver.kt](https://gist.github.com/JoseAlcerreca/e0bba240d9b3cffa258777f12e5c0ae9)

Tricky Add
:-------------------------:
<img height="500" src="https://user-images.githubusercontent.com/22769589/79694694-aab79380-827a-11ea-95f3-8e0d1c9a15a9.gif"></img>

With Android Navigation Component you can't add any fragment to your container. But you just wanna add 3-4 fragment to your navController you can use this solution.

[Navigation Component 2.1.0](https://developer.android.com/jetpack/androidx/releases/navigation#2.1.0) supports ```<dialog>``` tag in navigation graph.
  
This trick uses BottomSheetDialogFragment as fullscreen and with ```isDraggable = false``` attribute. It works like add. But adding too many screens can cause performance issues. Be careful.
