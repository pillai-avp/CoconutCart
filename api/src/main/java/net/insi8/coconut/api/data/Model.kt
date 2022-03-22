package net.insi8.coconut.api.data

import com.google.gson.annotations.SerializedName

data class Cart(
    val items: List<Item>,
    @SerializedName("extra_lines")
    val extraLines: List<ExtraLine>
)

data class Item(
    val product: Product,
    val quantity: Long,
    @SerializedName("display_price_total")
    val displayPriceTotal: String,
    @SerializedName("discounted_display_price_total")
    val discountedDisplayPriceTotal: String,
    val availability: Availability
)

data class Product(
    val id: Long,
    @SerializedName("full_name")
    val fullName: String,
    val brand: String?,
    @SerializedName("brand_id")
    val brandId: Long?,
    val name: String,
    @SerializedName("name_extra")
    val nameExtra: String,
    @SerializedName("front_url")
    val frontUrl: String,
    val images: List<Image>,
    @SerializedName("gross_price")
    val grossPrice: String,
    @SerializedName("gross_unit_price")
    val grossUnitPrice: String,
    @SerializedName("unit_price_quantity_abbreviation")
    val unitPriceQuantityAbbreviation: String,
    @SerializedName("unit_price_quantity_name")
    val unitPriceQuantityName: String,
    val discount: Discount?,
    val promotion: Promotion?,
    val availability: Availability,
    @SerializedName("client_classifiers")
    val clientClassifiers: List<ClientClassifier>
)

data class Image(
    val thumbnail: Thumbnail,
    val large: Large
)

data class Thumbnail(
    val url: String
)

data class Large(
    val url: String
)

data class Discount(
    @SerializedName("is_discounted")
    val isDiscounted: Boolean,
    @SerializedName("undiscounted_gross_price")
    val undiscountedGrossPrice: String,
    @SerializedName("undiscounted_gross_unit_price")
    val undiscountedGrossUnitPrice: String,
    @SerializedName("description_short")
    val descriptionShort: String
)

data class Promotion(
    val title: String,
    @SerializedName("title_color")
    val titleColor: String,
    @SerializedName("background_color")
    val backgroundColor: String,
    @SerializedName("text_color")
    val textColor: String,
    @SerializedName("description_short")
    val descriptionShort: String,
    @SerializedName("accessibility_text")
    val accessibilityText: String
)

data class Availability(
    @SerializedName("is_available")
    val isAvailable: Boolean,
    val code: String,
    val description: String,
    @SerializedName("description_short")
    val descriptionShort: String
)

data class ClientClassifier(
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("is_important")
    val isImportant: Boolean
)

data class ExtraLine(
    val description: String,
    @SerializedName("long_description")
    val longDescription: Any,
    @SerializedName("gross_amount")
    val grossAmount: String
)
