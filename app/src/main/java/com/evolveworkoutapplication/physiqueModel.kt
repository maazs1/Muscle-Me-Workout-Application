package com.evolveworkoutapplication

public class physiqueModel {
    private var name: String? = null
    private var image_drawable = 0

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getImage_drawable(): Int {
        return image_drawable
    }

    fun setImage_drawable(image_drawable: Int) {
        this.image_drawable = image_drawable
    }
}