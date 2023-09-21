package github.returdev.animemangavault.data.api.model.core.components

import com.google.gson.annotations.SerializedName

data class DataImageResponseComponent(

    @SerializedName("webp") val webpFormat : ImageUrls

){
    /**
     * Contains the urls of an image in different sizes.
     *
     * @property smallImageUrl The URL of the small-sized image.
     * @property normalImageUrl The URL of the normal-sized image.
     * @property largeImageUrl The URL of the large-sized image.
     */
    data class ImageUrls(
        @SerializedName("small_image_url") val smallImageUrl : String,
        @SerializedName("image_url") val normalImageUrl : String,
        @SerializedName("large_image_url") val largeImageUrl : String
    )
}