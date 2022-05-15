package com.example.practice

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AddTimePlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        def transform = new AddTimeTransform()
        def baseExtension = target.extensions.getByType(BaseExtension)
        baseExtension.registerTransform(transform)
    }
}

