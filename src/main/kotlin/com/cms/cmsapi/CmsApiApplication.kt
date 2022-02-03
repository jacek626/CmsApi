package com.cms.cmsapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CmsApiApplication

fun main(args: Array<String>) {
    runApplication<CmsApiApplication>(*args)
}
