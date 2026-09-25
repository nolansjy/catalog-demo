# README

## Setup
- Download the appdebug.apk file from [Releases](https://github.com/nolansjy/catalog-demo/releases/tag/v1.0.0) and install on Android device
- Alternatively, clone repo in Android Studio and run on emulator

## Specifications
- Uses Jetpack Compose with Hilt for dependency injection and Retrofit/Gson for handling API requests and response
- Targets Android SDK 36 with min SDK 21
- Note: Uses Version Catalog(libs.versions.toml) to handle plugins and dependencies. Dependencies not using Version Catalog generally had compile errors, so regular strings are used

## Architecture and File Structure
- Uses Data/Domain/UI layers with MVVM pattern
- In product List view, only fetches id/title/thumbnail/price. On tapping product, only then will fetch product details using the ID. The thumbnail is also passed as a placeholder before the list of images are loaded.
- This does introduce some loading when the product details are viewed, but still more efficient than fetching all details during the list view. 

### Data Layer
- di (dependency injection): NetworkModule, provides Retrofit instance, apiService and RepositoryImpl
- dto (data transfer object): API response is fetched as Response (full body) and Product (only 'products') DTO 
- mapper: Maps ProductDTO to ProductDetail and ProductItem classes
- network: ApiService provides API request methods
- repository: RepositoryImpl implements Repository methods through ApiService
- source: ProductPagingSource handles pagination for list of product items

### Domain Layer
- model: has ProductDetail and ProductItem classes
- repository: provides repository methods (should be renamed to be more specific when more repository interfaces are used)

### UI Layer 
- components: Custom components i.e. ProductCard, SearchTopBar
- main: contains AppNavHost for navigation, and all screens and viewmodels used (should be seperated into folders when more screens and viewmodels are required)

## TODOs
- Fix scroll indicator for images (attempted to implement but not visible)
- Add splash screen on app startup (currently a visible delay before products are loaded in app, when starting app for first time)
- More error handling e.g. for loss of network connectivity. Currently only Main screen has a retry button (when scrolling through items)

## AI Usage Declaration
- Used to look up meaning of some error messages for debugging
- Used to look up how Flow and Paging library can be used together
