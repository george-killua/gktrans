package com.killua.features.image.resource

import com.killua.features.image.domain.ImagesRepo
import com.killua.inject
import com.killua.plugins.AUTHENTICATION_HEADER
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post

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


