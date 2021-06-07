package com.skidi.skidi.model

import com.google.gson.annotations.SerializedName

data class BackendResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("included")
	val included: List<IncludedItem?>? = null
)

data class Coordinates(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class AttributesItem(

	@field:SerializedName("coordinates")
	val coordinates: Coordinates? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("maps_url")
	val mapsUrl: String? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null
)

data class SourcesItem(

	@field:SerializedName("snippet")
	val snippet: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class Attributes(

	@field:SerializedName("symptom_name")
	val symptomName: String? = null,

	@field:SerializedName("sources")
	val sources: List<SourcesItem?>? = null
)

data class IncludedItem(

	@field:SerializedName("attributes")
	val attributes: List<AttributesItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class Data(

	@field:SerializedName("attributes")
	val attributes: Attributes? = null,

	@field:SerializedName("type")
	val type: String? = null
)
