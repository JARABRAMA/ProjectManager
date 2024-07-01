package org.jarabrama.projectmanager.exception.project


class ProjectNotFoundException : ProjectException {
    constructor(id: Long) : super("Project id $id not found")
    constructor(name: String) : super("Project name $name not found")
}
