package com.tamasha.vedioaudiostreamingapp.util

import com.tamasha.vedioaudiostreamingapp.model.Constants.ENV_PROD
import com.tamasha.vedioaudiostreamingapp.model.Constants.ENV_QA
import com.tamasha.vedioaudiostreamingapp.model.Constants.REGEX_MEETING_URL_CODE
import com.tamasha.vedioaudiostreamingapp.model.Constants.REGEX_MEETING_URL_ROOM_ID

fun String.isValidMeetingUrl(): Boolean =
    this.matches(REGEX_MEETING_URL_ROOM_ID) || this.matches(REGEX_MEETING_URL_CODE)

fun String.getTokenEndpointEnvironment(): String = when {
    this.contains("prod2.100ms.live") -> "prod-in"
    this.contains(".app.100ms.live") -> "prod-in"
    else -> "qa-in"
}

fun String.getInitEndpointEnvironment(): String = when {
    this.contains("prod2.100ms.live") -> ENV_PROD
    this.contains(".app.100ms.live") -> ENV_PROD
    else -> ENV_QA
}

fun String.toUniqueRoomSpecifier(): String {
    require(this.isValidMeetingUrl()) {
        "$this is not a valid meeting-url"
    }

    return if (REGEX_MEETING_URL_CODE.matches(this)) {
        val groups = REGEX_MEETING_URL_CODE.findAll(this).toList()[0].groupValues
        groups[2]
    } else /* if (REGEX_MEETING_URL_ROOM_ID.matches(this)) */ {
        val groups = REGEX_MEETING_URL_ROOM_ID.findAll(this).toList()[0].groupValues
        groups[2]
    }
}
