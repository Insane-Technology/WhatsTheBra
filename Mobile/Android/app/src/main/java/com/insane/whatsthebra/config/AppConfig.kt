package com.insane.whatsthebra.config

object AppConfig {

    /**
     * API base - host and end-points
     */
    object API {

        private const val apiHost: String = "http://160.238.220.112:9090/api/wtb-v1/"
        private const val imageEndPointId: String = "image/id/"
        private const val imageEndPoint: String = "image/"

        fun getHost(): String {
            return apiHost;
        }

        fun getImageUrl(imageId: Int): String {
            return "${apiHost}${imageEndPointId}${imageId}"
        }

        fun getImageUrl(imageName: String): String {
            return "${apiHost}${imageEndPoint}${imageName}"
        }

    }

}