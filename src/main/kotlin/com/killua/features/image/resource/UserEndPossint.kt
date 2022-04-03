package com.killua.features.image.resource

import com.killua.features.image.domain.ImageRepository
import com.killua.features.image.domain.model.ImageDto
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.imageEndpoint() {
    routing {
        route("/image") {
            val imageRepository by inject<ImageRepository>()

            //create image request

            post {
                val image = call.receive<ImageDto>()


                val created = imageRepository.addImage(image)

                call.respond(HttpStatusCode.Created, created.toString())

            }
            //get all images
            get {

                val image: Any = when (val id = call.request.queryParameters["id"]) {
                    null -> {
                        imageRepository.getAllImages()
                    }
                    else -> {
                        imageRepository.getImage(id)
                    }
                }
                val response = image
                call.respond(response)
            }

            //update user details

            delete {
                val imageId = call.request.queryParameters["id"].toString()
                imageId.ifBlank {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
                val result = imageRepository.deleteImage(imageId)
                if (result)
                    call.respond(HttpStatusCode.Accepted)
                else call.respond(HttpStatusCode.BadRequest)
            }

        }
    }
}
