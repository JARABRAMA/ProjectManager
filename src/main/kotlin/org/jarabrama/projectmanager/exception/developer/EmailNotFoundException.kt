package org.jarabrama.projectmanager.exception.developer

class EmailNotFoundException(email: String): DeveloperException("Developer $email not found") {
}