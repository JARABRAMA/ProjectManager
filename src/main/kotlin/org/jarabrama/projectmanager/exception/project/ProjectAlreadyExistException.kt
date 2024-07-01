package org.jarabrama.projectmanager.exception.project

class ProjectAlreadyExistException(name: String): ProjectException("Project $name already exist") {
}