package com.insane.whatsthebra.config

object AppConfig {

    /**
     * API base - host and end-points
     */
    object API {

        private const val hostBase: String = "https://insane-wtb.rcastrucci.com/"
        private const val api: String = "api/wtb-v1/"
        private const val imageEndPointId: String = "image/id/"
        private const val imageEndPoint: String = "image/"

        fun getHostBase(): String {
            return hostBase;
        }

        fun getHost(): String {
            return "${hostBase}${api}";
        }

        fun getImageUrl(imageId: Int): String {
            return "${hostBase}${api}${imageEndPointId}${imageId}"
        }

        fun getImageUrl(imageName: String): String {
            return "${hostBase}${api}${imageEndPoint}${imageName}"
        }

    }

}