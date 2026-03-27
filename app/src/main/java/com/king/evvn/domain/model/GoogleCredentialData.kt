package com.king.evvn.domain.model

data class GoogleCredentialData(
    val idToken: String,
    val displayName: String?,
    val familyName: String?,
    val givenName: String?,
    val phoneNumber: String?,
    val profileURL: String?
)
