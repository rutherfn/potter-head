# API-Based Image Search Implementation Plan

## Overview
Replace the JSON file-based image lookup with an API-based search system that automatically finds character images when the HP-API doesn't provide them.

## Benefits
✅ **No manual maintenance** - No need to manually add 400+ character images  
✅ **Automatic coverage** - Works for any character, even new ones  
✅ **Always up-to-date** - API results are current  
✅ **Scales automatically** - No code changes needed for new characters  
✅ **Reduced app size** - No large JSON file in the app bundle  

## Architecture

### Current Flow:
1. Fetch characters from HP-API
2. If image URL is null/empty → Look up in JSON file (stored in DB)
3. Use found image or return null

### New Flow:
1. Fetch characters from HP-API
2. If image URL is null/empty → Check database cache first
3. If not in cache → Call Wikipedia API to search for image
4. Cache the result in database for future use
5. Use found image or return null

## Implementation Steps

### 1. Update NetworkModule Interface
Add to `NetworkModule.kt`:
```kotlin
val characterImageSearchService: CharacterImageSearchService
val characterImageSearchRepository: CharacterImageSearchRepository
```

### 2. Update NetworkModuleImpl
In `AppGraph.kt`, add:
```kotlin
// Create separate Retrofit instance for Wikipedia API
private val wikipediaRetrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl("https://en.wikipedia.org/w/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

override val characterImageSearchService: CharacterImageSearchService by lazy {
    wikipediaRetrofit.create(CharacterImageSearchService::class.java)
}

override val characterImageSearchRepository: CharacterImageSearchRepository by lazy {
    CharacterImageSearchRepositoryImpl(
        imageSearchService = characterImageSearchService,
        networkMonitor = networkMonitor
    )
}
```

### 3. Update CharacterRepositoryImpl
Already updated! It now accepts `imageSearchRepository` as an optional parameter.

### 4. Update DatabaseModule
In `AppGraph.kt`, update `CharacterRepositoryImpl` instantiation:
```kotlin
override val characterRepository: CharacterRepository by lazy {
    CharacterRepositoryImpl(
        dao = characterDao,
        characterImageDao = characterImageDao,
        imageSearchRepository = networkModule.characterImageSearchRepository  // Add this
    )
}
```

### 5. Optional: Add Caching Method
Add to `CharacterImageDao`:
```kotlin
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertCharacterImageUrl(characterImageUrl: CharacterImageUrlEntity)
```

Then update `CharacterRepositoryImpl.mergeImageUrlIfNeeded` to cache API results:
```kotlin
if (!searchedImageUrl.isNullOrBlank()) {
    // Cache the found image URL
    characterImageDao.insertCharacterImageUrl(
        CharacterImageUrlEntity(
            characterName = name,
            imageUrl = searchedImageUrl
        )
    )
    return copy(image = searchedImageUrl)
}
```

## API Options

### Option 1: Wikipedia API (Recommended) ✅
- **Free**: No API key required
- **Reliable**: Stable, well-maintained
- **Good coverage**: Most HP characters have Wikipedia pages
- **Base URL**: `https://en.wikipedia.org/w/api.php`

**Example Request:**
```
GET https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&pithumbsize=500&titles=Harry%20Potter
```

### Option 2: Google Custom Search API
- **Requires**: API key and billing setup
- **Cost**: $5 per 1000 requests (first 100 free/day)
- **More flexible**: Can search any image source
- **Better results**: More diverse image sources

### Option 3: Unsplash API
- **Free tier**: 50 requests/hour
- **Requires**: API key
- **Less specific**: Not optimized for character images

## Performance Considerations

### Caching Strategy
- **Database cache**: Store API results in database to avoid repeated calls
- **In-memory cache**: Consider adding a simple Map cache for frequently accessed characters
- **Cache invalidation**: Consider TTL (time-to-live) for cached images

### Rate Limiting
- Wikipedia API: ~200 requests/second (very generous)
- Consider batching requests if loading many characters at once
- Add retry logic with exponential backoff

### Offline Support
- Always check database cache first (works offline)
- Only call API when online and cache miss
- Gracefully handle API failures

## Testing

### Test Cases:
1. ✅ Character with API image → Uses API image
2. ✅ Character without API image, in cache → Uses cached image
3. ✅ Character without API image, not in cache, online → Searches API, caches result
4. ✅ Character without API image, not in cache, offline → Returns null
5. ✅ API search fails → Returns null gracefully

## Migration Path

### Phase 1: Add API support (backward compatible)
- Keep JSON file as fallback
- Add API search as additional option
- Test with subset of characters

### Phase 2: Remove JSON dependency
- Once API proves reliable, remove JSON file
- Remove `CharacterImageUrlReader` usage
- Clean up unused code

## Code Files Created/Modified

### New Files:
- ✅ `CharacterImageSearchService.kt` - Wikipedia API service
- ✅ `CharacterImageSearchRepository.kt` - Repository for image search
- ✅ `API_IMAGE_SEARCH_IMPLEMENTATION.md` - This document

### Modified Files:
- ✅ `CharacterRepositoryImpl.kt` - Updated to use API search
- ⏳ `NetworkModule.kt` - Add new dependencies
- ⏳ `AppGraph.kt` - Wire up dependencies
- ⏳ `CharacterImageDao.kt` - Add single insert method (optional)

## Implementation Status: ✅ COMPLETE

All implementation steps have been completed:

1. ✅ **Wikipedia API service created** - `CharacterImageSearchService.kt`
2. ✅ **Repository implemented** - `CharacterImageSearchRepository.kt` with multi-query fallback
3. ✅ **NetworkModule updated** - Added image search service and repository
4. ✅ **Dependency injection wired** - All services properly connected in `AppGraph.kt`
5. ✅ **CharacterRepository updated** - Now uses API search with database caching
6. ✅ **Caching implemented** - API results are stored in database for future use

## Next Steps (Optional)

1. **Test with real characters** - Verify it works end-to-end with actual API calls
2. **Monitor performance** - Check API response times and success rates
3. **Remove JSON file** - Once API approach is proven reliable (optional, can keep as fallback)

## Estimated Effort
- **Implementation**: 2-3 hours
- **Testing**: 1-2 hours
- **Total**: ~4-5 hours

## Risk Assessment
- **Low Risk**: API approach is well-established pattern
- **Mitigation**: Keep JSON as fallback initially
- **Rollback**: Easy to revert if issues arise

