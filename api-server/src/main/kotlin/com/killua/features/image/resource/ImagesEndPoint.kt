@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.killua.features.image.resource

import com.killua.features.image.domain.ImagesRepo
import com.killua.plugins.AUTHENTICATION_HEADER
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.locations.*
import io.ktor.locations.post
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

@Location("/image")
class ImageIndex {
    @Location("/user")
    data class AddUserImage(val userId: String)

    @Location("/accident")
    data class AddAccidentImage(val accidentId: String)

    @Location("/usedHistory")
    data class AddUsedHistoryImage(val UsedHistoryId: String)

    @Location("/car")
    data class AddCarImage(val carId: String)

    @Location("")
    data class DeleteImage(val imageId: String)

    @Location("/clean-table")
    class CleanImagesTable
}

@KtorExperimentalLocationsAPI
fun Route.imageEndpoint() {


    authenticate(AUTHENTICATION_HEADER) {
        val imagesRepo by inject<ImagesRepo>()


        get<ImageIndex> {
            call.respond("did it")
        }
        post<ImageIndex.AddUserImage> {
            call.respond("did it")
        }
        post<ImageIndex.AddCarImage> {
            call.respond("did it")
        }
        post<ImageIndex.AddAccidentImage> {
            call.respond("did it")
        }
        post<ImageIndex.AddUsedHistoryImage> {
            call.respond("did it")
        }
        delete<ImageIndex.DeleteImage> { it ->

            call.respond(("image deleted ${it.imageId}"))
        }
        delete<ImageIndex.CleanImagesTable> { it ->

            call.respond(("image deleted  clean table"))
        }
    }
}


